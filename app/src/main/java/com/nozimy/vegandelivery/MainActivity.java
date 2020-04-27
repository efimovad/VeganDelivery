package com.nozimy.vegandelivery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.db.model.Place;
import com.nozimy.vegandelivery.ui.basket.BasketFragment;
import com.nozimy.vegandelivery.ui.dish_list.DishListFragment;
import com.nozimy.vegandelivery.ui.home.HomeFragment;
import com.nozimy.vegandelivery.ui.personal.PersonalFragment;
import com.nozimy.vegandelivery.ui.place_list.PlaceListFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements DishListFragment.ListFragmentListener, PlaceListFragment.ListFragmentListener {

//    private List<Dish> mDishList;

    RequestQueue mQueue;
    private void sendReq() {
        mQueue = Volley.newRequestQueue(this);
    }

//    private void jsonParse() {
//        String url = "https://vegan-delivery-api.herokuapp.com/api/v1/dishes";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        mDishList = new ArrayList<>();
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("dishes");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonDish = jsonArray.getJSONObject(i);
//
//                                //int id = jsonDish.getInt("id");
//                                String name = jsonDish.getString("name");
//                                String ingredients = jsonDish.getString("ingredients");
//                                int calories = jsonDish.getInt("calories");
//                                int weight = jsonDish.getInt("weight");
//                                int cost = jsonDish.getInt("cost");
//                                String image = jsonDish.getString("image");
//
//                                Dish dish = new DishEntity(name, cost, calories, weight, ingredients, image);
//                                mDishList.add(dish);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        mQueue.add(request);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
//        jsonParse();

        BottomNavigationView bottomNav = findViewById(R.id.navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        PlaceListFragment placeList = new PlaceListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, placeList)
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
        }
    };

    @Override
    public void onDetailsItem(Dish dish) {

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

