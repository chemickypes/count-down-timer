# count-down-timer
Custom Count Down Timer inspired by Android CountDownTimer

I had some problems with Android [CountDownTimer](http://developer.android.com/reference/android/os/CountDownTimer.html) and so I made my version. 

## Constructor

 Standard Constructor
`public CountDownTimer(Long millisInFuture, Long countDownInterval)`

 Standard Constructor using seconds
`public CountDownTimer(int secondsInFuture,int countDownInterval)`

 Constructor in millis with count down interval 1000 
 `public CountDownTimer(Long millisInFuture)`
 
 Constructor in second with count down interval one second
 `public CountDownTimer(int seconds)`
 

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

