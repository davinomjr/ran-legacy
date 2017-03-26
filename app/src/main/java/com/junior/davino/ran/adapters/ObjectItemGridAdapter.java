package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.TestItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class ObjectItemGridAdapter extends RecyclerView.Adapter<ObjectItemGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "ObjectItemGridAdapter";

    private List<TestItem> items;
    private LayoutInflater inflater;
    private Context context;

    public ObjectItemGridAdapter(Context context, List<TestItem> items){
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
        TestItem item = items.get(position);
        Picasso.with(context).load(item.getCodeTestNumber()).resize(250,250).noFade().centerCrop().into(viewHolder.imgView);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        ImageView imgView;

        public GridViewHolder(View view){
            super(view);
            imgView = (ImageView)view.findViewById(R.id.item_object_view);
        }
    }
}
