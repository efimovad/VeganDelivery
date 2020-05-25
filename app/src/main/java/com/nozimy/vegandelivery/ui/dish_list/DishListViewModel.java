package com.nozimy.vegandelivery.ui.dish_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.DishEntity;
import com.nozimy.vegandelivery.db.model.Dish;
import com.nozimy.vegandelivery.interactors.DishesInteractor;

import java.util.List;

public class DishListViewModel extends AndroidViewModel {
    private DishesInteractor interactor = new DishesInteractor(getApplication());
    private LiveData<List<Dish>> mDishList = interactor.getDishes();


    public DishListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Dish>> getDishList() {
        return mDishList;
    }

    public void refresh(int cafeId) {
        interactor.refresh(cafeId);
    }

}
