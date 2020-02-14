package com.sitslive.sitsliveschool.ui.timetableDetails;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sitslive.sitsliveschool.R;

import java.util.ArrayList;
import java.util.List;

public class TimetableDetailsRecyclerViewAdapter extends RecyclerView.Adapter<TimetableDetailsRecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "FeeSummaryRecyclerViewAdapter";
    private List<TimetableDetailsDataObject> mDataset;
    private static MyClickListener myClickListener;
    private int q=1;
    private View view;
    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView date;
        TextView feeFor;
        TextView totalAmount;
//        TextView totalReceived;
//        TextView totalDue;
//        TextView balance;
        public DataObjectHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            feeFor = (TextView) itemView.findViewById(R.id.feeFor);
            totalAmount = (TextView) itemView.findViewById(R.id.totalAmount);
//            totalReceived = (TextView) itemView.findViewById(R.id.totalReceive);
//            totalDue = (TextView) itemView.findViewById(R.id.totalDue);
//            balance = (TextView) itemView.findViewById(R.id.balance);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
//            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
//        this.myClickListener = myClickListener;
    }
    public TimetableDetailsRecyclerViewAdapter(List<TimetableDetailsDataObject> myDataset) {
        mDataset = myDataset;
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_details_cards, parent, false);
        CardView cardView=(CardView)view.findViewById(R.id.card_view2);
        TextView textView=(TextView)view.findViewById(R.id.feeFor);
        TextView textView1=(TextView)view.findViewById(R.id.date);
        TextView textView2=(TextView)view.findViewById(R.id.totalAmount);
        if(q==1) {
            cardView.setCardBackgroundColor(Color.parseColor("#fffbe3"));
            textView.setTextColor(Color.parseColor("#d05e00"));
            textView1.setTextColor(Color.parseColor("#d05e00"));
            textView2.setTextColor(Color.parseColor("#d05e00"));
            q+=1;
        }
        else if(q==2){
            cardView.setCardBackgroundColor(Color.parseColor("#e3f2ff"));
            textView.setTextColor(Color.parseColor("#004a8a"));
            textView1.setTextColor(Color.parseColor("#004a8a"));
            textView2.setTextColor(Color.parseColor("#004a8a"));
            q+=1;
        }
        else {
            cardView.setCardBackgroundColor(Color.parseColor("#eeffef"));
            textView.setTextColor(Color.parseColor("#007e07"));
            textView1.setTextColor(Color.parseColor("#007e07"));
            textView2.setTextColor(Color.parseColor("#007e07"));
            q=1;
        }
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.date.setText(mDataset.get(position).getmDate());
        holder.feeFor.setText(mDataset.get(position).getmFeeFor());
        holder.totalAmount.setText(mDataset.get(position).getmTotalAmount());
//        holder.totalReceived.setText(mDataset.get(position).getmTotalReceive());
//        holder.totalDue.setText(mDataset.get(position).getmTotalDue());
//        holder.balance.setText(mDataset.get(position).getmBalance());
    }
    public void addItem(TimetableDetailsDataObject dataObj, int index) {
        //mDataset.add(index, dataObj);
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