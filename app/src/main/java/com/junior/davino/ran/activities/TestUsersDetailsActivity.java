package com.junior.davino.ran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DatabaseReference;
import com.junior.davino.ran.R;
import com.junior.davino.ran.code.FirebaseApplication;
import com.junior.davino.ran.fragments.TestUserForm;
import com.junior.davino.ran.fragments.TestUserParentForm;
import com.junior.davino.ran.fragments.TestUserResultHistoryFragment;
import com.junior.davino.ran.models.TestUser;
import com.junior.davino.ran.models.TestUserParent;

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
    private TestUserResultHistoryFragment resultHistoryFragment;
    private TestUser testUser;
    private MaterialDialog confirmationDialog;

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
                updateUser();
            }
        });

        Button startTestButton = (Button)findViewById(R.id.btn_start_test);
        startTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestUsersDetailsActivity.this, HomeTestActivity.class);
                intent.putExtra("user", Parcels.wrap(testUser));
                startActivity(intent);
            }
        });

        Button removeButton = (Button)findViewById(R.id.btn_delete_user);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialog = new MaterialDialog.Builder(TestUsersDetailsActivity.this)
                        .title(R.string.confirmation)
                        .content(R.string.confirmation_delete_msg)
                        .positiveText(R.string.yes)
                        .negativeText(R.string.no)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                removeUser();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                confirmationDialog.dismiss();
                            }
                        }).build();

                confirmationDialog.show();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager, TestUser user) {
        TestUsersDetailsActivity.ViewPagerAdapter adapter = new TestUsersDetailsActivity.ViewPagerAdapter(getSupportFragmentManager());
        userFormFragment = TestUserForm.newInstance(user, false);
        parentFormFragment = TestUserParentForm.newInstance(user.getParent(), false);
        resultHistoryFragment = TestUserResultHistoryFragment.newInstance(user);

        adapter.addFragment(userFormFragment, getString(R.string.user));
        adapter.addFragment(parentFormFragment, getString(R.string.parent));
        adapter.addFragment(resultHistoryFragment, getString(R.string.resultHistory));
        viewPager.setAdapter(adapter);
    }


    private void updateUser(){
        if (!userFormFragment.validateForm() || !parentFormFragment.validateForm()) {
            return;
        }

        try {
            DatabaseReference testUserReferences = database.getReference("users").child(testUser.getUserId()).child("testUsers");
            String key = testUser.getTestUserId();
            testUser = userFormFragment.getUser();
            DatabaseReference userReference = testUserReferences.child(key);
            userReference.child("name").setValue(testUser.getName());
            userReference.child("age").setValue(testUser.getAge());
            userReference.child("gender").setValue(testUser.getGender());
            userReference.child("schoolGrade").setValue(testUser.getSchoolGrade());

            TestUserParent testUserParent = testUser.getParent();
            DatabaseReference userParentReference = testUserReferences.child(key).child("parent");
            userParentReference.child("name").setValue(testUserParent.getName());
            userParentReference.child("email").setValue(testUserParent.getEmail());
            userParentReference.child("phone").setValue(testUserParent.getPhone());
            showSnackBar(getString(R.string.updateSuccess));
        }
        catch (Exception e){
            Log.i(TAG, e.getMessage());
        }
    }

    private void removeUser(){
        Log.i(TAG, "removeUser");
        DatabaseReference testUserReferences = database.getReference("users").child(testUser.getUserId()).child("testUsers");
        String key = testUser.getTestUserId();
        testUserReferences.child(key).removeValue();
        finish();
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
