package com.hasib.chatappusingwebsocket;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class ChatActivity extends AppCompatActivity implements TextWatcher {

    private static final int IMAGE_REQUEST_ID = 1001;
    private final String SERVER_PATH = "ws://echo.websocket.org";
    private String name;
    private WebSocket webSocket;
    private EditText messageEdit;
    private TextView sendBtn;
    private ImageView pickImgBtn;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        name = getIntent().getStringExtra("name");
        initiateSocketConnection();
    }

    private void initiateSocketConnection() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = client.newWebSocket(request, new SocketListener());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();

        if (string.isEmpty()) {
            resetMessageEdit();
        } else {
            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void resetMessageEdit() {
        messageEdit.removeTextChangedListener(this);

        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);
    }

    private void initiateView() {
        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.sendBtn);
        pickImgBtn = findViewById(R.id.pickImgBtn);
        recyclerView = findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter(getLayoutInflater());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());

                webSocket.send(jsonObject.toString());

                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);

                resetMessageEdit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        pickImgBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Pick Image"), IMAGE_REQUEST_ID);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && requestCode == RESULT_OK) {
            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);

                sendImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage(Bitmap image) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

        String base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);
            jsonObject.put("image", base64String);

            webSocket.send(jsonObject.toString());

            jsonObject.put("isSent", true);

            messageAdapter.addItem(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private class SocketListener extends WebSocketListener {
        @Override
        public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
            super.onMessage(webSocket, bytes);

            runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("isSent", false);

                    messageAdapter.addItem(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
        }

        @Override
        public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(ChatActivity.this, "Socket Connection Successful!", Toast.LENGTH_SHORT).show();

                initiateView();
            });
        }
    }
}