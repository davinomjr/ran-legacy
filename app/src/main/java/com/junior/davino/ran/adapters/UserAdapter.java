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
import com.junior.davino.ran.interfaces.OnLayoutListenerReady;
import com.junior.davino.ran.models.TestUser;

import java.util.ArrayList;

/**
 * Created by davin on 26/03/2017.
 */

public class UserAdapter extends FirebaseRecyclerAdapter<UserAdapter.ViewHolder, TestUser> {

    private final OnItemClickListener listener;
    private OnLayoutListenerReady recyclerViewListener;
    private final Context context;
    private int totalItemsSize;

    public UserAdapter(Context context, Query query, @Nullable ArrayList<TestUser> items, @Nullable ArrayList<String> keys, OnItemClickListener listener, OnLayoutListenerReady recyclerViewListener) {
        super(query, items, keys);
        this.listener = listener;
        this.context = context;
        this.recyclerViewListener = recyclerViewListener;
        totalItemsSize = items.size();
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        TestUser item = getItem(position);
        holder.textViewName.setText(item.getName());
        holder.textViewAgeGrade.setText(String.format("%d anos - %s", item.getAge(),item.getSchoolGrade()));
        holder.bindClick(item.getTestUserId(), listener);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewAgeGrade;
        View view;

        public ViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.user_name);
            textViewAgeGrade = (TextView) view.findViewById(R.id.user_age_grade);
            this.view = view;
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
    protected void itemAdded(TestUser item, String key, int position) {
        Log.d("UserAdapter", "Added a new item to the adapter.");
        if(position == totalItemsSize){
            recyclerViewListener.onLayoutReady();
        }
    }

    @Override
    protected void itemChanged(TestUser oldItem, TestUser newItem, String key, int position) {
        Log.d("UserAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(TestUser item, String key, int position) {
        Log.d("UserAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(TestUser item, String key, int oldPosition, int newPosition) {
        Log.d("UserAdapter", "Moved an item.");
    }
}