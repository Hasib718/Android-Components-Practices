package com.hasib.workmanagerexample;

import android.Manifest;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // Name of Notification Channel for verbose notifications of background work
    public static final String VERBOSE_NOTIFICATION_CHANNEL_NAME = "Verbose WorkManager Notifications";
    public static final String VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION = "Shows notifications whenever work starts";
    public static final String NOTIFICATION_TITLE = "WorkRequest Starting";
    public static final String CHANNEL_ID = "VERBOSE_NOTIFICATION";
    public static final int NOTIFICATION_ID = 1;

    // The name of the image manipulation work
    public static final String IMAGE_MANIPULATION_WORK_NAME = "image_manipulation_work";

    // Other keys
    public static final String OUTPUT_PATH = "blur_filter_outputs";
    public static final String KEY_IMAGE_URI = "KEY_IMAGE_URI";
    public static final String TAG_OUTPUT = "OUTPUT";

    public static final long DELAY_TIME_MILLIS = 3000;

    public static final int REQUEST_CODE_IMAGE = 100;
    public static final int REQUEST_CODE_PERMISSIONS = 101;

    public static final String KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT";
    public static final int MAX_NUMBER_REQUEST_PERMISSIONS = 2;

    public static final List<String> PERMISSIONS = Arrays.asList(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    );
}
