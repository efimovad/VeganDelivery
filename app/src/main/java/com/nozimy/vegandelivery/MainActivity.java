package com.nozimy.vegandelivery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.entity.OrderEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.db.model.Person;
import com.nozimy.vegandelivery.ui.auth.AuthFragment;
import com.nozimy.vegandelivery.ui.order.ItemListFragment;
import com.nozimy.vegandelivery.ui.order.OrderFragment;
import com.nozimy.vegandelivery.ui.basket.BasketFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishDialogFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishListFragment;
import com.nozimy.vegandelivery.ui.order.OrderListFragment;
import com.nozimy.vegandelivery.ui.personal.PersonalFragment;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements DishListFragment.ListFragmentListener,
        PlaceListFragment.ListFragmentListener,
        DishDialogFragment.DishDialogListener,
        BasketFragment.BasketFragmentListener,
        OnMapReadyCallback,
        PersonalFragment.PersonalFragmentListener,
        AuthFragment.AuthListener {

    private Order mCurrentOrder = new OrderEntity();
    private Boolean isLargeLayout;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        PlaceListFragment placeList = new PlaceListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, placeList)
                .commit();

        isLargeLayout = getResources().getBoolean(R.bool.large_layout);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = item -> {
                Fragment selected =  null;

                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selected = new PlaceListFragment();
                        break;
                    case R.id.navigation_basket:
                        selected = new BasketFragment();
                        ((BasketFragment) selected).setListener(this);
                        break;
                    case R.id.navigation_account:
                        selected = new AuthFragment();
                        ((AuthFragment) selected).setListener(this);
                        break;
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selected)
                        .commit();

                return true;
            };


    @Override
    public void onDetailsItem(Dish dish) {
        DishDialogFragment dishDialogFragment = new DishDialogFragment(dish, mCurrentOrder.getCount(dish));
        dishDialogFragment.show(getSupportFragmentManager(), "dish bottom sheet");
        dishDialogFragment.setListener(this);
    }

    @Override
    public void loadImage(ImageView view, String url) {
        LoadImage loadImage = new LoadImage(view);
        loadImage.execute(url);
    }

    @Override
    public void onDetailsItem(MyPlace myPlace) {
        DishListFragment dishList = new DishListFragment(myPlace);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, dishList)
                .commit();
    }

    public Order getCurrentOrder() {
        return mCurrentOrder;
    }

    @Override
    public int increment(Dish dish) { return mCurrentOrder.increment(dish); }

    @Override
    public int decrement(Dish dish) {
        return mCurrentOrder.decrement(dish);
    }

    @Override
    public void onSubmitOrder() {
        Fragment order = new OrderFragment(mCurrentOrder);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, order)
                .commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void clickOnFavoriteButton() {

    }

    @Override
    public void clickOnPersonalButton() {
    }

    @Override
    public void clickOnOrdersButton() {
        Order order = new OrderEntity();
        Dish dish1 = new DishEntity("test", 120, 399, 200, "test test test", "");
        order.increment(dish1);

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order);
        orders.add(order);

        Fragment ordersListFragment = new OrderListFragment(orders);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ordersListFragment)
                .commit();

//        Fragment itemListFragment = new ItemListFragment(order);
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.fragment_container, itemListFragment)
//                .commit();
    }

    @Override
    public void clickOnSalesButton() {

    }

    @Override
    public void openPersonal() {
        Fragment personalFragment = new PersonalFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, personalFragment)
            .commit();
        ((PersonalFragment)personalFragment).setListener(this);
    }
}

class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public LoadImage(ImageView ivResult) {
        this.imageView = ivResult;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }
}

