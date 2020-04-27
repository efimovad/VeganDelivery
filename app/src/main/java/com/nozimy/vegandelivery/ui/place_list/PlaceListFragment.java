package com.nozimy.vegandelivery.ui.place_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.dish_list.DishAdapter;
import com.nozimy.vegandelivery.ui.dish_list.DishListViewModel;

import java.util.Arrays;
import java.util.List;

public class PlaceListFragment extends Fragment implements PlaceAdapter.AdapterListener {
    private ListFragmentListener listener;

    private RecyclerView list;
    private PlaceAdapter adapter;
    private PlaceListViewModel mPlaceListViewModel;
    private List<Place> mPlaceList;

    @Override
    public void onItemClick(Place place) {
        listener.onDetailsItem(place);
    }

    public interface ListFragmentListener {
        void onDetailsItem(Place place);
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shop_list, null);

        list = v.findViewById(R.id.shop_list);

        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        // TODO: fix mock
//        Place place = new PlaceEntity(1,"Веганга", 25, 499, 3.4f);
//        final List<Place> place_list = Arrays.asList(place, place, place, place);
//
//        adapter = new PlaceAdapter(place_list);
//        adapter.setListener(this);
//        list.setAdapter(adapter);

        adapter = new PlaceAdapter();
        adapter.setListener(this);
        list.setAdapter(adapter);

        Observer<List<Place>> observer = new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                if (places != null) {
                    adapter.setPlaceList(places);
                }
            }
        };
        mPlaceListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);
        mPlaceListViewModel.getPlaceList().observe(getViewLifecycleOwner(), observer);


        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(mPlaceList);
            }
        };
        //handler.postDelayed(myRunnable, 1000);
        //handler.post(myRunnable);
        return v;
    }

    public void setListener(ListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListFragmentListener) {
            listener = (ListFragmentListener) context;
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
