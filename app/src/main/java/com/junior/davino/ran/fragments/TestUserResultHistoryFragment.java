package com.junior.davino.ran.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.junior.davino.ran.R;
import com.junior.davino.ran.activities.ResultActivity;
import com.junior.davino.ran.adapters.TestResultHistoryAdapter;
import com.junior.davino.ran.code.FirebaseApplication;
import com.junior.davino.ran.interfaces.OnItemClickListener;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.TestResult;
import com.junior.davino.ran.models.TestUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestUserResultHistoryFragment extends Fragment {

    private final static String TAG = "TestUserResultHistoryFragment";
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseApplication firebaseApplication = new FirebaseApplication();
    private TestUser testUser;
    private TestResultHistoryAdapter mAdapter;
    private ArrayList<TestResult> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    public TestUserResultHistoryFragment() {
    }

    public static TestUserResultHistoryFragment newInstance(TestUser user) {
        TestUserResultHistoryFragment resultHistoryForm = new TestUserResultHistoryFragment();
        Bundle args = new Bundle();
        args.putParcelable("testUser", Parcels.wrap(user));
        resultHistoryForm.setArguments(args);
        return resultHistoryForm;
    }

    private void setupFirebase() {
        mQuery = database.getReference("users").child(testUser.getUserId()).child("testUsers").child(testUser.getTestUserId()).child("testResults");
    }

    private void setupRecyclerview(View recycView) {
        RecyclerView recyclerView = (RecyclerView) recycView;
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.i("TestUsersActivity", "CLICKED");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                //progressDialog.dismiss();
            }
        });

        mAdapter = new TestResultHistoryAdapter(getActivity(), mQuery, mAdapterItems, mAdapterKeys, new OnItemClickListener(){
            @Override public void onItemClick(final String testResultId) {
                // Attach a listener to read the data at our posts reference
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final TestResult testResult = dataSnapshot.child(testResultId).getValue(TestResult.class);
                        final List<TestItem> items = new LinkedList<TestItem>();
                        Query mQueryItems = database.getReference("users").child(testUser.getUserId()).child("testUsers").child(testUser.getTestUserId()).child("testResults").child(testResultId).child("testItems");
                        mQueryItems.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                                    TestItem testItem = itemSnapshot.getValue(TestItem.class);
                                    items.add(testItem);
                                }

                                Log.i(TAG,"VAI CHAMAR RESULTACTIVITY");

                                Intent intent = new Intent(getActivity(), ResultActivity.class);
                                intent.putExtra("result", Parcels.wrap(testResult));
                                intent.putExtra("items", Parcels.wrap(items));
                                startActivity(intent);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {}
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<>();
            mAdapterKeys = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test_user_result_history, container, false);
        testUser = Parcels.unwrap(getArguments().getParcelable("testUser"));

        handleInstanceState(savedInstanceState);
        setupFirebase();
        setupRecyclerview(view.findViewById(R.id.rv_resultHistory));
        Log.i(TAG, "FINISHED LOADING");
        return view;
    }

    // Saving the list of items and keys of the items on rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }
}
