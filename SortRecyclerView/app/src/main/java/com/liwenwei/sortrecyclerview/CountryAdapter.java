package com.liwenwei.sortrecyclerview;


import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    SortedList<Country> list;

    public CountryAdapter() {
        list = new SortedList<Country>(Country.class, new SortedList.Callback<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                /**置顶判断
                 * ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序
                 * 1. 如果是相等的情况下返回0，包括都置顶或者都不置顶，
                 * 返回0的情况下要再做判断，拿它们置顶时间进行判断
                 * 2. 如果是不相等的情况下，o2是置顶的，则当前o1是非置顶的，应该在o2下面，所以返回1
                 *  同样，o1是置顶的，则当前o2是非置顶的，应该在o2上面，所以返回-1
                 * */

                // If both are pined, then sorted by the pined time
                if (o1.isPined() && o2.isPined()) {
                    return 0 - compareToTime(o1.getPinedTime(), o2.getPinedTime());
                }

                if (o1.isPined() && !o2.isPined()) {
                    return -1;
                }

                if (!o1.isPined() && o2.isPined()) {
                    return 1;
                }

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
                return item1.hashCode() == item2.hashCode();
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

    private int compareToTime(Date time1, Date time2) {
        return time1.compareTo(time2);
    }

    public void addAll(List<Country> countries) {
        list.beginBatchedUpdates();
        for (int i = 0; i < countries.size(); i++) {
            list.add(countries.get(i));
        }
        list.endBatchedUpdates();
    }

    public void add(Country country) {
        list.beginBatchedUpdates();
        list.add(country);
        list.endBatchedUpdates();
    }

    public Country get(int position) {
        return list.get(position);
    }

    public void pin(int position, Date pinedTime) {
        list.beginBatchedUpdates();
        Country country = list.get(position);
        country.setPined(true);
        country.setPinedTime(pinedTime);

        list.removeItemAt(position);
        list.add(country);

        list.endBatchedUpdates();
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
        Country item = list.get(position);
        holder.tvCountry.setText(item.getCountry());
        if (item.isPined()) {
            holder.tvCountry.setTextColor(120);
        }
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
