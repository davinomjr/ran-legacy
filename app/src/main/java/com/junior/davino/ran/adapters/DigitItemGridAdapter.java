package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.TestItem;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class DigitItemGridAdapter extends RecyclerView.Adapter<DigitItemGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "DigitItemGridAdapter";

    private List<TestItem> items;
    private LayoutInflater inflater;
    private Context context;


    public DigitItemGridAdapter(Context context, List<TestItem> items){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public DigitItemGridAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_digit_test, parent, false);
        DigitItemGridAdapter.GridViewHolder viewHolder = new DigitItemGridAdapter.GridViewHolder(itemView);
        int height = parent.getMeasuredHeight() / 5;
        itemView.getLayoutParams().height = height;
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(DigitItemGridAdapter.GridViewHolder viewHolder, int position) {
        TestItem item = items.get(position);
        viewHolder.txtView.setText(item.getName().toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        public TextView txtView;


        public GridViewHolder(View view){
            super(view);
            txtView = (TextView)view.findViewById(R.id.item_tview);
        }
    }
}
