package com.junior.davino.ran.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.Item;
import com.junior.davino.ran.models.enums.EnumTestType;

import java.util.List;

/**
 * Created by davin on 25/02/2017.
 */

public class GridTestItemAdapter extends RecyclerView.Adapter<GridTestItemAdapter.GridViewHolder> {

    private static final String TAG = "GridTestItemAdapter";

    EnumTestType testType;
    private List<Item> items;
    private LayoutInflater inflater;


    public GridTestItemAdapter(Context context, List<Item> items, EnumTestType testType){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
        this.testType = testType;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(testType == EnumTestType.COLORS) {
            itemView = inflater.inflate(R.layout.item_test, parent, false);
        }
        else if(testType == EnumTestType.DIGITS){
            itemView = inflater.inflate(R.layout.item_digit_test, parent, false);
            int height = parent.getMeasuredHeight() / 6;
            itemView.getLayoutParams().height = height;
        }

        GridViewHolder viewHolder = new GridViewHolder(itemView);

        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(GridViewHolder viewHolder, int position) {
        Item item = items.get(position);
        if(testType == EnumTestType.COLORS){
            GradientDrawable shape = (GradientDrawable)viewHolder.imgView.getBackground();
            shape.setColor(item.getOrderNumber());
            viewHolder.imgView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        }
        else if(testType == EnumTestType.DIGITS){
            viewHolder.txtView.setText(String.valueOf(item.getOrderNumber()));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgView;
        public TextView txtView;


        public GridViewHolder(View view){
            super(view);

            if(view == null){
                Log.i(TAG, "View nulla no GridViewHolder para geração de items do teste");
            }

            if(testType == EnumTestType.COLORS){
                imgView = (ImageView)view.findViewById(R.id.item_view);
            }
            else if(testType == EnumTestType.DIGITS){
                txtView = (TextView)view.findViewById(R.id.item_tview);
            }

        }
    }


}
