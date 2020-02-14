package com.sitslive.sitsliveschool.ui.transportDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class TransportActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int x;
    String URL = "http://apischools360.sitslive.com/Api/TransportDetails?stuCode=";
    String Key = "&key="+ GlobalVariables.schoolID;
    ArrayList<String> Date;
    ArrayList<String> FeeFor;
    ArrayList<Integer> TotalAmount;
    ArrayList<Integer> TotalDue;
    ArrayList<Integer> TotalReceive;
    ArrayList<Integer> Balance;

    private static String LOG_TAG = "CardViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        Date = new ArrayList<>();
        FeeFor = new ArrayList<>();
        TotalAmount = new ArrayList<>();
        TotalReceive = new ArrayList<>();
        TotalDue = new ArrayList<>();
        Balance = new ArrayList<>();
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
        loadCardsData(URL);
    }

    private ArrayList<TransportDetailsDataObject> getDataSet() {
        ArrayList results = new ArrayList<TransportDetailsDataObject>();
        for (int index = 0; index < x; index++) {
            TransportDetailsDataObject obj = new TransportDetailsDataObject("Name: "+Date.get(index),"Route Number: "+FeeFor.get(index),
                    "Monthly Amount: "+TotalAmount.get(index),"Fee Amount: "+TotalDue.get(index),"Due Amount: "+TotalReceive.get(index));
            results.add(index, obj);
        }
        return results;
    }

    private void loadCardsData(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Integer str = GlobalVariables.id;
        String test=url+str+Key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, test, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] arr=response.split("<string xmlns=\"http://tempuri.org/\">");
                String[] res= new String[]{"a",response};
                res[1]="{\"name\":"+res[1]+"}";
                try {
                    JSONObject jsonObject = new JSONObject(res[1]);
                    JSONArray jsonArray = jsonObject.getJSONArray("name");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String date,feeFor;
                        Integer totalAmount,totalDue,totalReceive;
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            date = jsonObject1.getString("student_name");
                            feeFor = jsonObject1.getString("route_number");
                            totalAmount = jsonObject1.getInt("monthly_amount");
                            totalDue = jsonObject1.getInt("fee_amount");
                            totalReceive = jsonObject1.getInt("due_amount");
                            //Integer balance = jsonObject1.getInt("amount");
                        Date.add(date);
                        FeeFor.add(feeFor);
                        TotalAmount.add(totalAmount);
                        TotalDue.add(totalDue);
                        TotalReceive.add(totalReceive);
                        //Balance.add(balance);
                        x = x + 1;
                    }

                    mAdapter = new TransportDetailsRecyclerViewAdapter(getDataSet());
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