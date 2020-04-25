package com.nozimy.vegandelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

public class MainActivity extends AppCompatActivity implements PlaceListFragment.ListFragmentListener{

    PlaceListFragment list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, BottomNavActivity.class);

        //startActivity(intent);

        list = new PlaceListFragment();
        list.setListener(this);

        //details = new DetailsFragment();
        //details.setListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, list)
                .commit();

    }

    @Override
    public void onDetailsItem(Place place) {

    }
}
