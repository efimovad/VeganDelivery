package com.nozimy.vegandelivery.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

import java.util.ArrayList;

public class OrderListFragment extends Fragment {
    private RecyclerView list;
    private OrderListAdapter adapter;
    private ArrayList<Order> mOrders;

    public OrderListFragment(ArrayList<Order> orders) {
        mOrders = orders;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.order, null);

        list = v.findViewById(R.id.items_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        adapter = new OrderListAdapter(mOrders);
        list.setAdapter(adapter);

        /*Observer<List<Dish>> observer = dishes -> {
            if (dishes != null) {
                adapter.setDishList(dishes);
            }
        };*/
        //getChildFragmentManager()

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                //adapter.updateWith();
            }
        };

        return v;
    }
}
