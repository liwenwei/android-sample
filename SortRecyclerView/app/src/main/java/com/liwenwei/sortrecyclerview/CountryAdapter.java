package com.liwenwei.sortrecyclerview;


import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    SortedList<Country> list;

    public CountryAdapter() {
        list = new SortedList<Country>(Country.class, new SortedList.Callback<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getCountry().compareTo(o2.getCountry());
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Country oldItem, Country newItem) {
                return oldItem.getCountry().equals(newItem.getCountry());
            }

            @Override
            public boolean areItemsTheSame(Country item1, Country item2) {
                return item1.getCountry().equals(item2.getCountry());
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    public void addAll(List<Country> countries) {
        list.beginBatchedUpdates();
        for (int i = 0; i < countries.size(); i++) {
            list.add(countries.get(i));
        }
        list.endBatchedUpdates();
    }

    public Country get(int position) {
        return list.get(position);
    }

    public void clear() {
        list.beginBatchedUpdates();
        while (list.size() > 0) {
            list.removeItemAt(list.size() - 1);
        }
        list.endBatchedUpdates();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvCountry.setText(list.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCountry;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCountry = itemView.findViewById(R.id.tv_country);
        }
    }
}
