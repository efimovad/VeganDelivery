package com.nozimy.vegandelivery.ui.place_list;

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
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.Place;

import java.util.Arrays;
import java.util.List;

public class PlaceListFragment extends Fragment implements PlaceAdapter.AdapterListener {
    private ListFragmentListener listener;

    private RecyclerView list;
    private PlaceAdapter adapter;

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
        Place place = new PlaceEntity(1,"Веганга", 25, 499, 3.4f);
        final List<Place> place_list = Arrays.asList(place, place, place, place);

        adapter = new PlaceAdapter(place_list);
        adapter.setListener(this);
        list.setAdapter(adapter);

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(place_list);
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
