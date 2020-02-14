package com.sitslive.sitsliveschool.ui.profile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sitslive.sitsliveschool.GlobalVariables;
import com.sitslive.sitsliveschool.LoginActivity;
import com.sitslive.sitsliveschool.MainActivity;
import com.sitslive.sitsliveschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    public static final String TAG = "MyTag";
    RequestQueue requestQueue;
    ArrayList<String> FeeFor;
    public static String _name="";
    public static String _phone="";
    public static String _father="";
    public static String _mother="";
    public static String _address="";
    public static String _cclass="";
    public static String _image="";
    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        standardQueueStringRequest(v);

        // Inflate the layout for this fragment
        return v;
    }
    private void standardQueueStringRequest(View v) {
        final TextView mName = (TextView) v.findViewById(R.id.headName);
        final TextView mName2 = (TextView) v.findViewById(R.id.inName);
        final TextView mPhone = (TextView) v.findViewById(R.id.phone);
        final TextView mPhone2 = (TextView) v.findViewById(R.id.inPhone);
        final TextView mFather = (TextView) v.findViewById(R.id.father);
        final TextView mMother = (TextView) v.findViewById(R.id.mother);
        final TextView mCClass = (TextView) v.findViewById(R.id.currentClass);
        final TextView mAddress = (TextView) v.findViewById(R.id.inAddress);
        final ImageView mProfileImage=(ImageView)v.findViewById(R.id.profileImage);
//        FloatingActionButton fab = v.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), MainActivity.class);
//                intent.setFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);
//            }
//        });
        //mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        // StringRequest with VOLLEY with Standard RequestQueue
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(v.getContext());
        String URL="http://apischools360.sitslive.com/Api/getDetails?stuCode=";
        String Key="&key="+ GlobalVariables.schoolID;
        // Request a string response from the provided URL.
        Integer str = GlobalVariables.id;
        String st=URL+str+Key;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, st,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] res= new String[]{"a",response};
                        res[1]="{\"name\":"+res[1]+"}";
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
                        // Display the first 500 characters of the response string.
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
                mName.setText("That didn't work!");
            }
        });

        stringRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }
}