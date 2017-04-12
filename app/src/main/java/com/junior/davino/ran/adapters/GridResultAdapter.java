package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.enums.EnumTestType;

import java.util.List;

/**
 * Created by davin on 08/03/2017.
 */

public class GridResultAdapter extends RecyclerView.Adapter<GridResultAdapter.GridViewHolder>  {
    private static final String TAG = "GridResultAdapter";

    private List<String> results;
    private LayoutInflater inflater;
    private EnumTestType testType;


    public GridResultAdapter(Context context, List<String> results, EnumTestType testType){
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.results = results;
        this.testType = testType;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public GridResultAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_result_test, parent, false);
        GridResultAdapter.GridViewHolder viewHolder = new GridResultAdapter.GridViewHolder(itemView);
        int height = parent.getMeasuredHeight() / 6;
        itemView.getLayoutParams().height = height;
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(GridResultAdapter.GridViewHolder viewHolder, int position) {
        String result = results.get(position);
        viewHolder.txtView.setText(result);
    }

    public int getItemCount() {
        return results.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder{

        public TextView txtView;


        public GridViewHolder(View view){
            super(view);
            txtView = (TextView)view.findViewById(R.id.item_resultTview);
            if(testType != EnumTestType.COLORS && testType != EnumTestType.OBJECTS){
                txtView.setTextSize(25);
            }
        }
    }


}
