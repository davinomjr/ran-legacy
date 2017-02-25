package com.junior.davino.ran.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.Item;

import java.util.List;

/**
 * Created by davin on 24/02/2017.
 */

public class GridItemAdapter extends ArrayAdapter<Item> {

    private static final String TAG = GridItemAdapter.class.getName();

    public GridItemAdapter(Context context, int resource){
        super(context, resource);
    }
    public GridItemAdapter(Context context, int resource, List<Item> items){
        super(context,resource,items);
    }

    public GridItemAdapter(Context context, List<Item> items){
        super(context,0,items);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_test, parent, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.item_view);
        GradientDrawable shape = (GradientDrawable)imageView.getBackground();
        shape.setColor(item.getOrderNumber());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
        return convertView;
    }
}
