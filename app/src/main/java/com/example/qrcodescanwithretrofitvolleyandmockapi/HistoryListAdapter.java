package com.example.qrcodescanwithretrofitvolleyandmockapi;

import android.view.View;
import android.view.ViewGroup;

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

    protected HistoryListAdapter(@NonNull AsyncDifferConfig<History> config) {
        super(config);
    }

    @NonNull
    @Override
    public HistoryListAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryListAdapter.HistoryViewHolder holder, int position) {

    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
