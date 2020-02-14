package com.sitslive.sitsliveschool.ui.profileDetails;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sitslive.sitsliveschool.GlobalVariables;
import com.sitslive.sitsliveschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileActivity extends AppCompatActivity {
    private int x;
    String URL = "http://45.115.62.5:89/AndroidAPI.asmx/GetProfileDetails?studentCode=";
    public static String _name="";
    public static String _phone="";
    public static String _father="";
    public static String _mother="";
    public static String _address="";
    public static String _cclass="";
    public static String _image="";

    private static String LOG_TAG = "CardViewActivity";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        loadCardsData(URL);
    }

    private void loadCardsData(String url) {
        final TextView mName = (TextView) findViewById(R.id.headName);
        final TextView mName2 = (TextView) findViewById(R.id.inName);
        final TextView mPhone = (TextView) findViewById(R.id.phone);
        final TextView mPhone2 = (TextView) findViewById(R.id.inPhone);
        final TextView mFather = (TextView) findViewById(R.id.father);
        final TextView mMother = (TextView) findViewById(R.id.mother);
        final TextView mCClass = (TextView) findViewById(R.id.currentClass);
        final TextView mAddress = (TextView) findViewById(R.id.inAddress);
        final ImageView mProfileImage=(ImageView) findViewById(R.id.profileImage);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Integer str = GlobalVariables.id;
        String test=url+str;
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
                        String name = jsonObject1.getString("name");
                        String phone = jsonObject1.getString("phone");
                        String father = jsonObject1.getString("father_name");
                        String mother = jsonObject1.getString("mother_name");
                        String address = jsonObject1.getString("address");
                        String cclass = jsonObject1.getString("current_class");
                        String image = jsonObject1.getString("image");
                        _name=name;
                        _phone=phone;
                        _father=father;
                        _mother=mother;
                        _address=address;
                        _cclass=cclass;
                        _image=image;
                    }

                    //spinner.setAdapter(new ArrayAdapter<String>(FeeSummaryActivity.this, android.R.layout.simple_spinner_dropdown_item, SchoolNames));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mName.setText(_name);
                mName2.setText(_name);
                mPhone.setText(_phone);
                mPhone2.setText(_phone);
                mFather.setText(_father);
                mMother.setText(_mother);
                mAddress.setText(_address);
                mCClass.setText(_cclass);

                @SuppressLint({"NewApi", "LocalSuppress"}) byte[] decodedString = Base64.decode(_image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mProfileImage.setImageBitmap(decodedByte);
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
