package com.hasib.workmanagerexample;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.List;

public class BlurViewModel extends AndroidViewModel {

    private final WorkManager workManager;
    private final LiveData<List<WorkInfo>> savedWorkInfo;
    private Uri imageUri, outputUri;

    public BlurViewModel(@NonNull Application application) {
        super(application);
        workManager = WorkManager.getInstance(application);

        /**
         *  This transformation makes sure that whenever the current work Id changes the WorkInfo
         *  the UI is listening to changes
         */
        savedWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.TAG_OUTPUT);
    }

    public LiveData<List<WorkInfo>> getSavedWorkInfo() {
        return savedWorkInfo;
    }

    public Uri getOutputUri() {
        return outputUri;
    }

    public void setOutputUri(String outputUri) {
        this.outputUri = uriOrNull(outputUri);
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     *
     * @param blurLevel The amount to blur the image
     */
    public void applyBlur(int blurLevel) {
        // Add WorkRequest to cleanup temporary images
        WorkContinuation continuation = workManager
                .beginUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        OneTimeWorkRequest.from(CleanupWorker.class));

        // Add WorkRequests to blur the image the number of times requested
        for (int i = 0; i < blurLevel; i++) {
            OneTimeWorkRequest.Builder blurBuilder = new OneTimeWorkRequest.Builder(BlurWorker.class);

            // Input the Uri if this is the first blur operation
            // After the first blur operation the input will be the output of previous
            // blur operations.
            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri());
            }

            continuation = continuation.then(blurBuilder.build());
        }

        // Create charging constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(true)
                .build();

        // Add WorkRequest to save the image to the filesystem
        OneTimeWorkRequest save = new OneTimeWorkRequest.Builder(SaveImageToFileWorker.class)
                .setConstraints(constraints)
                .addTag(Constants.TAG_OUTPUT)
                .build();

        continuation = continuation.then(save);

        // Actually start the work
        continuation.enqueue();
    }

    /**
     * Cancel work using the work's unique name
     */
    void cancelWork() {
        workManager.cancelUniqueWork(Constants.IMAGE_MANIPULATION_WORK_NAME);
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     *
     * @return Data which contains the Image Uri as a String
     */
    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        if (imageUri != null) {
            builder.putString(Constants.KEY_IMAGE_URI, imageUri.toString());
        }
        return builder.build();
    }

    private Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }

    /**
     * Getters
     */
    Uri getImageUri() {
        return imageUri;
    }

    /**
     * Setters
     */
    void setImageUri(String uri) {
        imageUri = uriOrNull(uri);
    }
}
