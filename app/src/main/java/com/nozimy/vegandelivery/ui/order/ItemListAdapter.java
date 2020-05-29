package com.nozimy.vegandelivery.ui.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;


public class ItemListAdapter extends RecyclerView.Adapter<com.nozimy.vegandelivery.ui.order.ItemListAdapter.MyHolder> {
    private Order mOrder;
    public static final int MAX_TITLE_LEN = 25;

    ItemListAdapter(Order order) {
        mOrder = order;
    }

    public void updateWith(Order order) {
        mOrder = order;
        notifyItemInserted(mOrder.size()-1);
    }

    @NonNull
    @Override
    public com.nozimy.vegandelivery.ui.order.ItemListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutToInflate = R.layout.order_item;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutToInflate, parent, false);

        return new com.nozimy.vegandelivery.ui.order.ItemListAdapter.MyHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull com.nozimy.vegandelivery.ui.order.ItemListAdapter.MyHolder holder, int position) {

        if (mOrder.getName(position).length() < MAX_TITLE_LEN) {
            holder.name.setText(mOrder.getName(position));
        } else {
            String name = mOrder.getName(position).substring(0, MAX_TITLE_LEN).concat("...");
            holder.name.setText(name);
        }

        holder.price.setText(mOrder.getPrice(position));
        holder.count.setText(Integer.toString(mOrder.getCount(position)));
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView count;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.item_price);
            count = itemView.findViewById(R.id.item_count);
        }
    }
}