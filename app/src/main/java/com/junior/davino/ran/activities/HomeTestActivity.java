package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestUser;
import com.junior.davino.ran.models.enums.EnumTestType;

import org.parceler.Parcels;

public class HomeTestActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HomeTestActivity";
    private TestUser testUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_test);
        testUser = Parcels.unwrap(getIntent().getParcelableExtra("user"));
        setListeners();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_signout){
            firebaseApp.logoff();
            Intent intent = new Intent(HomeTestActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setListeners(){
        findViewById(R.id.btn_color).setOnClickListener(this);
        findViewById(R.id.btn_digit).setOnClickListener(this);
        findViewById(R.id.btn_letter).setOnClickListener(this);
        findViewById(R.id.btn_object).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_color:
                initializeTest(EnumTestType.COLORS);
                break;
            case R.id.btn_digit:
                initializeTest(EnumTestType.DIGITS);
                break;
            case R.id.btn_letter:
                initializeTest(EnumTestType.LETTERS);
                break;
            case R.id.btn_object:
                initializeTest(EnumTestType.OBJECTS);
            break;

        }
    }


    private void initializeTest(EnumTestType testType){
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("option", testType);
        intent.putExtra("user", Parcels.wrap(testUser));
        this.startActivity(intent);
    }
}
