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

import com.junior.davino.ran.R;
import com.junior.davino.ran.fragments.SummaryResultFragment;
import com.junior.davino.ran.fragments.TestResultFragment;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.TestResult;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.speech.VoiceController;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "ResultActivity";
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TestResult result;
    private EnumTestType testType;
    private String audioFilePath;
    private List<TestItem> items;

    public List<TestItem> getItems() {
        return items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViewById(R.id.btn_play_audio).setOnClickListener(this);

        Intent intent = getIntent();
        result = Parcels.unwrap(intent.getExtras().getParcelable("result"));
        items = Parcels.unwrap(intent.getExtras().getParcelable("items"));
        testType = result.getTestType();
        audioFilePath = result.getAudioPath();

        Log.i(TAG, "FILE PATH = " + audioFilePath);

        toolbar = (Toolbar) findViewById(R.id.tabBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putParcelable("result", Parcels.wrap(result));
        bundle.putSerializable("option", testType);

        SummaryResultFragment summaryFragment = new SummaryResultFragment();
        TestResultFragment testResultFragment = new TestResultFragment();

        summaryFragment.setArguments(bundle);
        testResultFragment.setArguments(bundle);

        adapter.addFragment(summaryFragment, getString(R.string.resultTabTitle1));
        adapter.addFragment(testResultFragment, getString(R.string.test));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick");
        if (v.getId() == R.id.btn_play_audio) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        VoiceController.playAudio(audioFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            thread.start();
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
