package com.nozimy.vegandelivery.ui.personal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nozimy.vegandelivery.interactors.AccountInteractor;

import java.util.Arrays;
import java.util.List;

public class PersonalViewModel extends AndroidViewModel {

    private AccountInteractor mAccountInteractor;
    private MutableLiveData<String> mText;

    private MutableLiveData<List<String>> settingsItems;

    public PersonalViewModel(@NonNull Application application) {
        super(application);

        mAccountInteractor = new AccountInteractor(getApplication());
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