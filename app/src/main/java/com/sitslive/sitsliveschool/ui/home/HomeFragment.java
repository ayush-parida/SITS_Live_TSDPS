package com.sitslive.sitsliveschool.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.sitslive.sitsliveschool.R;
import com.sitslive.sitsliveschool.ui.examSchedule.ExamScheduleActivity;
import com.sitslive.sitsliveschool.ui.feeSummary.FeeSummaryActivity;
import com.sitslive.sitsliveschool.ui.homeworkDetails.HomeworkActivity;
import com.sitslive.sitsliveschool.ui.profile.ProfileFragment;
import com.sitslive.sitsliveschool.ui.profileDetails.MyProfileActivity;
import com.sitslive.sitsliveschool.ui.teachersDetails.MyTeachersActivity;
import com.sitslive.sitsliveschool.ui.transportDetails.TransportActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        TextView uname=(TextView)v.findViewById(R.id.user_name);
//        uname.setText(GlobalVariables.uname);
//        TextView uphone=(TextView)v.findViewById(R.id.user_id);
//        uphone.setText(GlobalVariables.uphone);
//
//
//        ImageView uimagee=(ImageView)v.findViewById(R.id.user_photo);
//        uimagee.setImageBitmap(GlobalVariables.uimage);


        CardView personalDetails=v.findViewById(R.id.PersonalDetails);
        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
//                getActivity().startActivity(intent);
                ProfileFragment profileFragment=new ProfileFragment();
                FragmentManager manager=getFragmentManager();
                manager.beginTransaction().replace(R.id.homeLayout,profileFragment,profileFragment.getTag()).commit();

            }
        });
        CardView fee=v.findViewById(R.id.fee);
        fee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeeSummaryActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView examSchedule=v.findViewById(R.id.examSchedule);
        examSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ExamScheduleActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView transport=v.findViewById(R.id.transport);
        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransportActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView academics=v.findViewById(R.id.academics);
        academics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTeachersActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView homework=v.findViewById(R.id.homework);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeworkActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return v;
    }

}