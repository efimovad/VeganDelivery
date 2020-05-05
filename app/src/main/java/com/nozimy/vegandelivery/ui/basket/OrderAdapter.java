package com.nozimy.vegandelivery.ui.basket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {
    private OrderListener listener;
    private Order mOrder;

    OrderAdapter(Order order) {
        mOrder = order;
    }

    public interface OrderListener {}

    public void setListener(OrderListener listener) {
        this.listener = listener;
    }

    public void updateWith(Dish dish) {
        mOrder.clear();
        mOrder.increment(dish);
        notifyItemInserted(mOrder.size()-1);
    }

    public void addItem(Dish dish) {
        updateWith(dish);
    }

    @NonNull
    @Override
    public OrderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutToInflate = R.layout.order_item;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutToInflate, parent, false);

        return new OrderAdapter.MyHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyHolder holder, int position) {
        holder.name.setText(mOrder.getName(position));
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

//    public void setDishList(Order ) {
//        order = order;
//        notifyDataSetChanged();
//    }
}