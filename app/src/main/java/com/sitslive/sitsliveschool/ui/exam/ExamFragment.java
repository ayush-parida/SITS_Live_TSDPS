package com.sitslive.sitsliveschool.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sitslive.sitsliveschool.MainActivity;
import com.sitslive.sitsliveschool.R;
import com.sitslive.sitsliveschool.ui.examReport.ExamReportActivity;
import com.sitslive.sitsliveschool.ui.examSchedule.ExamScheduleActivity;

public class ExamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exam, container, false);
        CardView personalDetails=v.findViewById(R.id.FeeSummary);
        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExamScheduleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView feeDueDetails=v.findViewById(R.id.FeeDueDetails);
        feeDueDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExamReportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        return v;
    }}