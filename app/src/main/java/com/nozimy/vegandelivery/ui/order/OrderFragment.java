package com.nozimy.vegandelivery.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

public class OrderFragment extends Fragment {

    private View mRoot;
    private GoogleMap mMap;
    private MapView mapView;
    private Order mOrder;

    public OrderFragment(Order order) {
        mOrder = order;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_submit_order, container, false);

        mapView = mRoot.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(googleMap -> {
            mMap = googleMap;

            LatLng sydney = new LatLng(55.798086,37.6048852);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

            LatLng home = new LatLng(55.7678096,37.6707284);
            googleMap.addMarker(new MarkerOptions().position(home).title("Marker Title").snippet("Marker Description"));

            //CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(14).build();

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            MarkerOptions marker1 = new MarkerOptions();
            marker1.position(sydney);

            MarkerOptions marker2 = new MarkerOptions();
            marker2.position(home);

            builder.include(marker1.getPosition());
            builder.include(marker2.getPosition());

            LatLngBounds bounds = builder.build();

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.25);

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);

        });

        Button payButton = mRoot.findViewById(R.id.order_pay_button);
        View.OnClickListener payListener = v -> {
            OrderViewModel orderListViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
            orderListViewModel.createOrder(2, mOrder);
            mOrder.clear();
        };
        payButton.setOnClickListener(payListener);

        return mRoot;
    }

}
