package com.junior.davino.ran.activities;

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
import com.junior.davino.ran.fragments.TestUserForm;
import com.junior.davino.ran.fragments.TestUserParentForm;
import com.junior.davino.ran.models.TestUser;

import java.util.ArrayList;
import java.util.List;

public class RegisterTestUserActivity extends BaseActivity {

    private static final String TAG = "RegisterTestUserActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TestUserForm userFormFragment;
    private TestUserParentForm parentFormFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_test_user);

        toolbar = (Toolbar) findViewById(R.id.tabBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Button registerButton = (Button) findViewById(R.id.btn_register_user);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClickButtonListener");
                registerUser();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        TestUser user = new TestUser();
        userFormFragment = TestUserForm.newInstance(user, true);
        parentFormFragment = TestUserParentForm.newInstance(user.getParent(), true);

        adapter.addFragment(userFormFragment, getString(R.string.user));
        adapter.addFragment(parentFormFragment, getString(R.string.parent));
        viewPager.setAdapter(adapter);
    }

    private void registerUser() {
        if (!userFormFragment.validateForm() || !parentFormFragment.validateForm()) {
            return;
        }

        try {
            String userId = firebaseApp.getFirebaseAuth().getCurrentUser().getUid();
            DatabaseReference testUserReferences = database.getReference("users").child(userId).child("testUsers");
            String key = testUserReferences.push().getKey();
            TestUser testUser = userFormFragment.getUser();
            testUser.setUserId(userId);
            testUser.setTestUserId(key);
            testUser.setParent(parentFormFragment.getUserParent());
            testUserReferences.child(key).setValue(testUser);
            showSnackBar(getString(R.string.createSuccess));
            finish();
        } catch (Exception e) {
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
