package com.junior.davino.ran.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.davino.ran.R;
import com.junior.davino.ran.activities.ResultActivity;
import com.junior.davino.ran.models.enums.EnumTestType;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestResultFragment extends Fragment {

    public TestResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_test_result, container, false);
        Bundle bundle = getArguments();
        EnumTestType option = (EnumTestType) bundle.getSerializable("option");

        RanTestResultFragment fragment = RanTestResultFragment.newInstance(option, ((ResultActivity) getActivity()).getItems());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();

        return v;
    }
}
