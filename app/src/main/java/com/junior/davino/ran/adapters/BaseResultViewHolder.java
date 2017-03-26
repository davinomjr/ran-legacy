package com.junior.davino.ran.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.junior.davino.ran.R;

/**
 * Created by davin on 20/03/2017.
 */

public class BaseResultViewHolder extends RecyclerView.ViewHolder {

    public ImageView resultView;

    public BaseResultViewHolder(View v){
        super(v);
        resultView = (ImageView)v.findViewById(R.id.item_result_check);
    }
}
