/*
    Copyright 2016 Angelo Moroni

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */


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
    private static final long ONE_SECOND = 1000;
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


    /**
     * Constructor with one parameter.
     *
     * count down interval: 1000 millis (one second)
     *
     * @param millisInFuture
     */
    public CountDownTimer(Long millisInFuture){
        this(millisInFuture,ONE_SECOND);
    }

    /**
     * Contructor expressed in seconds
     * @param seconds to start count down
     */
    public CountDownTimer(int seconds){
        this((long) (seconds*1000),ONE_SECOND);
    }

    /**
     * standard constructor in seconds
     * @param secondsInFuture
     * @param countDownInterval
     */
    public CountDownTimer(int secondsInFuture,int countDownInterval){
        this((long)(secondsInFuture*1000),(long)(countDownInterval*1000));
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
