package com.junior.davino.ran.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestUser;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestUserResultHistory extends Fragment {

    private TestUser testUser;

    public TestUserResultHistory() {
        // Required empty public constructor
    }

    public static TestUserResultHistory newInstance(TestUser user) {
        TestUserResultHistory resultHistoryForm = new TestUserResultHistory();
        Bundle args = new Bundle();
        args.putParcelable("testUser", Parcels.wrap(user));
        resultHistoryForm.setArguments(args);
        return resultHistoryForm;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_user_result_history, container, false);
        testUser = Parcels.unwrap(getArguments().getParcelable("testUser"));

        // TODO: load recyclerView
        return view;
    }

}
