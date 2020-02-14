package com.sitslive.sitsliveschool.ui.myAttendance;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sitslive.sitsliveschool.R;

import java.util.List;

public class AttendanceDetailsRecyclerViewAdapter extends RecyclerView.Adapter<AttendanceDetailsRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "FeeSummaryRecyclerViewAdapter";
    private List<AttendanceAll> mDataset;
    private static MyClickListener myClickListener;
    static View view;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView date;
        TextView feeFor;
//        TextView totalAmount;
//        TextView totalReceived;
//        TextView totalDue;
//        TextView balance;
        public DataObjectHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            feeFor = (TextView) itemView.findViewById(R.id.feeFor);
//            totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
//            totalReceived = (TextView) itemView.findViewById(R.id.totalReceive);
//            totalDue = (TextView) itemView.findViewById(R.id.totalDue);
//            balance = (TextView) itemView.findViewById(R.id.balance);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            //myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
    }
    public AttendanceDetailsRecyclerViewAdapter(List<AttendanceAll> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.attendance_details_cards, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        CardView cardView=(CardView)view.findViewById(R.id.card_view2);
        holder.feeFor.setText(mDataset.get(position).getmDate());
        holder.date.setText(mDataset.get(position).getmFeeFor());
        String out = mDataset.get(position).getmFeeFor();
        TextView textView=(TextView)view.findViewById(R.id.feeFor);
        TextView textView1=(TextView)view.findViewById(R.id.date);
        String str="Status: Present";
        if(!out.equals(str)){
            cardView.setCardBackgroundColor(Color.parseColor("#ffd6d6"));
            textView.setTextColor(Color.parseColor("#ff0000"));
            textView1.setTextColor(Color.parseColor("#ff0000"));
        }
        else if(out.equals(str)){
            cardView.setCardBackgroundColor(Color.parseColor("#e7ffee"));
            textView.setTextColor(Color.parseColor("#005c1a"));
            textView1.setTextColor(Color.parseColor("#005c1a"));
        }
//        holder.totalAmount.setText(mDataset.get(position).getmTotalAmount());
//        holder.totalReceived.setText(mDataset.get(position).getmTotalReceive());
//        holder.totalDue.setText(mDataset.get(position).getmTotalDue());
//        holder.balance.setText(mDataset.get(position).getmBalance());
    }
    public void addItem(AttendanceAll dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}