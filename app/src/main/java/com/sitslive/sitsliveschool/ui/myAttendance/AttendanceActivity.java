package com.sitslive.sitsliveschool.ui.myAttendance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sitslive.sitsliveschool.GlobalVariables;
import com.sitslive.sitsliveschool.MainActivity;
import com.sitslive.sitsliveschool.R;
import com.sitslive.sitsliveschool.ui.examReport.ExamResultAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {
//    private  static final String TAG = "CalendarActivity";
//    private CalendarView mCalendarView;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendance);
//        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
//        mCalendarView.setUnfocusedMonthDateColor(Color.RED);
//    }
//}
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int x;
    String URL = "http://apischools360.sitslive.com/Api/Attendance?stuCode=";
    String Key = "&key="+ GlobalVariables.schoolID;
    ArrayList<String> Date;
    ArrayList<String> FeeFor;
    ArrayList<String> TotalAmount;
    ArrayList<String> TotalDue;
    ArrayList<String> TotalReceive;
    ArrayList<String> Balance;
    ArrayList<String> Spin;
    private List<AttendanceAll> uploads;
    Spinner months;
    int Hold;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Date = new ArrayList<>();
        FeeFor = new ArrayList<>();
        TotalAmount = new ArrayList<>();
        TotalReceive = new ArrayList<>();
        TotalDue = new ArrayList<>();
        Balance = new ArrayList<>();
        Spin=new ArrayList<>();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        months=(Spinner)findViewById(R.id.month);
        uploads = new ArrayList<AttendanceAll>();
        months.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedItemText = (String) parentView.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                int size = uploads.size();

                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        uploads.remove(0);
                    }

                    mAdapter.notifyItemRangeRemoved(0,size);
                }

                if(position > 0){
                    // Notify the selected item text
                    Toast.makeText
                            (getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT)
                            .show();
                    loadCardsData(URL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private ArrayList<AttendanceDetailsDataObject> getDataSet() {
        ArrayList results = new ArrayList<AttendanceDetailsDataObject>();
        for (int index = 0; index < x; index++) {
            AttendanceDetailsDataObject obj = new AttendanceDetailsDataObject("Date: "+Date.get(index),"Status: "+FeeFor.get(index));
            results.add(index, obj);
        }
        return results;
    }

    private void loadCardsData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Integer str = GlobalVariables.id;
        mRecyclerView.removeAllViews();
        String test=url+str+Key+"&month="+months.getSelectedItemPosition();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res= new String[]{"a",response};
                res[1]="{\"name\":"+res[1]+"}";
                try {
                    JSONObject jsonObject = new JSONObject(res[1]);
                    JSONArray jsonArray = jsonObject.getJSONArray("name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String date = jsonObject1.getString("date");
                        String feeFor = jsonObject1.getString("status");
                        AttendanceAll upload=new AttendanceAll(date,feeFor);
                        uploads.add(upload);
//                        String totalAmount = jsonObject1.getString("code");
                        //String totalDue = jsonObject1.getString("file_link");
                        //String totalReceive = jsonObject1.getString("TotalReceive");
//                        //String balance = jsonObject1.getString("Balance");
//                        TotalAmount.add(totalAmount);
                        //TotalDue.add(totalDue);
                        //TotalReceive.add(totalReceive);
                        //Balance.add(balance);
                    }

                    mAdapter = new AttendanceDetailsRecyclerViewAdapter(uploads);
                    mRecyclerView.setAdapter(mAdapter);
                    //spinner.setAdapter(new ArrayAdapter<String>(FeeSummaryActivity.this, android.R.layout.simple_spinner_dropdown_item, SchoolNames));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

}