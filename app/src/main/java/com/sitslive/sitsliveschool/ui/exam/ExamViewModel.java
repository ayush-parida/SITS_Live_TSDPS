package com.sitslive.sitsliveschool.ui.exam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExamViewModel() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://apischools360.sitslive.com/Api/Fee?stuCode=931&key=@@schools@@@@@@@@@3@@@@&schoolCodeKey=3", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] res= new String[]{"a",response};
                res[1]="{\"name\":"+res[1]+"}";
                try{
                    JSONObject jsonObject=new JSONObject(res[1]);
                    JSONArray jsonArray=jsonObject.getJSONArray("name");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String date=jsonObject1.getString("Date");
                        String feeFor=jsonObject1.getString("FeeFor");
                        Integer totalAmount=jsonObject1.getInt("TotalAmount");
                        Integer totalDue=jsonObject1.getInt("TotalDue");
                        Integer totalReceive=jsonObject1.getInt("TotalReceive");
                        Integer balance=jsonObject1.getInt("Balance");

                    }
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mText = new MutableLiveData<>();
        mText.setValue("This is exam fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}