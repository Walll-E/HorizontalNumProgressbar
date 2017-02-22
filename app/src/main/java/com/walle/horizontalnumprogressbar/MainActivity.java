package com.walle.horizontalnumprogressbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.walle.progressbar.NumberProgressBar;
import com.walle.progressbar.OnProgressBarListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private NumberProgressBar progressBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        progressBar = (NumberProgressBar) findViewById(R.id.progressBar);
//        progressBar.setmRectfHeight(dp2px(30));
//        progressBar.setSuffix(" ");
//        progressBar.setProgressTextVisibility(View.INVISIBLE);
//        progressBar.setOnProgressBarListener(this);
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressBar.incrementProgressBy(1);
//                    }
//                });
//            }
//        },2000,100);
//    }
//
//    public float dp2px(float dp) {
//        final float scale = getResources().getDisplayMetrics().density;
//        return dp * scale + 0.5f;
//    }
//
//    @Override
//    public void onProgressChange(int current, int max) {
//        if (current==max){
//            Toast.makeText(this,"完成",Toast.LENGTH_SHORT).show();
//            progressBar.setProgress(0);
//            return;
//        }
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        timer.cancel();
//    }
    }
}
