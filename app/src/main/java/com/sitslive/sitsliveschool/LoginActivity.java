package com.sitslive.sitsliveschool;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    Spinner spinner;
    TextInputEditText editTextUname;
    TextInputEditText editTextPws;
    Button login;
    String URL="http://apischools360.sitslive.com/api/getSchools";
    String URLLogin="http://apischools360.sitslive.com/Api/Login?uname=";
    String SchoolCodeKey="&schoolCodeKey=";
    String Password="&pws=";
    String Key="&key=";
    ArrayList<String> SchoolNames;
    ArrayList<String> ID;
    ArrayList<String>temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SchoolNames=new ArrayList<>();
        ID=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.spinner);
        loadSpinnerData(URL);
        temp=new ArrayList<>();
        editTextUname=(TextInputEditText)findViewById(R.id.editText);
        editTextPws=(TextInputEditText)findViewById(R.id.editText2);
        login=(Button)findViewById((R.id.button));
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Please Wait...",Toast.LENGTH_LONG).show();
                GlobalVariables.schoolID=ID.get(spinner.getSelectedItemPosition());
                String newURL=URLLogin+editTextUname.getText().toString()+Key+ GlobalVariables.schoolID+Password+editTextPws.getText().toString();
                loginEvent(newURL,v);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorAccent));

                String country= spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
    }
    private void loginEvent(final String url, final View view) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res= new String[]{"a",response};
                res[1]="{\"name\":"+res[1]+"}";
                try{
                    JSONObject jsonObject=new JSONObject(res[1]);

                    JSONArray jsonArray=jsonObject.getJSONArray("name");
                    if(jsonArray.length()<=0){
                        Toast.makeText(getApplicationContext(),"Please Check Username and Password",Toast.LENGTH_LONG).show();
                    }
                    else{
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String tempp = jsonObject1.getString("RegistrationNo");
                            Integer temp= jsonObject1.getInt("StudentCode");
                            if (tempp.equals(editTextUname.getText().toString())) {

                                Intent intent = new Intent(view.getContext(), MainActivity.class);
                                GlobalVariables.id=temp;
                                view.getContext().startActivity(intent);
                            }
                            else{

                            }
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),"Please Re-Open the Application..",Toast.LENGTH_LONG).show();
                    e.printStackTrace();}
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
                        String school=jsonObject1.getString("SchoolName");
                        String id=jsonObject1.getString("SchoolCode");
                        SchoolNames.add(school);
                        ID.add(id);
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_dropdown_item, SchoolNames));
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

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}