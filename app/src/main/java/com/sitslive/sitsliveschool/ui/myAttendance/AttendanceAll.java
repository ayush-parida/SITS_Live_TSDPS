package com.sitslive.sitsliveschool.ui.myAttendance;

public class AttendanceAll {
    public String mDate;
    public String mFeeFor;
public AttendanceAll(){}

public AttendanceAll(String date, String feeFor)
{
    this.mDate = "Date: "+date;
    this.mFeeFor = "Status: "+feeFor;
}

    public String getmFeeFor(){
        return mFeeFor;
    }
    public void setmFeeFor(String mFeeFor){
        this.mFeeFor=mFeeFor;
    }
    public String getmDate(){
        return mDate;
    }
    public void setmDate(String mDate){
        this.mDate=mDate;
    }
}
