package com.nozimy.vegandelivery.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<com.nozimy.vegandelivery.ui.order.OrderListAdapter.MyHolder> {
    private List<Order> mOrders;
    private Context mContext;
    private OrderListener listener;

    OrderListAdapter(Context context) {
        mContext = context;
    }

    public interface OrderListener {
        void loadOrderImage(ImageView view, String url);
    }

    public void setListener(OrderListener listener) {
        this.listener = listener;
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
        listener.loadOrderImage(holder.logo, mOrders.get(position).getLogo());

        int status = mOrders.get(position).getStatus();

        switch (status) {
            case Order.Delivered:
                holder.status.setText("ДОСТАВЛЕН");
                holder.status.getBackground().setTint(mContext.getResources().getColor(R.color.colorDelivered));
                break;
            case Order.Created:
                holder.status.setText("ОБРАБАТЫВАЕТСЯ");
                holder.status.getBackground().setTint(mContext.getResources().getColor(R.color.colorInProcess));
                break;
            case Order.Preparing:
                holder.status.setText("ГОТОВИТСЯ");
                holder.status.getBackground().setTint(mContext.getResources().getColor(R.color.colorInProcess));
                break;
            case Order.Delivering:
                holder.status.setText("В ПУТИ");
                holder.status.getBackground().setTint(mContext.getResources().getColor(R.color.colorInProcess));
                break;
            case Order.Canceled:
                holder.status.setText("ОТМЕНЕН");
                holder.status.getBackground().setTint(mContext.getResources().getColor(R.color.colorCanceled));
                break;
        }

        holder.total.setText(mOrders.get(position).getTotalPrice());
        holder.name.setText(mOrders.get(position).getCafeName());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.items.setLayoutManager(layoutManager);
        holder.items.setHasFixedSize(true);
        ItemListAdapter itemListAdapter = new ItemListAdapter(mOrders.get(position));
        holder.items.setAdapter(itemListAdapter);
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mOrders == null)
            return 0;
        return mOrders.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView total;
        private TextView name;
        private TextView status;
        private RecyclerView items;
        private ImageView logo;

        public MyHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            logo = itemView.findViewById(R.id.logo);
            name = itemView.findViewById(R.id.place_name);
            total = itemView.findViewById(R.id.total_price);
            status = itemView.findViewById(R.id.order_status);
            items = itemView.findViewById(R.id.items_list);
        }
    }

    @Override
    public long getItemId(int position) {
        return mOrders.get(position).getId();
    }

    public void setOrderList(List<Order> orders) {
        mOrders = orders;
        notifyDataSetChanged();
    }
}
