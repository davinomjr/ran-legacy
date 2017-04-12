package com.junior.davino.ran.utils;

import android.util.Log;

/**
 * Created by davin on 27/02/2017.
 */

public class TimerUtil {
    private static final String TAG = "TimerUtil";
    long start;
    boolean running;
    int lastResult;

    public int getLastResult() {
        return lastResult;
    }

    public void start(){
        Log.i(TAG, "start");
        start = System.currentTimeMillis();
        running = true;
    }

    public int stop(){
        Log.i(TAG, "stop");
        running = false;
        long tDelta = System.currentTimeMillis() - start;
        double elapsedSeconds = tDelta / 1000.0;
        lastResult = (int)Math.ceil(elapsedSeconds) - 1;
        return lastResult;
    }

    public boolean isTimerRunninng(){
        return running;
    }

    public void reset(){
        Log.i(TAG, "reset");
        start = 0;
    }
}
