package com.nozimy.vegandelivery.ui.dashboard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nozimy.vegandelivery.interactors.places.PlacesListInteractor;
import com.nozimy.vegandelivery.interactors.places.PlacesListInteractorDefault;

public class DashboardViewModel extends AndroidViewModel {

    private PlacesListInteractor interactor = PlacesListInteractorDefault.getInstance(getApplication());

    private MutableLiveData<String> mText;

    public DashboardViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}