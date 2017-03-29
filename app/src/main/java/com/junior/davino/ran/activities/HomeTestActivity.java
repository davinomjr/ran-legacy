package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.enums.EnumTestType;

public class HomeTestActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HomeTestActivity";
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_test);
        setListeners();
//        toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
//        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
        this.startActivity(intent);
    }


}
