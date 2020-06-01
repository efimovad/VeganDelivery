package com.nozimy.vegandelivery.ui.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.model.Order;

public class OrderFragment extends Fragment {

    private View mRoot;
    private GoogleMap mMap;
    private MapView mapView;
    private Order mOrder;
    private OrderListener listener;

    public OrderFragment(Order order) {
        mOrder = order;
    }

    public interface OrderListener {
        void clickOnOrdersButton();
    }

    public void setListener(OrderListener listener) {
        this.listener = listener;
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

            LatLng shop = mOrder.getShopLatLng();
            LatLng user = mOrder.getUserLatLng();
            googleMap.addMarker(new MarkerOptions().position(shop).title(mOrder.getCafeName()).snippet(""));
            googleMap.addMarker(new MarkerOptions().position(user).title("Адрес доставки").snippet(""));

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            MarkerOptions marker1 = new MarkerOptions();
            marker1.position(shop);

            MarkerOptions marker2 = new MarkerOptions();
            marker2.position(user);

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
            TextInputLayout floor = mRoot.findViewById(R.id.order_floor);
            TextInputLayout porch = mRoot.findViewById(R.id.order_porch);
            TextInputLayout flat = mRoot.findViewById(R.id.order_flat);

            int test = floor.getEditText().getText().toString().length();
            if (floor.getEditText().getText().toString().length() == 0
                    || porch.getEditText().getText().toString().length() == 0
                    || flat.getEditText().getText().toString().length() == 0) {
                Toast toast = Toast.makeText(getContext(),
                        "Укажите номер квартиры, подъезд и этаж", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            OrderViewModel orderListViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
            orderListViewModel.createOrder(mOrder);
            mOrder.clear();
            listener.clickOnOrdersButton();
        };
        payButton.setOnClickListener(payListener);

        TextView totalPrice = mRoot.findViewById(R.id.total_price);
        totalPrice.setText(mOrder.getTotalPrice());

        TextView userAddress = mRoot.findViewById(R.id.user_address);
        userAddress.setText(mOrder.getUserAddress());

        return mRoot;
    }

}
