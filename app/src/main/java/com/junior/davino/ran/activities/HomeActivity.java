package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.ui.auth.ResultCodes;
import com.junior.davino.ran.R;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActivity";
    private static final int RC_SIGN_IN = 29;
    private static final int RC_SIGN_UP = 0;
    EditText inputEmail, inputPassword;
    TextInputLayout emailLayout, passwordLayout;
    Button loginButton;
    MaterialDialog processingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        emailLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        TextView tvRegister = (TextView) findViewById(R.id.link_signup);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SignUpActivity.class));
            }
        });

        TextView tvLostPassword = (TextView) findViewById(R.id.link_lostPassword);
        tvLostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LostPasswordActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(this, TestUsersActivity.class));
                finish();
                return;
            } else {
                showSnackBar(getString(R.string.sign_In_Required));
            }
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


         processingDialog = new MaterialDialog.Builder(HomeActivity.this)
                .title(R.string.authenticating)
                .content(R.string.please_wait)
                .progress(true, 0)
                .show();


        firebaseApp.loginAUser(this, email, password, getString(R.string.err_msg_signin));
    }

    public boolean validate() {
        boolean valid = true;

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

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

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        processingDialog.dismiss();
        loginButton.setEnabled(true);
        startActivity(new Intent(getApplication(), TestUsersActivity.class));
    }

    public void onLoginFailed() {
        onLoginFailed(null);
    }

    public void onLoginFailed(String errorMessage) {
        if(processingDialog != null && processingDialog.isShowing()){
            processingDialog.dismiss();
        }

        if(errorMessage == null){
            errorMessage = getString(R.string.sign_in_failed);
        }

        showSnackBar(errorMessage);
        loginButton.setEnabled(true);
    }


}
