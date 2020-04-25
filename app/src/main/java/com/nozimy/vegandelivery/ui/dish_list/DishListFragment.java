package com.nozimy.vegandelivery.ui.dish_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.dish_list.DishAdapter;
import com.nozimy.vegandelivery.ui.place_list.PlaceAdapter;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

import java.util.Arrays;
import java.util.List;

public class DishListFragment extends Fragment implements DishAdapter.AdapterListener {
    private DishListFragment.ListFragmentListener listener;

    private RecyclerView list;
    private DishAdapter adapter;

    @Override
    public void onItemClick(Dish dish) {
        listener.onDetailsItem(dish);
    }

    public interface ListFragmentListener {
        void onDetailsItem(Dish dish);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dish_list, null);

        list = v.findViewById(R.id.dish_list);

        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        // TODO: fix mock
        Dish dish = new DishEntity("Роллы с авокадо", 120, 1000,
                600, "рис, авокадо, листы нориб рисовый уксус");
        final List<Dish> dish_list = Arrays.asList(dish, dish, dish, dish, dish, dish);

        adapter = new DishAdapter(dish_list);
        adapter.setListener(this);
        list.setAdapter(adapter);

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(dish_list);
            }
        };
        //handler.postDelayed(myRunnable, 1000);
        //handler.post(myRunnable);
        return v;
    }

    public void setListener(DishListFragment.ListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DishListFragment.ListFragmentListener) {
            listener = (DishListFragment.ListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "must implement ListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
