package com.nozimy.vegandelivery;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nozimy.vegandelivery.db.entity.OrderEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Order;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.basket.BasketFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishDialogFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishListFragment;
import com.nozimy.vegandelivery.ui.personal.PersonalFragment;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements DishListFragment.ListFragmentListener,
        PlaceListFragment.ListFragmentListener,
        DishDialogFragment.DishDialogListener {

    Order mCurrentOrder = new OrderEntity();
    Boolean isLargeLayout;

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
                        break;
                    case R.id.navigation_account:
                        selected = new PersonalFragment();
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
    public void onDetailsItem(Place place) {
        DishListFragment dishList = new DishListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, dishList)
                .commit();
    }

    public Order getCurrentOrder() {
        return mCurrentOrder;
    }

    @Override
    public void increment(Dish dish) {
        mCurrentOrder.increment(dish);
    }

    @Override
    public void decrement(Dish dish) {
        mCurrentOrder.decrement(dish);
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

