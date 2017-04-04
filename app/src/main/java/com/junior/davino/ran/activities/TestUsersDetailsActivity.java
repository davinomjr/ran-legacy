package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.junior.davino.ran.R;
import com.junior.davino.ran.code.FirebaseApplication;
import com.junior.davino.ran.fragments.TestUserForm;
import com.junior.davino.ran.fragments.TestUserParentForm;
import com.junior.davino.ran.models.TestUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class TestUsersDetailsActivity extends BaseActivity {

    private static final String TAG = "TestUsersDetailsActivity";
    FirebaseApplication firebaseApp = new FirebaseApplication();
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TestUserForm userFormFragment;
    private TestUserParentForm parentFormFragment;
    private TestUser testUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_users_details);
        testUser = Parcels.unwrap(getIntent().getParcelableExtra("testUser"));

        toolbar = (Toolbar) findViewById(R.id.tabBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager, testUser);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Button updateButton = (Button)findViewById(R.id.btn_update_user);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickButtonListener");
                updateUser();
            }
        });

        Button startTestButton = (Button)findViewById(R.id.btn_start_test);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickButtonListener");
                startActivity(new Intent(TestUsersDetailsActivity.this, HomeTestActivity.class));
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, TestUser user) {
        TestUsersDetailsActivity.ViewPagerAdapter adapter = new TestUsersDetailsActivity.ViewPagerAdapter(getSupportFragmentManager());
        userFormFragment = TestUserForm.newInstance(user, false);
        parentFormFragment = TestUserParentForm.newInstance(user.getParent(), false);

        adapter.addFragment(userFormFragment, getString(R.string.user));
        adapter.addFragment(parentFormFragment, getString(R.string.parent));
        viewPager.setAdapter(adapter);
    }


    private void updateUser(){
        if (!userFormFragment.validateForm() || !parentFormFragment.validateForm()) {
            return;
        }

        try {
            DatabaseReference testUserReferences = database.getReference("users").child(firebaseApp.getFirebaseUserAuthenticateId()).child("testUsers");
            String key = testUser.getUserId();
            testUser = userFormFragment.getUser();
            testUser.setParent(parentFormFragment.getUserParent());
            testUserReferences.child(key).setValue(testUser);
            showSnackBar(getString(R.string.updateSuccess));
        }
        catch (Exception e){
            Log.i(TAG, e.getMessage());
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
