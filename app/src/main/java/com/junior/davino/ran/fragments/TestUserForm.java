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
import android.widget.RadioButton;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestUser;
import com.junior.davino.ran.models.enums.EnumGender;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestUserForm extends Fragment {

    public TestUser user;
    public TextInputLayout inputLayoutName, inputLayoutAge, inputLayoutSchoolGrade;
    public TextInputEditText inputName, inputAge, inputSchoolGrade;
    public RadioButton radioMale, radioFemale;
    private EnumGender currentChoseGender;

    public TestUserForm() {
    }

    public static TestUserForm newInstance(TestUser user, boolean newUser) {
        TestUserForm userFormFragment = new TestUserForm();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        args.putSerializable("newUser", newUser);
        userFormFragment.setArguments(args);
        return userFormFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_user_form, container, false);
        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        inputLayoutAge = (TextInputLayout) view.findViewById(R.id.input_layout_age);
        inputLayoutSchoolGrade = (TextInputLayout) view.findViewById(R.id.input_layout_schoolGrade);
        radioMale = (RadioButton) view.findViewById(R.id.radio_male);
        radioFemale = (RadioButton) view.findViewById(R.id.radio_female);

        inputName = (TextInputEditText) view.findViewById(R.id.input_name);
        inputAge = (TextInputEditText) view.findViewById(R.id.input_age);
        inputSchoolGrade = (TextInputEditText) view.findViewById(R.id.input_schoolGrade);

        inputName.addTextChangedListener(new TextChangeListener(inputName));
        inputAge.addTextChangedListener(new TextChangeListener(inputAge));
        inputSchoolGrade.addTextChangedListener(new TextChangeListener(inputSchoolGrade));

        inputLayoutName.setHintAnimationEnabled(false);
        inputLayoutAge.setHintAnimationEnabled(false);
        inputLayoutSchoolGrade.setHintAnimationEnabled(false);

        user = Parcels.unwrap(getArguments().getParcelable("user"));
        if(!(boolean)getArguments().getSerializable("newUser")){
            fillValues();
        }

        inputLayoutName.setHintAnimationEnabled(true);
        inputLayoutAge.setHintAnimationEnabled(true);
        inputLayoutSchoolGrade.setHintAnimationEnabled(true);
        return view;
    }

    private void fillValues(){
        inputName.setText(user.getName());
        inputAge.setText(String.valueOf(user.getAge()));
        inputSchoolGrade.setText(user.getSchoolGrade());
        if(user.getGender() == null){
            radioMale.setChecked(true);
        }
        else{
            if(user.getGender().equals("Masculino")){
                radioMale.setChecked(true);
            }
            else{
                radioFemale.setChecked(true);
            }
        }

    }

    private String getCurrentSelectedGender() {
        boolean maleChecked = ((RadioButton) getView().findViewById(R.id.radio_male)).isChecked();
        return maleChecked ? "Masculino" : "Feminino";
    }

    private boolean validateName() {
        removeErrorMessage(inputLayoutName);

        if (inputName.getText().toString().trim().isEmpty()) {
            setRequiredMessage(inputLayoutName);
            return false;
        }

        return true;
    }

    private boolean validateAge() {
        removeErrorMessage(inputLayoutAge);

        if (inputAge.getText().toString().trim().isEmpty()) {
            setRequiredMessage(inputLayoutAge);
            return false;
        }


        return true;
    }

    private boolean validateSchoolGrade() {
        removeErrorMessage(inputLayoutSchoolGrade);
        if (inputSchoolGrade.getText().toString().trim().isEmpty()) {
            setRequiredMessage(inputLayoutSchoolGrade);
            return false;
        }

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

    public boolean validateForm() {
        return validateName() & validateAge() & validateSchoolGrade();
    }

    public TestUser getUser(){
        user.setName(inputName.getText().toString());
        user.setAge(Integer.parseInt(inputAge.getText().toString()));
        user.setSchoolGrade(inputSchoolGrade.getText().toString());
        user.setGender(getCurrentSelectedGender());
        return user;
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
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_age:
                    validateAge();
                    break;
                case R.id.input_schoolGrade:
                    validateSchoolGrade();
                    break;
            }
        }
    }
}