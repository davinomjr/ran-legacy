package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.junior.davino.ran.R;
import com.junior.davino.ran.adapters.UserAdapter;
import com.junior.davino.ran.models.TestUser;

import org.parceler.Parcels;

import java.util.ArrayList;

public class TestUsersActivity extends BaseActivity implements View.OnClickListener {

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private UserAdapter mMyAdapter;
    private ArrayList<TestUser> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    private FloatingActionButton fabButton;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        progressDialog = new MaterialDialog.Builder(TestUsersActivity.this)
                .title(R.string.loading)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        progressDialog.show();

        fabButton = (FloatingActionButton) findViewById(R.id.btn_fab);
        fabButton.setOnClickListener(this);

        handleInstanceState(savedInstanceState);
        setupFirebase();
        setupRecyclerview();
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

    private void setupFirebase() {
        mQuery = database.getReference("users").child(firebaseApp.getFirebaseAuth().getCurrentUser().getUid()).child("testUsers");
    }

    private void setupRecyclerview() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_users);
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

        mMyAdapter = new UserAdapter(this, mQuery, mAdapterItems, mAdapterKeys, new UserAdapter.OnItemClickListener(){
            @Override public void onItemClick(final String testUserId) {
                // Attach a listener to read the data at our posts reference
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        TestUser testUser = dataSnapshot.child(testUserId).getValue(TestUser.class);
                        Intent intent = new Intent(TestUsersActivity.this, TestUsersDetailsActivity.class);
                        intent.putExtra("testUser", Parcels.wrap(testUser));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                Log.i("TestUsersActivity", "CLICKD and USER ID = " + testUserId);
            }

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.action_signout){
            firebaseApp.logoff();
            Intent intent = new Intent(TestUsersActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    // Saving the list of items and keys of the items on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mMyAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mMyAdapter.getKeys());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAdapter.destroy();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_fab){
            startActivity(new Intent(this, RegisterTestUserActivity.class));
        }
    }

    public void dismissProgressLoading(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}