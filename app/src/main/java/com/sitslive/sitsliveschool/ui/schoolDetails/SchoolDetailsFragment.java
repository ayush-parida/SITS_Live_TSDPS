package com.sitslive.sitsliveschool.ui.schoolDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

public class SchoolDetailsFragment extends Fragment {

    private SchoolDetailsViewModel schoolDetailsViewModel;
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
    public SchoolDetailsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.school_details_fragment, container, false);

        standardQueueStringRequest(v);
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.setFlags(intent.getFlags()|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
    private void standardQueueStringRequest(View v) {
        final TextView mName = (TextView) v.findViewById(R.id.headName);
        final TextView mName2 = (TextView) v.findViewById(R.id.inName);
        final TextView mPhone = (TextView) v.findViewById(R.id.phone);
        final TextView mPhone2 = (TextView) v.findViewById(R.id.inPhone);
        final TextView mMother = (TextView) v.findViewById(R.id.mother);
        final TextView mCClass = (TextView) v.findViewById(R.id.currentClass);
        final TextView mAddress = (TextView) v.findViewById(R.id.inAddress);
        final ImageView mProfileImage=(ImageView)v.findViewById(R.id.profileImage);
        //mLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        // StringRequest with VOLLEY with Standard RequestQueue
        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(v.getContext());
        String URL="http://apischools360.sitslive.com/Api/AboutSchool?key=";
        String Key= GlobalVariables.schoolID;
        // Request a string response from the provided URL.
        String st=URL+Key;
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
                                String name = jsonObject1.getString("SchoolName");
                                String phone = jsonObject1.getString("SchoolSlogan");
                                String father = jsonObject1.getString("Address");
                                String mother = jsonObject1.getString("Phone");
                                String address = jsonObject1.getString("WebSite");
                                String cclass = jsonObject1.getString("Head");
                                String image = jsonObject1.getString("SchoolLogo");
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
                        mName2.setText(_cclass);
                        mPhone.setText(_address);
                        mPhone2.setText(_mother);
                        mMother.setText(_phone);
                        mAddress.setText(_father);
                        mCClass.setText(_mother);

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