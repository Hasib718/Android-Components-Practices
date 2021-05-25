package com.hasib.workmanagerexample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Locale;

public class SaveImageToFileWorker extends Worker {
    private static final String TAG = SaveImageToFileWorker.class.getSimpleName();
    private static final String TITLE = "Blurred Image";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss z", Locale.getDefault());

    public SaveImageToFileWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        // Makes a notification when the work starts and slows down the work so that it's easier to
        // see each WorkRequest start, even on emulated devices
        WorkerUtils.makeStatusNotification("Saving image", applicationContext);
        WorkerUtils.sleep();

        ContentResolver resolver = applicationContext.getContentResolver();
        try {
            String resourceUri = getInputData().getString(Constants.KEY_IMAGE_URI);

            Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)));

//            String outputUri = MediaStore.Images.Media.insertImage(
//                    resolver, bitmap, TITLE, DATE_FORMATTER.format(new Date()));

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, TITLE);
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
            values.put(MediaStore.Images.Media.DATE_ADDED, DATE_FORMATTER.format(new Date()));

            Uri outputUri = resolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL), values);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            if (outputUri != null) {
                resolver.openOutputStream(outputUri).write(stream.toByteArray());
            }
            bitmap.recycle();

            if (outputUri != null && TextUtils.isEmpty(outputUri.toString())) {
                Log.e(TAG, "Writing to MediaStore failed");
                return Result.failure();
            }

            Data outputData = new Data.Builder()
                    .putString(Constants.KEY_IMAGE_URI, outputUri.toString())
                    .build();
            return Result.success(outputData);
        } catch (Exception e) {
            Log.e(TAG, "Unable to save image to Gallery", e);
            return ListenableWorker.Result.failure();
        }
    }
}
