package com.nozimy.vegandelivery.ui.place_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;

import java.util.Arrays;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PlaceListFragment extends Fragment implements PlaceAdapter.AdapterListener {
    private ListFragmentListener listener;

    private RecyclerView list;
    private PlaceAdapter adapter;
    private PlaceListViewModel mPlaceListViewModel;
    private List<MyPlace> mMyPlaceList;

    @Override
    public void onItemClick(MyPlace myPlace) {
        listener.onDetailsItem(myPlace);
    }

    public interface ListFragmentListener {
        void onDetailsItem(MyPlace myPlace);
    };

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        String apiKey = getString(R.string.google_maps_key);
//        if (!Places.isInitialized()) {
//            Places.initialize(getActivity().getApplicationContext(), apiKey);
//        }
//
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        // Set up a PlaceSelectionListener to handle the response.
////        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
////            @Override
////            public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
////                //todo
////                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
////            }
////
////            @Override
////            public void onError(Status status) {
////                // TODO: Handle the error.
////                Log.i(TAG, "An error occurred: " + status);
////            }
////        });
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shop_list, null);

        list = v.findViewById(R.id.shop_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );

        adapter = new PlaceAdapter();
        adapter.setListener(this);
        list.setAdapter(adapter);

        Observer<List<PlaceEntity>> observer = new Observer<List<PlaceEntity>>() {
            @Override
            public void onChanged(List<PlaceEntity> places) {
                if (places != null) {
                    List<MyPlace> list = mPlaceListViewModel.transformPlaceEntityToMyPlace(places);
                    adapter.setPlaceList(list);
                    mMyPlaceList = list;
                }
            }
        };
        mPlaceListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);
        mPlaceListViewModel.getPlaceList().observe(getViewLifecycleOwner(), observer);


        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {
                adapter.updateWith(mMyPlaceList);
            }
        };
        //handler.postDelayed(myRunnable, 1000);
        //handler.post(myRunnable);

        String apiKey = getString(R.string.google_maps_key);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.location_autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setCountry("ru");




        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

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
