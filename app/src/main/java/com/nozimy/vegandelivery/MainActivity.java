package com.nozimy.vegandelivery;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nozimy.vegandelivery.db.entity.OrderEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.db.model.MyPlace;
import com.nozimy.vegandelivery.ui.auth.AuthFragment;
import com.nozimy.vegandelivery.ui.favourite.FavouriteFragment;
import com.nozimy.vegandelivery.ui.order.OrderFragment;
import com.nozimy.vegandelivery.ui.basket.BasketFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishDialogFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishListFragment;
import com.nozimy.vegandelivery.ui.order.OrderListFragment;
import com.nozimy.vegandelivery.ui.personal.PersonalFragment;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;
import com.nozimy.vegandelivery.ui.stock.StockFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements DishListFragment.ListFragmentListener,
        PlaceListFragment.ListFragmentListener,
        DishDialogFragment.DishDialogListener,
        BasketFragment.BasketFragmentListener,
        OnMapReadyCallback,
        PersonalFragment.PersonalFragmentListener,
        AuthFragment.AuthListener,
        OrderListFragment.OrderFragmentListener,
        OrderFragment.OrderListener {

    private Order mCurrentOrder = new OrderEntity();
    private Boolean isLargeLayout;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String email = acct.getEmail();
            mCurrentOrder.setUser(email);
        }

        BottomNavigationView bottomNav = findViewById(R.id.navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        PlaceListFragment placeList = new PlaceListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, placeList)
                .addToBackStack(null)
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
                        .addToBackStack(null)
                        .commit();

                return true;
            };


    @Override
    public void onDetailsItem(Dish dish, MyPlace place) {
        DishDialogFragment dishDialogFragment = new DishDialogFragment(dish, mCurrentOrder.getCount(dish), place);
        dishDialogFragment.show(getSupportFragmentManager(), "dish bottom sheet");
        dishDialogFragment.setListener(this);
    }

    @Override
    public void loadImage(ImageView view, String url) {
        Glide.with(this).load(url).into(view);
    }

    @Override
    public void setAddress(String address, LatLng latLng) {
        mCurrentOrder.setUserAddress(address, latLng);
    }

    @Override
    public String getAddress() {
        return mCurrentOrder.getUserAddress();
    }

    @Override
    public void showNotification(String name) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.conflict_notification);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Button clearButton = dialog.findViewById(R.id.clean_button);
        View.OnClickListener mCorkyListener = v -> {
            mCurrentOrder.clear();
            dialog.dismiss();
        };
        clearButton.setOnClickListener(mCorkyListener);
    }

    @Override
    public void onDetailsItem(MyPlace myPlace) {
        DishListFragment dishList = new DishListFragment(myPlace);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, dishList)
                .addToBackStack(null)
                .commit();
    }

    public Order getCurrentOrder() {
        return mCurrentOrder;
    }

    @Override
    public int increment(Dish dish, MyPlace place) { return mCurrentOrder.increment(dish, place); }

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
                .addToBackStack(null)
                .commit();
        ((OrderFragment)order).setListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void clickOnFavoriteButton() {
        Fragment favouriteFragment = new FavouriteFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, favouriteFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clickOnOrdersButton() {
        Fragment ordersListFragment = new OrderListFragment(mCurrentOrder.getUser());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, ordersListFragment)
                .addToBackStack(null)
                .commit();
        ((OrderListFragment)ordersListFragment).setListener(this);
    }

    @Override
    public void clickOnSalesButton() {
        Fragment stockFragment = new StockFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, stockFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void clickOnLogout() {
        Fragment authFragment = new AuthFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, authFragment)
            .commit();
        ((AuthFragment)authFragment).setListener(this);
    }

    @Override
    public void setUser(String user) {
        mCurrentOrder.setUser(user);
    }

    @Override
    public void openPersonal() {
        Fragment personalFragment = new PersonalFragment();
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, personalFragment)
            .addToBackStack(null)
            .commit();
        ((PersonalFragment)personalFragment).setListener(this);
    }


}

