package com.nozimy.vegandelivery.ui.place_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.nozimy.vegandelivery.R;
import com.nozimy.vegandelivery.db.entity.PlaceEntity;
import com.nozimy.vegandelivery.db.model.MyPlace;

import java.util.List;

public class FavouriteFragment extends Fragment implements PlaceAdapter.AdapterListener {
    private PlaceAdapter adapter;
    private PlaceListViewModel mPlaceListViewModel;
    private RecyclerView list;
    private List<MyPlace> mMyPlaceList;
    private PlaceListFragment.ListFragmentListener listener;
    private Observer<List<PlaceEntity>> observer;
    private TextView text;

    public void setListener(PlaceListFragment.ListFragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favourite, null);

        list = v.findViewById(R.id.shop_list);
        list.setLayoutManager(
                new GridLayoutManager(this.getContext(), 1)
        );
        ((SimpleItemAnimator) list.getItemAnimator()).setSupportsChangeAnimations(false);

        adapter = new PlaceAdapter(getContext());
        adapter.setHasStableIds(true);
        adapter.setListener(this);
        list.setAdapter(adapter);

        observer = places -> {
            if (places != null) {
                List<MyPlace> list = mPlaceListViewModel.transformPlaceEntityToMyPlace(places);
                adapter.setPlaceList(list);
                mMyPlaceList = list;

                text = v.findViewById(R.id.favorite_empty);
                if (list.isEmpty()) {
                    text.setVisibility(View.VISIBLE);
                } else {
                    text.setVisibility(View.INVISIBLE);
                }
            }
        };
        mPlaceListViewModel = ViewModelProviders.of(this).get(PlaceListViewModel.class);
        mPlaceListViewModel.getFavPlaceList().observe(getViewLifecycleOwner(), observer);

        return v;
    }

    @Override
    public void onItemClick(MyPlace myPlace) {
        listener.onDetailsItem(myPlace);
    }

    @Override
    public void loadPlaceImage(ImageView view, String url) {
        listener.loadImage(view, url);
    }

    @Override
    public void changeFavStatus(long id, boolean value, int position) {
        mPlaceListViewModel.changeFavStatus(id, value);
        int size = adapter.removeAt(position);
        if (size == 0) {
            text.setVisibility(View.VISIBLE);
        }
    }
}
