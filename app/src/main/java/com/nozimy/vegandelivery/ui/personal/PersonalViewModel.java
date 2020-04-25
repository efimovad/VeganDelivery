package com.nozimy.vegandelivery.ui.personal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nozimy.vegandelivery.db.model.Place;

import java.util.Arrays;
import java.util.List;

public class PersonalViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<List<String>> settingsItems;

    public PersonalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
        settingsItems = new MutableLiveData<>();
        settingsItems.setValue(Arrays.asList("Мои заказы", "Мои данные", "Любимые рестораны"));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<String>> getSettingItems() {
        return settingsItems;
    }
}