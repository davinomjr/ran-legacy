package com.junior.davino.ran.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junior.davino.ran.R;
import com.junior.davino.ran.models.TestResult;
import com.junior.davino.ran.models.enums.EnumTestType;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryResultFragment extends Fragment  {


    private static final String TAG = "SummaryResultFragment";
    private TestResult resultSummary;

    public SummaryResultFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary_result, container, false);
        Bundle bundle = getArguments();
        resultSummary = Parcels.unwrap(bundle.getParcelable("result"));
        EnumTestType testType = (EnumTestType) bundle.getSerializable("option");


        TextView timeTv = (TextView) view.findViewById(R.id.timeTv);
        TextView meanTimeTv = (TextView) view.findViewById(R.id.mean_timeTv);
        TextView stimuliNumber = (TextView) view.findViewById(R.id.stimuli_number);
        TextView matchesTv = (TextView) view.findViewById(R.id.hits_number);
        TextView missesTv = (TextView) view.findViewById(R.id.misses_number);

        timeTv.setText(String.format(getString(R.string.time), String.valueOf(resultSummary.getResultTime())));
        meanTimeTv.setText(String.format(getString(R.string.meanTime), String.valueOf(resultSummary.getMeanResultTime())));
        stimuliNumber.setText(String.format(getString(R.string.sNumberStimuli), String.valueOf(resultSummary.getStimuliCount())));
        matchesTv.setText(String.format(getString(R.string.sNumberMatches),  String.valueOf(resultSummary.getHitsCount())));
        missesTv.setText(String.format(getString(R.string.sNumberMiss), String.valueOf(resultSummary.getMissesCount())));

//        if(testType == EnumTestType.LETTERS){
//            matchesTv.setVisibility(View.INVISIBLE);
//            missesTv.setVisibility(View.INVISIBLE);
//        }

        return view;
    }
}
