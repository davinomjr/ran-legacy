package com.junior.davino.ran.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.junior.davino.ran.utils.FontsUtil;

/**
 * Created by davin on 24/02/2017.
 */

public abstract class BaseActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsUtil.setDefaultFont(this, "DEFAULT", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "MONOSPACE", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "SERIF", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "SANS_SERIF", "fonts/OpenDyslexic-Regular.otf");
    }

}
