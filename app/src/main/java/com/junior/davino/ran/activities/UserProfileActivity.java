package com.junior.davino.ran.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.junior.davino.ran.R;

public class UserProfileActivity extends BaseActivity {

    private static final String TAG = "UserProfileActivity";
    EditText inputName, inputEmail, inputPassword;
    TextInputLayout nameLayout, emailLayout, passwordLayout;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        emailLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        nameLayout.setHintAnimationEnabled(false);
        emailLayout.setHintAnimationEnabled(false);

        fillValues();
        updateButton = (Button) findViewById(R.id.btn_update_user);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void fillValues(){
        FirebaseUser user = firebaseApp.getFirebaseAuth().getCurrentUser();
        inputName.setText(user.getDisplayName());
        inputEmail.setText(user.getEmail());

        nameLayout.setHintAnimationEnabled(true);
        emailLayout.setHintAnimationEnabled(true);
    }

    private void updateUser(){
        if (!validate()) {
            return;
        }

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        firebaseApp.updateUser(name, email, password);
        showSnackBar(getString(R.string.updateSuccess));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if(name.isEmpty()){
            nameLayout.setError(getString(R.string.err_msg_name));
            valid = false;
        } else {
            nameLayout.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError(getString(R.string.err_msg_email));
            valid = false;
        } else {
            emailLayout.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordLayout.setError(getString(R.string.err_msg_password));
            valid = false;
        } else {
            passwordLayout.setError(null);
        }

        return valid;
    }

}
