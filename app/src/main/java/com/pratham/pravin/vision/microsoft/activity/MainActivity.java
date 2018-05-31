package com.pratham.pravin.vision.microsoft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pratham.pravin.vision.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_microsoft);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAnalyze)
    public void launchAnalyzeActivity(View view){
        launchActivity(AnalyzeImageActivity.class);
    }

    @OnClick(R.id.btnAnalyzeInDomains)
    public void launchAnalyzeInDomainActivity(View view){
        launchActivity(AnalyzeCelebritiesActivity.class);
    }

    @OnClick(R.id.btnRecognize)
    public void launchRecognizeActivity(View view){
        launchActivity(AnalyzeTextActivity.class);
    }

    @OnClick(R.id.btnHandwritingRecognize)
    public void launchHandwritingActivity(View view){
        launchActivity(HandwritingRecognizeActivity.class);
    }

    /*@OnClick(R.id.btnFaceDetection)
    public void launchFaceDetectionActivity(View view){
        launchActivity(FaceDetectionActivity.class);
    }*/

   /* @OnClick(R.id.btnEmotion)
    public void launchEmotionDetectionActivity(View view){
        launchActivity(EmotionActivity.class);
    }*/

     private void launchActivity(Class<?> cls){
        Intent mIntent = new Intent(MainActivity.this, cls);
        startActivity(mIntent);
    }

}
