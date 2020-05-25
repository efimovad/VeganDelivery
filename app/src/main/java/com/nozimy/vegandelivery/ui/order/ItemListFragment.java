package com.nozimy.vegandelivery.ui.order;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.ui.basket.BasketViewModel;
import com.nozimy.vegandelivery.ui.dish_list.DishAdapter;
import com.nozimy.vegandelivery.ui.dish_list.DishListViewModel;

import java.util.Arrays;
import java.util.List;

public class ItemListFragment extends Fragment {
    private RecyclerView list;
    private ItemListAdapter adapter;
    private Order mOrder;

    public ItemListFragment(Order order) {
        mOrder = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.order_item_list, null);

        list = v.findViewById(R.id.items_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        adapter = new ItemListAdapter(mOrder);
        list.setAdapter(adapter);

        /*Observer<List<Dish>> observer = dishes -> {
            if (dishes != null) {
                adapter.setDishList(dishes);
            }
        };*/

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(mOrder);
            }
        };

        return v;
    }
}
