package com.junior.davino.ran.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private Context context;


    public GridTestItemAdapter(Context context, List<Item> items, EnumTestType testType){
        this.context = context;
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
        else if(testType == EnumTestType.OBJECTS){
            itemView = inflater.inflate(R.layout.item_object_test, parent, false);
        }
        else{
//        else if(testType == EnumTestType.DIGITS){
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
        else if(testType == EnumTestType.OBJECTS){
            Glide.with(context)
                    .load("")
                    .placeholder(getObjectImage(item.getOrderNumber()))
                    .into(viewHolder.imgView);

//            viewHolder.imgView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        }
        else {//if(testType == EnumTestType.DIGITS){
            viewHolder.txtView.setText(String.valueOf(item.getName()));
        }
    }

    private Drawable getObjectImage(int code){
        switch(code){
            case 1:
                return context.getResources().getDrawable(R.drawable.cachorro);
            case 2:
                return context.getResources().getDrawable(R.drawable.tesoura);
            case 3:
                return context.getResources().getDrawable(R.drawable.pente);
            case 4:
                return context.getResources().getDrawable(R.drawable.panela);
            case 5:
                return context.getResources().getDrawable(R.drawable.bola);
            default: throw new IllegalArgumentException("Imagem não encontrada");
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
            else if(testType == EnumTestType.OBJECTS){
                imgView = (ImageView)view.findViewById(R.id.item_object_view);
            }
            else { // DIGIS OR LETTERS
                txtView = (TextView)view.findViewById(R.id.item_tview);
            }
        }
    }


}
