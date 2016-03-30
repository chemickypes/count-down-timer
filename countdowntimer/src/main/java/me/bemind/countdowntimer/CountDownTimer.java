package me.bemind.countdowntimer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by debug on 23/03/16.
 */
public abstract class CountDownTimer {

    private static final java.lang.String REMAINING_MILLIS_KEY = "remaining_mills_key";
    private static final int DURING = 1;
    private static final int END = 2;
    private Long millisInFuture, countDownInterval;

    private Thread thread;

    private Handler handler;
    private boolean threadCanRun = true;
    private boolean isThreadStoppped = false;

    public CountDownTimer(Long millisInFuture, Long countDownInterval) {
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        setHandler();
        setThread();
    }

    private void setHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(!isThreadStoppped) {
                    if (msg.what == DURING) {
                        onTick(msg.getData().getLong(REMAINING_MILLIS_KEY));
                    } else if (msg.what == END) {
                        threadCanRun = true;
                        setThread();
                        onFinish();
                    } else {

                    }
                }else {
                    threadCanRun = true;
                    setThread();
                }
            }
        };
    }

    private void setThread() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Long mif = millisInFuture;

                handler.sendEmptyMessageDelayed(END,millisInFuture);

                while (mif > 0){
                    mif -= countDownInterval;
                    Bundle bundle = new Bundle();
                    bundle.putLong(REMAINING_MILLIS_KEY,mif);
                    Message m = new Message();
                    m.what = DURING;
                    m.setData(bundle);
                    handler.sendMessageDelayed(m,mif);
                }
            }
        });
    }


    public void start(){

        if(threadCanRun){
            isThreadStoppped = false;
            threadCanRun = false;
            thread.start();

        }

    }

    public void cancel(){
        isThreadStoppped = true;
        handler.removeMessages(DURING);
        handler.removeMessages(END);
        setThread();
        threadCanRun = true;
        //setHandler();
    }

    protected abstract void onFinish();

    protected abstract void onTick(long millisUntilFinished);
}
