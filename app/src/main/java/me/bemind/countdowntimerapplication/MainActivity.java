package me.bemind.countdowntimerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.bemind.countdowntimer.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVTY";
    private static final long MAX_TIME = 5000;
    private static final long COUNT_DOWN_INTERVAL = 1000;
    private Button text;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (Button) findViewById(R.id.text);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.start();
            }
        });

        findViewById(R.id.text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
            }
        });



        countDownTimer = new CountDownTimer(MAX_TIME,COUNT_DOWN_INTERVAL) {
            @Override
            protected void onFinish() {
                Log.d(TAG,"FINISH!");
            }

            @Override
            protected void onTick(long millisUntilFinished) {
                Log.d(TAG,"Remaining: "+((MAX_TIME-millisUntilFinished)/COUNT_DOWN_INTERVAL));
            }
        };
    }
}
