package com.sitslive.sitsliveschool.ui.examReport;

public class ExamResultAll {
    public String date;
    public String feeFor;
    public String Review;
    public String Price;
public ExamResultAll(){}

public ExamResultAll(String date, String feeFor,String totalAmount,String totalDue)
{
    this.feeFor = "Subject Name: "+date;
    this.date = "Maximum Marks: "+feeFor;
    this.Price = "Minimum Marks: "+totalAmount;
    this.Review="Obtained Marks: "+totalDue;
}

}
