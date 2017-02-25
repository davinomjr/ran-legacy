package com.junior.davino.ran.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.davino.ran.R;
import com.junior.davino.ran.activities.TestActivity;
import com.junior.davino.ran.adapters.GridItemAdapter;
import com.junior.davino.ran.adapters.GridTestItemAdapter;
import com.junior.davino.ran.models.Item;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RanTestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RanTestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RanTestFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Item> items;
    private GridItemAdapter adapter;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public RanTestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RanTestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RanTestFragment newInstance() {
        RanTestFragment fragment = new RanTestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ran_test, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_item);
        mRecyclerView.setHasFixedSize(true);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 8, GridLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        items = ((TestActivity)getActivity()).getItems();
        GridTestItemAdapter adapter = new GridTestItemAdapter(getActivity(),items);
        mRecyclerView.setAdapter(adapter);

//        GridView gridView = (GridView) view.findViewById(R.id.grid_test);
//        gridView.setBackgroundColor(Color.WHITE);
//        gridView.setColumnWidth(GridView.AUTO_FIT);
//        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
//        gridView.setVerticalSpacing(25);
//        gridView.setHorizontalSpacing(25);
//
//        items = ColorBuilder.createListOfColors(gridView, 20);
//        adapter = new GridItemAdapter(view.getContext(),items);
//        gridView.setAdapter(adapter);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
