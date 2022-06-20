package com.example.qrcodescanwithretrofitvolleyandmockapi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryListAdapter extends ListAdapter<History, HistoryListAdapter.HistoryViewHolder> {


    //declaration
    List<History> historyList;

    protected HistoryListAdapter(@NonNull DiffUtil.ItemCallback<History> diffCallback) {
        super(diffCallback);
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder{

        //bind the data (item in activity_main.xml ->  3 textview)
        private TextView tv_location;
        private TextView tv_date;
        private TextView tv_time;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_location = itemView.findViewById(R.id.location_text);
            tv_date = itemView.findViewById(R.id.date_text);
            tv_time = itemView.findViewById(R.id.time_text);
        }

        //bind into view holder
        public void bind(String location, String date, String time){
            tv_location.setText(location);
            tv_date.setText(date);
            tv_time.setText(time);
        }

        static HistoryViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item, parent, false);
            return new HistoryViewHolder(view);
        }
    }

    protected HistoryListAdapter(@NonNull AsyncDifferConfig<History> config) {
        super(config);
    }

    @NonNull
    @Override
    public HistoryListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HistoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryViewHolder holder, int position) {
        History currentHistory = getItem(position);
        holder.bind(
                currentHistory.getLocation(),
                currentHistory.getDate(),
                currentHistory.getTime());
    }

    static class HistoryDiff extends DiffUtil.ItemCallback<History> {
        @Override
        public boolean areItemsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull History oldItem, @NonNull History newItem) {
            return oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getTime().equals(newItem.getTime()) &&
                    oldItem.getLocation().equals(newItem.getLocation());
        }
    }

//    public class HistoryViewHolder extends RecyclerView.ViewHolder {
//        public HistoryViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
}
