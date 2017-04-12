package com.junior.davino.ran.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestUserParent;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestUserParentForm extends Fragment {

    TestUserParent parent;
    private TextInputLayout inputLayoutParentName, inputLayoutParentEmail, inputLayoutParentPhone;
    private TextInputEditText inputParentName, inputParentEmail, inputParentPhone;

    public TestUserParentForm() {
    }

    public static TestUserParentForm newInstance(TestUserParent parent, boolean newUser) {
        TestUserParentForm parentUserFormFragment = new TestUserParentForm();
        Bundle args = new Bundle();
        args.putParcelable("parent", Parcels.wrap(parent));
        args.putSerializable("newUser", newUser);
        parentUserFormFragment.setArguments(args);
        return parentUserFormFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_user_parent_form, container, false);
        parent = Parcels.unwrap(getArguments().getParcelable("parent"));

        inputLayoutParentName = (TextInputLayout) view.findViewById(R.id.input_layout_parent_name);
        inputLayoutParentEmail = (TextInputLayout) view.findViewById(R.id.input_layout_parent_email);
        inputLayoutParentPhone = (TextInputLayout) view.findViewById(R.id.input_layout_parent_phone);

        inputParentName = (TextInputEditText) view.findViewById(R.id.input_parent_name);
        inputParentEmail = (TextInputEditText) view.findViewById(R.id.input_parent_email);
        inputParentPhone = (TextInputEditText) view.findViewById(R.id.input_parent_phone);


        inputLayoutParentName.setHintAnimationEnabled(false);
        inputLayoutParentEmail.setHintAnimationEnabled(false);
        inputLayoutParentPhone.setHintAnimationEnabled(false);

        inputParentName.addTextChangedListener(new TextChangeListener(inputParentName));
        inputParentEmail.addTextChangedListener(new TextChangeListener(inputParentEmail));
        inputParentPhone.addTextChangedListener(new TextChangeListener(inputParentPhone));

        if(!(boolean)getArguments().getSerializable("newUser")){
            fillValues();
        }

        inputLayoutParentName.setHintAnimationEnabled(true);
        inputLayoutParentEmail.setHintAnimationEnabled(true);
        inputLayoutParentPhone.setHintAnimationEnabled(true);

        return view;
    }

    private void fillValues(){
        inputParentName.setText(parent.getName());
        inputParentEmail.setText(parent.getEmail());
        inputParentPhone.setText(parent.getPhone());
    }


    private boolean validateParentName() {
//        removeErrorMessage(inputLayoutParentName);
//        if (inputParentName.getText().toString().trim().isEmpty()) {
//            setRequiredMessage(inputLayoutParentName);
//            return false;
//        }

        return true;
    }

    private boolean validateParentEmail() {
//        removeErrorMessage(inputLayoutParentEmail);
//        if (inputParentEmail.getText().toString().trim().isEmpty()) {
//            setRequiredMessage(inputLayoutParentEmail);
//            return false;
//        }

        return true;
    }

    private boolean validateParentPhone() {
//        removeErrorMessage(inputLayoutParentPhone);
//        if (inputParentPhone.getText().toString().trim().isEmpty()) {
//            setRequiredMessage(inputLayoutParentPhone);
//            return false;
//        }

        return true;
    }

    private void requestFocus(View v) {
        if (v.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void setRequiredMessage(TextInputLayout textLayout) {
        textLayout.setError(String.format(getString(R.string.err_required_input), textLayout.getHint()));
        requestFocus(textLayout);
    }

    private void removeErrorMessage(TextInputLayout textLayout){
        textLayout.setErrorEnabled(false);
    }

    public boolean validateForm(){
        //return validateParentName() & validateParentEmail() & validateParentPhone();
        return true;
    }

    public TestUserParent getUserParent(){
        parent.setName(inputParentName.getText().toString());
        parent.setEmail(inputParentEmail.getText().toString());
        parent.setPhone(inputParentPhone.getText().toString());
        return parent;
    }

    private class TextChangeListener implements TextWatcher {

        private View view;

        private TextChangeListener(View view) {
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
            switch (view.getId()) {
                case R.id.input_parent_email:
                    validateParentEmail();
                    break;
                case R.id.input_parent_name:
                    validateParentName();
                    break;
                case R.id.input_parent_phone:
                    validateParentPhone();
                    break;
            }
        }
    }
}