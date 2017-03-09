package com.junior.davino.ran.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junior.davino.ran.R;
import com.junior.davino.ran.activities.TestActivity;
import com.junior.davino.ran.adapters.GridTestItemAdapter;
import com.junior.davino.ran.models.enums.EnumTestType;
import com.junior.davino.ran.models.Item;

import java.util.List;

public class RanTestFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Item> items;


    private RecyclerView mRecyclerView;


    public RanTestFragment() {
        // Required empty public constructor
    }


    public static RanTestFragment newInstance(EnumTestType testType) {
        RanTestFragment fragment = new RanTestFragment();
        Bundle args = new Bundle();
        args.putSerializable("option", testType);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ran_test, container, false);

        EnumTestType option = (EnumTestType) getArguments().getSerializable("option");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_item);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 6, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);


        items = ((TestActivity)getActivity()).getItems();
        GridTestItemAdapter adapter = new GridTestItemAdapter(getActivity(),items, option);
        mRecyclerView.setAdapter(adapter);


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
