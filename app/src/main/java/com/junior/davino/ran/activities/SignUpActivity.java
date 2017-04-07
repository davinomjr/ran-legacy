package com.junior.davino.ran.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.junior.davino.ran.R;

public class SignUpActivity extends BaseActivity {

    EditText inputName, inputEmail, inputPassword;
    TextInputLayout nameLayout, emailLayout, passwordLayout;
    Button signUpButton;
    MaterialDialog processingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        emailLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        passwordLayout = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        signUpButton = (Button) findViewById(R.id.btn_signup);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signUpButton.setEnabled(false);

        processingDialog = new MaterialDialog.Builder(SignUpActivity.this)
                .title(R.string.progress_dialog)
                .content("Criando...")
                .progress(true, 0)
                .show();

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();


        firebaseApp.createNewUser(this, name, email, password, getString(R.string.err_msg_signup));
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


    public void onSignupSuccess() {
        processingDialog.dismiss();
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        processingDialog.dismiss();
        showSnackBar(getString(R.string.sign_in_failed));
        signUpButton.setEnabled(true);
    }

}
