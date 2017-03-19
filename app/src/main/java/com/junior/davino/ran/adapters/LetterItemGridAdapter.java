package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class LetterItemGridAdapter extends RecyclerView.Adapter<LetterItemGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "LetterItemGridAdapter";

    private List<TestItem> items;
    private LayoutInflater inflater;
    private Context context;


    public LetterItemGridAdapter(Context context, List<TestItem> items){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public LetterItemGridAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_digit_test, parent, false);
        LetterItemGridAdapter.GridViewHolder viewHolder = new LetterItemGridAdapter.GridViewHolder(itemView);
        int height = parent.getMeasuredHeight() / 6;
        itemView.getLayoutParams().height = height;
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(LetterItemGridAdapter.GridViewHolder viewHolder, int position) {
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

            if(view == null){
                Log.i(TAG, "View nulla no GridViewHolder para geração de items do teste");
            }

            txtView = (TextView)view.findViewById(R.id.item_tview);
        }
    }


}
