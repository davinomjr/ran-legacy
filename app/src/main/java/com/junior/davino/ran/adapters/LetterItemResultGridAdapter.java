package com.junior.davino.ran.adapters;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
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

public class LetterItemResultGridAdapter extends RecyclerView.Adapter<LetterItemResultGridAdapter.GridViewHolder> implements IGridAdapter {

    private static final String TAG = "LetterItemResultGridAdapter";

    private List<TestItem> items;
    private LayoutInflater inflater;
    private Context context;


    public LetterItemResultGridAdapter(Context context, List<TestItem> items){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
    }


    /**
     * Chamado quando é necessário criar uma view
     */
    @Override
    public LetterItemResultGridAdapter.GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_digit_test_result, parent, false);
        LetterItemResultGridAdapter.GridViewHolder viewHolder = new LetterItemResultGridAdapter.GridViewHolder(itemView);
        int height = (int) (parent.getMeasuredHeight() / 4.5);
        itemView.getLayoutParams().height = height;
        return viewHolder;
    }

    /**
     * Vincula dados da lista a view
     */
    @Override
    public void onBindViewHolder(LetterItemResultGridAdapter.GridViewHolder viewHolder, int position) {
        TestItem item = items.get(position);
        viewHolder.txtView.setText(item.getName().toString());
        viewHolder.resultView.setVisibility(View.INVISIBLE);
        if(item.getResult()){ // Acerto
            viewHolder.resultView.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_done_black_24dp, null));
        }
        else{ // Erro
            viewHolder.resultView.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_error_black_24dp, null));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class GridViewHolder extends BaseResultViewHolder {

        public TextView txtView;

        public GridViewHolder(View view){
            super(view);
            txtView = (TextView)view.findViewById(R.id.item_result_tview);
        }
    }


}
