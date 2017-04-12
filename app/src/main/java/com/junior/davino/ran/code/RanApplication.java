package com.junior.davino.ran.code;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.junior.davino.ran.R;
import com.meetme.support.fonts.FontManager;

/**
 * Created by davin on 10/04/2017.
 */

public class RanApplication extends Application {
    private static final String TAG = "RanApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "onCreate: SDK INT=" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) Log.v(TAG, "onCreate: PREVIEW_SDK_INT=" + Build.VERSION.PREVIEW_SDK_INT);

        boolean installed = FontManager.install(this, R.raw.fonts);
        Log.v(TAG, "FontManager.install=" + installed);
    }
}
