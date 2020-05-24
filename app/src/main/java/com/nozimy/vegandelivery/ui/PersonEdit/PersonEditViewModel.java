package com.nozimy.vegandelivery.ui.PersonEdit;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nozimy.vegandelivery.db.entity.PersonEntity;
import com.nozimy.vegandelivery.interactors.AccountInteractor;

public class PersonEditViewModel extends AndroidViewModel {

    private AccountInteractor mAccountInteractor;

    public PersonEditViewModel(@NonNull Application application) {
        super(application);

        mAccountInteractor = new AccountInteractor(getApplication());
    }

    LiveData<PersonEntity> getCurrentPerson(){
        return mAccountInteractor.getCurrentPerson();
    }

    void savePerson(String name, String phone, String email){
        mAccountInteractor.updateCurrentPerson(name, phone, email);
    }
}
