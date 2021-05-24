package com.hasib.workmanagerexample;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.hasib.workmanagerexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private int permissionRequestCount = 0;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            permissionRequestCount = savedInstanceState.getInt(Constants.KEY_PERMISSIONS_REQUEST_COUNT, 0);
        }

        requestPermissionsIfNecessary();

        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, Constants.REQUEST_CODE_IMAGE);
            }
        });
    }

    /**
     * Save the permission request count on a rotate
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.KEY_PERMISSIONS_REQUEST_COUNT, permissionRequestCount);
    }

    /**
     * Request permissions twice - if the user denies twice then show a toast about how to update
     * the permission for storage. Also disable the button if we don't have access to pictures on
     * the device.
     */
    private void requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (permissionRequestCount < Constants.MAX_NUMBER_REQUEST_PERMISSIONS) {
                permissionRequestCount++;
                ActivityCompat.requestPermissions(
                        this,
                        Constants.PERMISSIONS.toArray(new String[0]),
                        Constants.REQUEST_CODE_PERMISSIONS
                );
            } else {
                Toast.makeText(this, R.string.set_permissions_in_settings, Toast.LENGTH_SHORT).show();
                binding.selectImage.setEnabled(false);
            }
        }
    }

    private boolean checkAllPermissions() {
        boolean hasPermission = true;
        for (String permission : Constants.PERMISSIONS) {
            hasPermission = hasPermission && isPermissionGranted(permission);
        }

        return hasPermission;
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.REQUEST_CODE_IMAGE) {
            try {
                handleImageRequestResult(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "onActivityResult: " + String.format("Unexpected Result code %s", resultCode));
        }
    }

    private void handleImageRequestResult(Intent intent) {
        // If clipdata is available, we use it, otherwise we use data
        Uri imageUri = null;
        if (intent.getClipData() != null) {
            imageUri = intent.getClipData().getItemAt(0).getUri();
        } else if (intent.getData() != null) {
            imageUri = intent.getData();
        }

        if (imageUri == null) {
            Log.e(TAG, "handleImageRequestResult: Invalid input image uri");
            return;
        } else {
            Log.d(TAG, "handleImageRequestResult: " + imageUri.toString());
        }

        final Intent filterIntent = new Intent(this, BlurActivity.class);
        filterIntent.putExtra(Constants.KEY_IMAGE_URI, imageUri.toString());
        startActivity(filterIntent);
    }
}