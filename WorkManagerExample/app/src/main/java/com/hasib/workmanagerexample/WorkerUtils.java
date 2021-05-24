package com.hasib.workmanagerexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class WorkerUtils {
    /**
     * Create a Notification that is shown as a heads-up notification if possible.
     * <p>
     * For this codelab, this is used to show a notification so that you know when different steps
     * of the background work chain are starting
     *
     * @param message Message shown on the notification
     * @param context Context needed to create Toast
     */
    public static void makeStatusNotification(String message, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_work_black_24)
                .setContentTitle(Constants.NOTIFICATION_TITLE)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0});

        NotificationManagerCompat.from(context).notify(Constants.NOTIFICATION_ID, builder.build());
    }

    /**
     * Method for sleeping for a fixed about of time to emulate slower work
     */
    public static void sleep() {
        try {
            Thread.sleep(Constants.DELAY_TIME_MILLIS, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Blurs the given Bitmap image
     *
     * @param bitmap  Image to blur
     * @param context Application context
     * @return Blurred bitmap image
     */
    public static Bitmap blurBitmap(Bitmap bitmap, Context context) {
        RenderScript rsContext = null;

        try {
            // Create the output bitmap
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

            // Blur the image
            rsContext = RenderScript.create(context, RenderScript.ContextType.DEBUG);
            Allocation inAlloc = Allocation.createFromBitmap(rsContext, bitmap);
            Allocation outAlloc = Allocation.createTyped(rsContext, inAlloc.getType());
            ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext));
            theIntrinsic.setRadius(10f);
            theIntrinsic.setInput(inAlloc);
            theIntrinsic.forEach(outAlloc);

            outAlloc.copyTo(output);

            return output;
        } finally {
            rsContext.finish();
        }
    }

    /**
     * Writes bitmap to a temporary file and returns the Uri for the file
     *
     * @param context Application context
     * @param bitmap  Bitmap to write to temp file
     * @return Uri for temp file with bitmap
     */
    public static Uri writeBitmapToFile(Context context, Bitmap bitmap) {
        final String name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString());
        final File outputDir = new File(context.getFilesDir(), Constants.OUTPUT_PATH);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        final File outputFile = new File(outputDir, name);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(outputFile);
    }
}
