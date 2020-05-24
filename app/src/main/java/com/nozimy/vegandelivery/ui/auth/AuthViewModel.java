package com.nozimy.vegandelivery.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.nozimy.vegandelivery.interactors.AccountInteractor;

public class AuthViewModel extends AndroidViewModel {
    private AccountInteractor mAccountInteractor;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        mAccountInteractor = new AccountInteractor(getApplication());
    }

    void insertPerson(GoogleSignInAccount account){
        mAccountInteractor.insertPerson(account);
    }

}
