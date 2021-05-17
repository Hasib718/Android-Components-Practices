package com.hasib.databindingexample;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.databinding.BindingAdapter;

public class BindingAdapters {

    @BindingAdapter("app:hideIfZero")
    public static void hideIfZero(View view, int number) {
        if (number == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter(value = {"app:progressScaled", "android:max"}, requireAll = true)
    public static void setProgress(ProgressBar progressBar, int likes, int max) {
        progressBar.setProgress(Math.min((likes * max / 10), max));
    }

    @BindingAdapter("app:popularityIcon")
    public static void popularityIcon(ImageView imageView, SimpleViewModel.Popularity popularity) {
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(getAssociatedColor(popularity, imageView.getContext())));
        imageView.setImageDrawable(getDrawablePopularity(popularity, imageView.getContext()));
    }

    @BindingAdapter("app:progressTint")
    public static void progressTint(ProgressBar progressBar, SimpleViewModel.Popularity popularity) {
        progressBar.setProgressTintList(ColorStateList.valueOf(getAssociatedColor(popularity, progressBar.getContext())));
    }

    private static int getAssociatedColor(SimpleViewModel.Popularity popularity, Context context) {
        switch (popularity) {
            case NORMAL:
                return context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.colorForeground}).getColor(0, 0x000000);
            case POPULAR:
                return ContextCompat.getColor(context, R.color.popular);
            case STAR:
                return ContextCompat.getColor(context, R.color.star);
            default:
                return ContextCompat.getColor(context, R.color.orange);
        }
    }

    private static Drawable getDrawablePopularity(SimpleViewModel.Popularity popularity, Context context) {
        switch (popularity) {
            case NORMAL:
                return ContextCompat.getDrawable(context, R.drawable.ic_person_black_96dp);
            case POPULAR:
            case STAR:
                return ContextCompat.getDrawable(context, R.drawable.ic_whatshot_black_96dp);
            default:
                return ContextCompat.getDrawable(context, R.drawable.ic_not_interested_black_96);
        }
    }
}
