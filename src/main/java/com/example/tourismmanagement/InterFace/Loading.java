package com.example.tourismmanagement.InterFace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tourismmanagement.Deferent.ProgressBarAnimation;
import com.example.tourismmanagement.R;

public class Loading extends AppCompatActivity {
    ImageView imgLoading;
    TextView textViewProgress;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        setControl();
        setEvent();


    }

    private void setEvent() {
        final AnimationDrawable loadingDrawable = (AnimationDrawable) imgLoading.getDrawable();
        loadingDrawable.start();

        ProgressBarAnimation anim = new ProgressBarAnimation(this,progressBar, textViewProgress, 0f, 100f);
        anim.setDuration(5000);
        progressBar.setAnimation(anim);
    }

    private void setControl() {
        imgLoading = findViewById(R.id.imageViewLoading);
        textViewProgress = findViewById(R.id.textViewProgess);
        progressBar = findViewById(R.id.progressBar);
    }
}