package com.sitslive.sitsliveschool.ui.examReport;

public class ExamReportDataObject {
    private String mFeeFor;
    private String mDate;
    private String mTotalAmount;
    private String mTotalReceive;
//    private String mTotalDue;
//    private String mBalance;

    ExamReportDataObject(String feeFor, String date, String totalAmount, String totalReceive) {
        mFeeFor=feeFor;
        mDate=date;
        mTotalAmount=totalAmount;
        mTotalReceive=totalReceive;
//        mTotalDue=totalDue;
//        mBalance=balance;
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
    public String getmTotalAmount(){
        return mTotalAmount;
    }
    public void setmTotalAmount(String mTotalAmount){
        this.mTotalAmount=mTotalAmount;
    }
    public String getmTotalReceive(){
        return mTotalReceive;
    }
    public void setmTotalReceive(String mTotalReceive){
        this.mTotalReceive=mTotalReceive;
    }
//    public String getmTotalDue(){
//        return mTotalDue;
//    }
//    public void setmTotalDue(String mTotalDue){
//        this.mTotalDue=mTotalDue;
//    }
//    public String getmBalance(){
//        return mBalance;
//    }
//    public void setmBalance(String mBalance){
//        this.mBalance=mBalance;
//    }
}