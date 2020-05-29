package com.nozimy.vegandelivery.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.List;

public class OrderListFragment extends Fragment implements OrderListAdapter.OrderListener {
    private RecyclerView list;
    private OrderListAdapter adapter;
    private OrderViewModel mOrderListViewModel;
    private OrderFragmentListener listener;

    public interface OrderFragmentListener {
        void loadImage(ImageView view, String url);
    };

    public void setListener(OrderFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.order_list, null);

        list = v.findViewById(R.id.order_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        adapter = new OrderListAdapter(getActivity());
        list.setAdapter(adapter);
        adapter.setListener(this);

        Observer<List<Order>> observer = orders -> {
            if (orders != null) {
                adapter.setOrderList(orders);
            }
        };

        mOrderListViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        mOrderListViewModel.refresh(2);
        mOrderListViewModel.getOrderList().observe(getViewLifecycleOwner(), observer);

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                //adapter.updateWith();
            }
        };

        return v;
    }

    @Override
    public void loadOrderImage(ImageView view, String url) {
        listener.loadImage(view, url);
    }
}
