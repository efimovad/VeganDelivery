package com.nozimy.vegandelivery.ui.dish_list;

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

import com.google.android.libraries.places.api.model.Place;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.ui.basket.BasketViewModel;

import java.util.Arrays;
import java.util.List;

public class DishListFragment extends Fragment implements DishAdapter.DishListener {
    private DishListFragment.ListFragmentListener listener;
    private RecyclerView list;
    private DishAdapter adapter;
    private List<Dish> mDishList;
    private DishListViewModel mDishListViewModel;
    private MyPlace myPlace;


    public DishListFragment(MyPlace place) {
        myPlace = place;
    }

    @Override
    public void onItemClick(Dish dish) {
        listener.onDetailsItem(dish, myPlace);
    }

    @Override
    public void loadDishImage(ImageView view, String url) {
        listener.loadImage(view, url);
    }

    public interface ListFragmentListener {
        void onDetailsItem(Dish dish, MyPlace place);
        void loadImage(ImageView view, String url);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dish_list, null);

        TextView title = v.findViewById(R.id.place_title);
        title.setText(myPlace.getName());

        list = v.findViewById(R.id.dish_list);

        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        adapter = new DishAdapter();
        adapter.setListener(this);
        adapter.setHasStableIds(true);
        list.setAdapter(adapter);

        Observer<List<Dish>> observer = dishes -> {
            if (dishes != null) {
                adapter.setDishList(dishes);
            }
        };

        mDishListViewModel = ViewModelProviders.of(this).get(DishListViewModel.class);
        mDishListViewModel.refresh(myPlace.getId());
        mDishListViewModel.getDishList().observe(getViewLifecycleOwner(), observer);

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(mDishList);
            }
        };

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
