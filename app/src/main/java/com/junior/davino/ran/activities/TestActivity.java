package com.junior.davino.ran.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.junior.davino.ran.R;
import com.junior.davino.ran.fragments.RanTestFragment;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.utils.ColorBuilder;

import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class TestActivity extends FragmentActivity implements RanTestFragment.OnFragmentInteractionListener {

    private List<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        items = ColorBuilder.createListOfColors(20);
        RanTestFragment fragment = RanTestFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_test_container, fragment)
                .commit();
    }

    public void onFragmentInteraction(Uri uri){

    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
