package com.nozimy.vegandelivery.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthViewModel extends AndroidViewModel {


    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

}
