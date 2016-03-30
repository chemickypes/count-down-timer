# count-down-timer
Custom Count Down Timer inspired by Android CountDownTimer

I had some problems with Android [CountDownTimer](http://developer.android.com/reference/android/os/CountDownTimer.html) and so I made my version. 

##Constructor 
`public CountDownTimer(Long millisInFuture, Long countDownInterval)`

##Methods 
Methods of this implementation have same signature of standard one. 

`public void start();`

`public void cancel();`

`protected abstract void onFinish();`

`protected abstract void onTick(long millisUntilFinished);`

##Simple Example

    new CountDownTimer(MAX_TIME,COUNT_DOWN_INTERVAL) {

            @Override
            protected void onFinish() {
                Log.d(TAG,"FINISH!");
            }

            @Override
            protected void onTick(long millisUntilFinished) {
                Log.d(TAG,"Remaining: "+((MAX_TIME-millisUntilFinished)/COUNT_DOWN_INTERVAL));
            }
        }.start();

Simple App report same example. 

