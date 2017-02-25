package com.junior.davino.ran.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.Item;

import java.util.List;

/**
 * Created by davin on 25/02/2017.
 */

public class GridTestItemAdapter extends RecyclerView.Adapter<GridTestItemAdapter.GridViewHolder> {

    private List<Item> items;
    private LayoutInflater inflater;


    public GridTestItemAdapter(Context context, List<Item> items){
        this.items = items;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    /**
     * Chamado quando é necessário criar uma view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_test, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(itemView);
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(GridViewHolder viewHolder, int position) {
        Item item = items.get(position);
        GradientDrawable shape = (GradientDrawable)viewHolder.imgView.getBackground();
        shape.setColor(item.getOrderNumber());
        viewHolder.imgView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        viewHolder.imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgView;


        public GridViewHolder(View view){
            super(view);
            imgView = (ImageView)view.findViewById(R.id.item_view);
        }



    }


}
