package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.junior.davino.ran.R;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = (Button)findViewById(R.id.btn_cores);
        button.setOnClickListener(this);
    }


    private void initializeTest(){
        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onClick(View v){
        Log.i(TAG, "Onclick");
        switch(v.getId()){
            case R.id.btn_cores:
                initializeTest();
        }
    }


}
