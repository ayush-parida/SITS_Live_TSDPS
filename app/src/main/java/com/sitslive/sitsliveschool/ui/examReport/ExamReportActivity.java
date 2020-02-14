package com.sitslive.sitsliveschool.ui.examReport;

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
import com.sitslive.sitsliveschool.MainActivity;
import com.sitslive.sitsliveschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExamReportActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static ArrayList<ExamReportDataObject> results=new ArrayList<>();
    private int x;
    String URL = "http://apischools360.sitslive.com/Api/ExamResult?stuCode=";
    String Key="&key="+ GlobalVariables.schoolID;
    String URL2="http://apischools360.sitslive.com/Api/ExamType?stuCode=";
    ArrayList<String> Date;
    ArrayList<String> FeeFor;
    ArrayList<String> TotalAmount;
    ArrayList<String> TotalDue;
    ArrayList<String> TotalReceive;
    ArrayList<String> Balance;
    ArrayList<String> ID;
    ArrayList<String> Spin;
    private List<ExamResultAll> uploads;
    Spinner spinner;
    int Hold;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_report);
        Date = new ArrayList<>();
        FeeFor = new ArrayList<>();
        TotalAmount = new ArrayList<>();
        TotalReceive = new ArrayList<>();
        TotalDue = new ArrayList<>();
        Balance = new ArrayList<>();
        ID=new ArrayList<>();
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
        spinner=(Spinner)findViewById(R.id.examType);
        mRecyclerView.setLayoutManager(mLayoutManager);
        uploads = new ArrayList<ExamResultAll>();
        Spin.add("Select Examination Type");
        ID.add("0");
        loadExamTypes(URL2);
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

    private ArrayList<ExamReportDataObject> getDataSet() {
                   results=new ArrayList<>();
        for (int index = 0; index < x; index++) {
            ExamReportDataObject obj = new ExamReportDataObject("Subject Name: "+Date.get(index),
                    "Maximum Marks: "+ FeeFor.get(index),"Minimum Marks: "+TotalAmount.get(index),"Obtained Marks: "+TotalDue.get(index));
            results.add(index, obj);
        }
        return results;
    }

    private void loadCardsData(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Integer str = GlobalVariables.id;
        String test=url+str+Key+"&examTypeCode="+ID.get(spinner.getSelectedItemPosition());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String[] res = new String[]{"a", response};
                res[1] = "{\"name\":" + res[1] + "}";
                try {
                    JSONObject jsonObject = new JSONObject(res[1]);
                    JSONArray jsonArray = jsonObject.getJSONArray("name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String date = jsonObject1.getString("SubjectName");
                        String feeFor = jsonObject1.getString("MaximumMarks");
                        String totalAmount = jsonObject1.getString("MinimumMarks");
                        String totalDue = jsonObject1.getString("ObtainedMarks");
                        //String totalReceive = jsonObject1.getString("TotalReceive");
                        //String balance = jsonObject1.getString("Balance");
                        ExamResultAll upload=new ExamResultAll(date,feeFor,totalAmount,totalDue);
                        uploads.add(upload);
                        Date.add(date);
                        FeeFor.add(feeFor);
                        TotalAmount.add(totalAmount);
                        TotalDue.add(totalDue);
                        //TotalReceive.add(totalReceive);
                        //Balance.add(balance);
                        x = x + 1;
                    }


                    mAdapter = new ExamReportRecyclerViewAdapter(uploads);
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
    private void loadExamTypes(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Integer str = GlobalVariables.id;
        mRecyclerView.removeAllViews();
        String test=url+str+Key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res = new String[]{"a", response};
                res[1] = "{\"name\":" + res[1] + "}";
                try {
                    JSONObject jsonObject = new JSONObject(res[1]);
                    JSONArray jsonArray = jsonObject.getJSONArray("name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String date = jsonObject1.getString("ExaminationTypeName");
                        String id=jsonObject1.getString("ExaminationTypeCode");
//                        String feeFor = jsonObject1.getString("maximum_marks");
//                        String totalAmount = jsonObject1.getString("minimum_marks");
//                        String totalDue = jsonObject1.getString("obtained_marks");
                        //String totalReceive = jsonObject1.getString("TotalReceive");
                        //String balance = jsonObject1.getString("Balance");
                        Spin.add(date);
                        ID.add(id);
//                        FeeFor.add(feeFor);
//                        TotalAmount.add(totalAmount);
//                        TotalDue.add(totalDue);
                        //TotalReceive.add(totalReceive);
//                        //Balance.add(balance);
//
//                        x = x + 1;
                    }

                    spinner.setAdapter(new ArrayAdapter<String>(ExamReportActivity.this, android.R.layout.simple_spinner_dropdown_item, Spin));
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
