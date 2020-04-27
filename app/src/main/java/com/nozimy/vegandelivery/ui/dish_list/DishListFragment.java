package com.nozimy.vegandelivery.ui.dish_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.ui.basket.BasketViewModel;

import java.util.Arrays;
import java.util.List;

public class DishListFragment extends Fragment implements DishAdapter.DishListener {
    private DishListFragment.ListFragmentListener listener;

    private RecyclerView list;
    private DishAdapter adapter;
    private List<Dish> mDishList;

    private DishListViewModel mDishListViewModel;

    public DishListFragment() {
    }

    @Override
    public void onItemClick(Dish dish) {
        listener.onDetailsItem(dish);
    }

    @Override
    public void loadDishImage(ImageView view, String url) {
        listener.loadImage(view, url);
    }

    public interface ListFragmentListener {
        void onDetailsItem(Dish dish);
        void loadImage(ImageView view, String url);
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
//        Dish dish1 = new DishEntity("Роллы с авокадо", 120, 1000,
//                600, "рис, авокадо, листы нориб рисовый уксус",
//                "https://sun9-22.userapi.com/impg/c857728/v857728939/17fd2c/eqjDfWVs6uo.jpg?size=520x0&quality=90&sign=69e67db5c2628abb50f6c3bca0e0654e");
//
//        Dish dish2 = new DishEntity("Роллы с авокадо", 120, 1000,
//                600, "рис, авокадо, листы нориб рисовый уксус",
//                "https://sun9-54.userapi.com/impg/c857128/v857128939/fad0c/LDPu6Bzygo0.jpg?size=520x0&quality=90&sign=3ca4a83204cb6a9657d0f81a83ea9c8e");
//
//        Dish dish3 = new DishEntity("Роллы с авокадо", 120, 1000,
//                600, "рис, авокадо, листы нориб рисовый уксус",
//                "https://sun9-59.userapi.com/impg/c205724/v205724939/859d0/eoEeTucq2U8.jpg?size=520x0&quality=90&sign=120c52b7584770474612118201cfeb58");
//        final List<Dish> dish_list = Arrays.asList(dish1, dish2, dish3, dish1, dish2, dish3);

        adapter = new DishAdapter();
        adapter.setListener(this);
        list.setAdapter(adapter);

        Observer<List<Dish>> observer = new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                if (dishes != null) {
                    adapter.setDishList(dishes);
                }
            }
        };
        mDishListViewModel = ViewModelProviders.of(this).get(DishListViewModel.class);
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
