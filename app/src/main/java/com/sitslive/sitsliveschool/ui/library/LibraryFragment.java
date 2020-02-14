package com.sitslive.sitsliveschool.ui.library;

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
import com.sitslive.sitsliveschool.ui.fee.FeeViewModel;
import com.sitslive.sitsliveschool.ui.feeDueDetails.FeeDueDetailsActivity;
import com.sitslive.sitsliveschool.ui.feePaidDetails.FeePaidDetailsActivity;
import com.sitslive.sitsliveschool.ui.feeSummary.FeeSummaryActivity;

public class LibraryFragment extends Fragment {

    private FeeViewModel feeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fee, container, false);
        CardView feeSummary=v.findViewById(R.id.FeeSummary);
        feeSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeeSummaryActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView feeDueDetails=v.findViewById(R.id.FeeDueDetails);
        feeDueDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeeDueDetailsActivity.class);
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
    }
}