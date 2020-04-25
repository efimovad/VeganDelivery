package com.nozimy.vegandelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.dish_list.DishListFragment;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

public class MainActivity extends AppCompatActivity implements DishListFragment.ListFragmentListener,
        PlaceListFragment.ListFragmentListener {

    private DishListFragment dishList;
    private PlaceListFragment placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, BottomNavActivity.class);
        //startActivity(intent);

        dishList = new DishListFragment();
        dishList.setListener(this);

        placeList = new PlaceListFragment();
        placeList.setListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, placeList)
                .commit();

    }

    @Override
    public void onDetailsItem(Dish dish) {

    }

    @Override
    public void onDetailsItem(Place place) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, dishList)
                .commit();
    }
}
