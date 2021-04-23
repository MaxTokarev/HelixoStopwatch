package com.helixo.stopwatch.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.helixo.stopwatch.R;
import com.helixo.stopwatch.databinding.ItemLapBinding;

import java.util.LinkedList;
import java.util.List;

public class LapsAdapter extends RecyclerView.Adapter<LapsAdapter.Vh> {

    private List<LapItem> lapItems = new LinkedList<>();

    public void setItems(List<LapItem> items) {
        lapItems = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLapBinding binding = ItemLapBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new Vh(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        holder.bind(lapItems.get(position));
    }

    @Override
    public int getItemCount() {
        return lapItems.size();
    }

    static class Vh extends RecyclerView.ViewHolder {
        private final ItemLapBinding binding;

        public Vh(@NonNull ItemLapBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LapItem item) {
            binding.time.setText(item.getTime());
            String lampNumber = binding
                .getRoot()
                .getResources()
                .getString(R.string.lap_number, item.getLapNumber());
            binding.lampNumber.setText(lampNumber);
        }
    }
}


