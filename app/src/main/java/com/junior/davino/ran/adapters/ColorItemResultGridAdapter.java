package com.junior.davino.ran.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.interfaces.IGridAdapter;
import com.junior.davino.ran.models.TestItem;

import java.util.List;

/**
 * Created by davin on 14/03/2017.
 */

public class ColorItemResultGridAdapter extends RecyclerView.Adapter<ColorItemResultGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "ColorItemResultGridAdapter";

    private List<TestItem> items;
    private LayoutInflater inflater;
    private Context context;


    public ColorItemResultGridAdapter(Context context, List<TestItem> items){
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = items;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_color_test_result, parent, false);
        int height = parent.getMeasuredHeight() / 5;
        itemView.getLayoutParams().height = height;
        GridViewHolder viewHolder = new GridViewHolder(itemView);
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(GridViewHolder viewHolder, int position) {
        TestItem item = items.get(position);
        GradientDrawable shape = (GradientDrawable)viewHolder.imgView.getBackground();
        shape.setColor(item.getCodeTestNumber());
        if(item.getResult()){ // Acerto
            viewHolder.resultView.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_done_white_24dp, null));
        }
        else{ // Erro
            viewHolder.resultView.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_error_white_24dp, null));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends BaseResultViewHolder {

        ImageView imgView;

        public GridViewHolder(View view){
            super(view);
            imgView = (ImageView)view.findViewById(R.id.item_color_result_view);
        }
    }


}
