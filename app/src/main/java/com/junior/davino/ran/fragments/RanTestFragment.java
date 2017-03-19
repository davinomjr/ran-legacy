package com.junior.davino.ran.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.TestItem;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.factories.AdapterFactory;

import java.util.ArrayList;
import java.util.List;

public class RanTestFragment extends Fragment {

    private static final String TAG = "RanTestFragment";

    private List<TestItem> items;
    private RecyclerView mRecyclerView;


    public RanTestFragment() {
        // Required empty public constructor
    }


    public static RanTestFragment newInstance(EnumTestType testType, ArrayList<TestItem> items) {
        RanTestFragment fragment = new RanTestFragment();
        Bundle args = new Bundle();
        args.putSerializable("option", testType);
        args.putSerializable("items", items);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ran_test, container, false);

        Context context = getActivity();
        EnumTestType option = (EnumTestType) getArguments().getSerializable("option");
        items = (List<TestItem>)getArguments().getSerializable("items");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_item);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        IGridAdapter adapter = AdapterFactory.buildAdapter(context, items, option);
        mRecyclerView.setAdapter((RecyclerView.Adapter) adapter);
        return view;
    }
}
