package com.junior.davino.ran.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.junior.davino.ran.R;
import com.junior.davino.ran.code.FirebaseApplication;
import com.junior.davino.ran.utils.Constants;
import com.junior.davino.ran.utils.FontsUtil;

import java.util.Arrays;

/**
 * Created by davin on 24/02/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected FirebaseApplication firebaseApp;
    protected FirebaseDatabase database;

    private static String currentFont = "Arial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFont(currentFont);
        database = FirebaseDatabase.getInstance();
        firebaseApp = new FirebaseApplication();
    }

    public void changeFont(String font){
        currentFont = font;
        setFont(currentFont);
    }

    public void showSnackBar(String message){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public int getCurrentFontNumber(){
        return FontsUtil.fontNameToNumber(currentFont);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    private void setFont(String font){
        String fontPath = String.format("fonts/%s.ttf", font);
        FontsUtil.setDefaultFont(this, "DEFAULT", fontPath);
        FontsUtil.setDefaultFont(this, "MONOSPACE", fontPath);
        FontsUtil.setDefaultFont(this, "SERIF", fontPath);
        FontsUtil.setDefaultFont(this, "SANS_SERIF", fontPath);
    }
}
