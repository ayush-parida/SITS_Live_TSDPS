package com.sitslive.sitsliveschool.ui.examSchedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.sitslive.sitsliveschool.LoginActivity;
import com.sitslive.sitsliveschool.MainActivity;
import com.sitslive.sitsliveschool.R;
import com.sitslive.sitsliveschool.ui.examReport.ExamResultAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExamScheduleActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int x=0;
    String URL = "http://apischools360.sitslive.com/Api/examSchedule?stuCode=";
    String Key = "&key=";
    String URL2="http://apischools360.sitslive.com/Api/ExamType?stuCode="+GlobalVariables.id;
    String Key2="&key="+GlobalVariables.schoolID;
    ArrayList<String> Date;
    ArrayList<String> FeeFor;
    ArrayList<String> TotalAmount;
    private List<ExamScheduleDataObject> uploads;
    //ArrayList<String> TotalDue;
    ArrayList<Integer> TotalReceive;
    ArrayList<Integer> Balance;
    ArrayList<String> ExamTypeNames;
    Spinner spinner;
    ArrayList<String> ID;

    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_schedule);
        Date = new ArrayList<>();
        FeeFor = new ArrayList<>();
        TotalAmount = new ArrayList<>();
        TotalReceive = new ArrayList<>();
        //TotalDue = new ArrayList<>();
        Balance = new ArrayList<>();
        ExamTypeNames=new ArrayList<>();
        ID=new ArrayList<>();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        spinner=(Spinner)findViewById(R.id.spinner);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ExamTypeNames.add("Select Examination Type");
        ID.add("0");
        loadSpinnerData(URL2+Key2);
        uploads = new ArrayList<ExamScheduleDataObject>();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    private ArrayList<ExamScheduleDataObject> getDataSet() {
        ArrayList results = new ArrayList<ExamScheduleDataObject>();
        for (int index = 0; index < x; index++) {
            ExamScheduleDataObject obj = new ExamScheduleDataObject("Examination Type: "+Date.get(index),
                    "Subject: "+FeeFor.get(index),"Date: "+TotalAmount.get(index),
                    "Maximum Marks: "+TotalReceive.get(index),"Minimum Marks: "+Balance.get(index));
            results.add(index, obj);
        }
        return results;
    }

    private void loadCardsData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String str = GlobalVariables.id+"&key="+GlobalVariables.schoolID+"&examTypeCode="+ID.get(spinner.getSelectedItemPosition());
        String test=url+str;
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
                        String date = jsonObject1.getString("ExaminationTypeName");
                        String feeFor = jsonObject1.getString("SubjectName");
                        String totalAmount = jsonObject1.getString("ESDateTime");
                        //String totalDue = jsonObject1.getString("time");
                        Integer totalReceive = jsonObject1.getInt("MaximumMarks");
                        Integer balance = jsonObject1.getInt("MinimumMarks");
                        Date.add(date);
                        FeeFor.add(feeFor);
                        TotalAmount.add(totalAmount);
                        //TotalDue.add(totalDue);
                        TotalReceive.add(totalReceive);
                        Balance.add(balance);
                        ExamScheduleDataObject upload=new ExamScheduleDataObject(date,feeFor,totalAmount,totalReceive.toString(),balance.toString());
                        uploads.add(upload);


//                        if(i==x) {
//                            Date.add(date);
//                            FeeFor.add(feeFor);
//                            TotalAmount.add(totalAmount);
//                            //TotalDue.add(totalDue);
//                            TotalReceive.add(totalReceive);
//                            Balance.add(balance);
//                            x = x + 1;
//                        }
//                        else if(i<x){
//                            Date.add(i,date);
//                            FeeFor.add(i,feeFor);
//                            TotalAmount.add(i,totalAmount);
//                            //TotalDue.add(totalDue);
//                            TotalReceive.add(i,totalReceive);
//                            Balance.add(i,balance);
//                        }
//                        else if(i>x){
//                            Date.remove(i);
//                            FeeFor.remove(i);
//                            TotalAmount.remove(i);
//                            //TotalDue.add(totalDue);
//                            TotalReceive.remove(i);
//                            Balance.remove(i);
//                        }
                    }

                    mAdapter = new ExamScheduleRecyclerViewAdapter(uploads);
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
    private void loadSpinnerData(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res= new String[]{"a",response};
                res[1]="{\"name\":"+res[1]+"}";
                try{
                    JSONObject jsonObject=new JSONObject(res[1]);
                    JSONArray jsonArray=jsonObject.getJSONArray("name");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String school=jsonObject1.getString("ExaminationTypeName");
                        String id=jsonObject1.getString("ExaminationTypeCode");
                        ExamTypeNames.add(school);
                        ID.add(id);
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(ExamScheduleActivity.this, android.R.layout.simple_spinner_dropdown_item, ExamTypeNames));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 300000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
