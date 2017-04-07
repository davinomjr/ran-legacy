package com.junior.davino.ran.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.junior.davino.ran.R;
import com.junior.davino.ran.code.FirebaseApplication;
import com.junior.davino.ran.utils.FontsUtil;

/**
 * Created by davin on 24/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected FirebaseApplication firebaseApp;
    protected FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFonts();
        database = FirebaseDatabase.getInstance();
        firebaseApp = new FirebaseApplication();
    }

    private void setFonts(){
        FontsUtil.setDefaultFont(this, "DEFAULT", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "MONOSPACE", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "SERIF", "fonts/OpenDyslexic-Regular.otf");
        FontsUtil.setDefaultFont(this, "SANS_SERIF", "fonts/OpenDyslexic-Regular.otf");
    }

    public void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
