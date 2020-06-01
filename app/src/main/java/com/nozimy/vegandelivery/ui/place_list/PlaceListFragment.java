package com.nozimy.vegandelivery.ui.place_list;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.AddressComponents;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PlaceListFragment extends Fragment implements PlaceAdapter.AdapterListener {
    private ListFragmentListener listener;
    private RecyclerView list;
    private PlaceAdapter adapter;
    private PlaceListViewModel mPlaceListViewModel;
    private List<MyPlace> mMyPlaceList;
    AutocompleteSupportFragment autocompleteFragment;

    @Override
    public void onItemClick(MyPlace myPlace) {
        listener.onDetailsItem(myPlace);
    }

    @Override
    public void loadPlaceImage(ImageView view, String url) { listener.loadImage(view, url); }

    @Override
    public void changeFavStatus(long id, boolean value, int position) {
        mPlaceListViewModel.changeFavStatus(id, value);
        //adapter.notifyItemChanged(position);
    }

    public interface ListFragmentListener {
        void onDetailsItem(MyPlace myPlace);
        void loadImage(ImageView view, String url);
        void setAddress(String address, LatLng latLng);
        String getAddress();
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shop_list, null);

        list = v.findViewById(R.id.shop_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );
        ((SimpleItemAnimator) list.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new PlaceAdapter(getContext());
        adapter.setListener(this);
        adapter.setHasStableIds(true);

        list.setAdapter(adapter);

        Observer<List<PlaceEntity>> observer = places -> {
            if (places != null) {
                List<MyPlace> list = mPlaceListViewModel.transformPlaceEntityToMyPlace(places);
                adapter.setPlaceList(list);
                mMyPlaceList = list;
            }
        };
        mPlaceListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);
        mPlaceListViewModel.getPlaceList().observe(getViewLifecycleOwner(), observer);

        setPlaceAutocomplete();

        return v;
    }

    public void setListener(ListFragmentListener listener) {
        this.listener = listener;
    }

    public void setPlaceAutocomplete() {
        String apiKey = getString(R.string.google_maps_key);
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), apiKey);
        }

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.location_autocomplete_fragment);
        autocompleteFragment.setPlaceFields(fields);
        autocompleteFragment.setCountry("ru");
        autocompleteFragment.setHint("Укажите адрес доставки");

        autocompleteFragment.setText(listener.getAddress());

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                listener.setAddress(place.getAddress(), place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                if (status != null && status.hasResolution()) {
                    Log.i(TAG, status.getStatusMessage());
                }
            }

        });
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
