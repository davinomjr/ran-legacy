package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestUser;
import com.junior.davino.ran.models.enums.EnumGender;

import org.parceler.Parcels;

public class TestUsersDetailsActivity extends BaseActivity {

    private static final String TAG = "TestUsersDetailsActivity";
    private TextInputLayout inputLayoutName, inputLayoutAge, inputLayoutSchoolGrade;
    private EditText inputName, inputAge, inputSchoolGrade;
    private RadioButton radioMale, radioFemale;
    private EnumGender currentChoseGender;
    private TestUser testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_users_details);
        testUser = Parcels.unwrap(getIntent().getParcelableExtra("testUser"));


        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutAge = (TextInputLayout) findViewById(R.id.input_layout_age);
        inputLayoutSchoolGrade = (TextInputLayout) findViewById(R.id.input_layout_schoolGrade);

        inputName = (EditText) findViewById(R.id.input_name);
        inputAge = (EditText) findViewById(R.id.input_age);
        inputSchoolGrade = (EditText) findViewById(R.id.input_schoolGrade);
        inputName.addTextChangedListener(new TextChangeListener(inputName));

        radioMale = (RadioButton)findViewById(R.id.radio_male);
        radioFemale= (RadioButton)findViewById(R.id.radio_female);

        inputName.setText(testUser.getName());
        inputAge.setText(String.valueOf(testUser.getAge()));
        inputSchoolGrade.setText(testUser.getSchoolGrade());

        if(testUser.getGender().equals("Masculino")){
            radioMale.setChecked(true);
        }
        else{
            radioFemale.setChecked(true);
        }

        Button updateButton = (Button)findViewById(R.id.btn_update_user);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickButtonListener");
                updateUser();
            }
        });

        Button startTestButton = (Button)findViewById(R.id.btn_start_test);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickButtonListener");
                startActivity(new Intent(TestUsersDetailsActivity.this, HomeTestActivity.class));
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    currentChoseGender = EnumGender.MALE;
                break;
            case R.id.radio_female:
                if (checked)
                    currentChoseGender = EnumGender.FEMALE;
                break;
        }
    }


    private void updateUser(){
        if(!validateForm()){
            return;
        }

        try {
            String key = testUser.getUserId();
            testUser.setName(inputName.getText().toString());
            testUser.setAge(Integer.parseInt(inputAge.getText().toString()));
            testUser.setSchoolGrade(inputSchoolGrade.getText().toString());
            if(currentChoseGender == EnumGender.MALE){
                testUser.setGender(getString(R.string.genderMale));
            }
            else{
                testUser.setGender(getString(R.string.genderFemale));
            }

            database.getReference("users").child(key).setValue(testUser);
            showSnackBar(getString(R.string.updateSuccess));
        }
        catch (Exception e){
            Log.i(TAG, e.getMessage());
        }
    }


    private boolean validateForm(){
        if(!validateName()){
            return false;
        }

        if(!validateAge()){
            return false;
        }

        if(!validateSchoolGrade()){
            return false;
        }

        return true;
    }

    private void requestFocus(View v){
        if (v.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void setRequiredMessage(TextInputLayout textLayout){
        textLayout.setError(String.format(getString(R.string.err_required_input), textLayout.getHint()));
        requestFocus(inputName);
    }

    private boolean validateName(){
        if(inputName.getText().toString().trim().isEmpty()){
            setRequiredMessage(inputLayoutName);
            return false;
        }

        return true;
    }

    private boolean validateAge(){
        if(inputAge.getText().toString().trim().isEmpty()){
            setRequiredMessage(inputLayoutAge);
            return false;
        }


        return true;
    }

    private boolean validateSchoolGrade(){
        if(inputSchoolGrade.getText().toString().trim().isEmpty()){
            setRequiredMessage(inputLayoutSchoolGrade);
            return false;
        }

        return true;
    }


    private class TextChangeListener implements TextWatcher {

        private View view;

        private TextChangeListener(View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_age:
                    validateAge();
                    break;
                case R.id.input_layout_schoolGrade:
                    validateSchoolGrade();
                    break;
            }
        }
    }
}
