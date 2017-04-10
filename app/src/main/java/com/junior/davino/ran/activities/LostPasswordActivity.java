package com.junior.davino.ran.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.junior.davino.ran.R;

public class LostPasswordActivity extends BaseActivity {

    private static final String TAG = "LostPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_password);

        Button btnRecover = (Button)findViewById(R.id.btn_recover_password);
        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickRecoverPassword");
                recoverPassword();
            }
        });
    }

    private void recoverPassword(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        TextInputLayout emailLayout = (TextInputLayout)findViewById(R.id.input_layout_email);
        TextInputEditText emailEditText = (TextInputEditText)findViewById(R.id.input_email);

        removeErrorMessage(emailLayout);
        if(emailEditText.getText().toString().trim().isEmpty()){
            setRequiredMessage(emailLayout);
            return;
        }

        firebaseApp.sendEmailRecoveryPassword(emailEditText.getText().toString());
        showSnackBar(getString(R.string.recoverSuccessfull));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void requestFocus(View v) {
        if (v.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void setRequiredMessage(TextInputLayout textLayout) {
        textLayout.setError(String.format(getString(R.string.err_required_input), textLayout.getHint()));
        requestFocus(textLayout);
    }

    private void removeErrorMessage(TextInputLayout textLayout){
        textLayout.setErrorEnabled(false);
    }
}
