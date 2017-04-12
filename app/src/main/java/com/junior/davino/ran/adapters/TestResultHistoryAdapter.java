package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.Query;
import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.OnItemClickListener;
import com.junior.davino.ran.models.TestResult;

import java.util.ArrayList;

/**
 * Created by davin on 27/02/2017.
 */

public class TestResultHistoryAdapter extends FirebaseRecyclerAdapter<TestResultHistoryAdapter.ViewHolder, TestResult> {

    private final OnItemClickListener listener;
    private LayoutInflater inflater;
    private Context context;
    private static final String TAG = "TestResultHistoryAdapter";


    public TestResultHistoryAdapter(Context context, Query query, @Nullable ArrayList<TestResult> items, @Nullable ArrayList<String> keys, OnItemClickListener listener){
        super(query, items, keys);
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listener = listener;
    }

    @Override
    public TestResultHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_result_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestResultHistoryAdapter.ViewHolder holder, int position) {
        TestResult item = getItem(position);
        holder.resultName.setText(item.toString());
        holder.bindClick(item.getResultId(), listener);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView resultName;


        public ViewHolder(View v){
            super(v);
            this.view = v;
            resultName =  (TextView) view.findViewById(R.id.result_history_name);
        }

        public void bindClick(final String userId, final OnItemClickListener listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(userId);
                }
            });
        }
    }

    @Override
    protected void itemAdded(TestResult item, String key, int position) {
        Log.d(TAG, "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(TestResult oldItem, TestResult newItem, String key, int position) {
        Log.d(TAG, "Changed an item.");
    }

    @Override
    protected void itemRemoved(TestResult item, String key, int position) {
        Log.d(TAG, "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(TestResult item, String key, int oldPosition, int newPosition) {
        Log.d(TAG, "Moved an item.");
    }
}
