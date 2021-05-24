package com.hasib.workmanagerexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;

import com.bumptech.glide.Glide;
import com.hasib.workmanagerexample.databinding.ActivityBlurBinding;

import java.util.List;

public class BlurActivity extends AppCompatActivity {

    private ActivityBlurBinding binding;
    private BlurViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlurBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(BlurViewModel.class);

        Intent intent = getIntent();
        String imageUriExtra = intent.getStringExtra(Constants.KEY_IMAGE_URI);
        viewModel.setImageUri(imageUriExtra);
        if (viewModel.getImageUri() != null) {
            Glide.with(this).load(viewModel.getImageUri()).into(binding.imageView);
        }

        binding.goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.applyBlur(getBlurLevel());
            }
        });

        viewModel.getSavedWorkInfo().observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                // If there are no matching work info, do nothing
                if (workInfos == null || workInfos.isEmpty()) {
                    return;
                }

                // We only care about the first output status.
                // Every continuation has only one worker tagged TAG_OUTPUT
                WorkInfo workInfo = workInfos.get(0);

                if (!workInfo.getState().isFinished()) {
                    showWorkInProgress();
                } else {
                    showWorkFinished();

                    String outputImageUri = workInfo.getOutputData().getString(Constants.KEY_IMAGE_URI);
                    if (!TextUtils.isEmpty(outputImageUri)) {
                        viewModel.setOutputUri(outputImageUri);
                        binding.seeFileButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        binding.seeFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri currentUri = viewModel.getOutputUri();
                if (currentUri != null) {
                    Intent actionView = new Intent(Intent.ACTION_VIEW, currentUri);
                    if (actionView.resolveActivity(getPackageManager()) != null) {
                        startActivity(actionView);
                    }
                }
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.cancelWork();
            }
        });
    }

    private void showWorkFinished() {
        binding.progressBar.setVisibility(View.GONE);
        binding.cancelButton.setVisibility(View.GONE);
        binding.goButton.setVisibility(View.VISIBLE);
    }

    private void showWorkInProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.cancelButton.setVisibility(View.VISIBLE);
        binding.goButton.setVisibility(View.GONE);
        binding.seeFileButton.setVisibility(View.GONE);
    }

    private int getBlurLevel() {
        switch (binding.radioBlurGroup.getCheckedRadioButtonId()) {
            case R.id.radio_blur_lv_1:
                return 1;
            case R.id.radio_blur_lv_2:
                return 2;
            case R.id.radio_blur_lv_3:
                return 3;
        }

        return 1;
    }
}