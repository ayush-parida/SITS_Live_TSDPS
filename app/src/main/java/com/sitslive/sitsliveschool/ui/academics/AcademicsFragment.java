package com.sitslive.sitsliveschool.ui.academics;

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
import com.sitslive.sitsliveschool.ui.homeworkDetails.HomeworkActivity;
import com.sitslive.sitsliveschool.ui.myAttendance.AttendanceActivity;
import com.sitslive.sitsliveschool.ui.subjectDetails.SubjectDetailsActivity;
import com.sitslive.sitsliveschool.ui.teachersDetails.MyTeachersActivity;
import com.sitslive.sitsliveschool.ui.timetableDetails.TimeTableActivity;

public class AcademicsFragment extends Fragment {

    private AcademicsViewModel academicsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_academics, container, false);
        CardView attendance=v.findViewById(R.id.Attendance);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttendanceActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView timeTable=v.findViewById(R.id.TimeTable);
        timeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TimeTableActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView notices=v.findViewById(R.id.Events);
        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubjectDetailsActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView myTeachers=v.findViewById(R.id.MyTeachers);
        myTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyTeachersActivity.class);
                getActivity().startActivity(intent);
            }
        });
        CardView homework=v.findViewById(R.id.Homework);
        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeworkActivity.class);
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
//        CardView events=v.findViewById(R.id.Events);
//        events.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SubjectDetailsActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
        return v;
    }
}