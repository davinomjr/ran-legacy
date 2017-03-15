package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class ObjectItemGridAdapter extends RecyclerView.Adapter<ObjectItemGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "ObjectItemGridAdapter";

    private List<Item> items;
    private LayoutInflater inflater;
    private Context context;


    public ObjectItemGridAdapter(Context context, List<Item> items){
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_object_test, parent, false);
        GridViewHolder viewHolder = new GridViewHolder(itemView);
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(GridViewHolder viewHolder, int position) {
        Item item = items.get(position);
        Picasso.with(context).load(item.getOrderNumber()).resize(250,250).noFade().centerCrop().into(viewHolder.imgView);
        viewHolder.imgView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        ImageView imgView;

        public GridViewHolder(View view){
            super(view);

            if(view == null){
                Log.i(TAG, "View nulla no GridViewHolder para geração de items do teste");
            }

            imgView = (ImageView)view.findViewById(R.id.item_object_view);
        }
    }
}
