package com.nozimy.vegandelivery.ui.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<com.nozimy.vegandelivery.ui.order.OrderListAdapter.MyHolder> {
    private ArrayList<Order> mOrders;

    OrderListAdapter(ArrayList<Order> orders) {
        mOrders = orders;
    }



    public void updateWith(Order order) {
        mOrders.add(order);
        notifyItemInserted(mOrders.size()-1);
    }

    @NonNull
    @Override
    public com.nozimy.vegandelivery.ui.order.OrderListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutToInflate = R.layout.order;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutToInflate, parent, false);

        return new com.nozimy.vegandelivery.ui.order.OrderListAdapter.MyHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull com.nozimy.vegandelivery.ui.order.OrderListAdapter.MyHolder holder, int position) {

//        if (mOrder.getName(position).length() < MAX_TITLE_LEN) {
//            holder.name.setText(mOrder.getName(position));
//        } else {
//            String name = mOrder.getName(position).substring(0, MAX_TITLE_LEN).concat("...");
//            holder.name.setText(name);
//        }
//
//        holder.price.setText(mOrder.getPrice(position));
        //holder.count.setText(mOrder.getCount(position));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView total;
        private TextView name;
        private TextView date;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name);
            total = itemView.findViewById(R.id.total_price);
            date = itemView.findViewById(R.id.order_date);
        }
    }
}
