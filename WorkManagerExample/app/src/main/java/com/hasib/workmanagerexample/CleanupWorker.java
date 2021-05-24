package com.hasib.workmanagerexample;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.util.Arrays;

public class CleanupWorker extends Worker {
    private static final String TAG = CleanupWorker.class.getSimpleName();

    public CleanupWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context applicationContext = getApplicationContext();

        // Makes a notification when the work starts and slows down the work so that it's easier to
        // see each WorkRequest start, even on emulated devices
        WorkerUtils.makeStatusNotification("Cleaning up old temporary files",
                applicationContext);
        WorkerUtils.sleep();

        try {
            File outputDirectory = new File(applicationContext.getFilesDir(), Constants.OUTPUT_PATH);
            if (outputDirectory.exists()) {
                File[] entries = outputDirectory.listFiles();
                if (entries != null && entries.length > 0) {
                    Arrays.stream(entries)
                            .filter(file -> !TextUtils.isEmpty(file.getName()) && file.getName().endsWith(".png"))
                            .forEach(file -> {
                                boolean deleted = file.delete();
                                Log.i(TAG, String.format("Deleted %s - %s", file.getName(), deleted));
                            });
                }
            }

            return ListenableWorker.Result.success();
        } catch (Exception e) {
            Log.e(TAG, "Error cleaning up", e);
            return ListenableWorker.Result.failure();
        }
    }
}
