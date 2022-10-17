package com.fitareq.techno71;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fitareq.techno71.databinding.ListviewItemBinding;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.mViewHolder> {

    private ArrayList<String> items;
    private CallBack callBack;

    public RecentAdapter(ArrayList<String> items, CallBack callBack) {
        this.items = items;
        this.callBack = callBack;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        notifyDataSetChanged();
        if (items.size() < 1)
            callBack.hideRecentSearch();
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(ListviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.binding.title.setText(items.get(position));

        holder.binding.delete.setOnClickListener(v -> {
            Utils.removeStringFromSharedPref(items.get(position), holder.itemView.getContext());
            setItems(Utils.getRecentSearch(holder.itemView.getContext()));
        });
        holder.binding.getRoot().setOnClickListener(v -> {
            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), BrowserActivity.class).putExtra(Utils.URL_KEY, items.get(position)));
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    class mViewHolder extends RecyclerView.ViewHolder {

        ListviewItemBinding binding;
        public mViewHolder(@NonNull ListviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CallBack{
        void hideRecentSearch();
    }
}
