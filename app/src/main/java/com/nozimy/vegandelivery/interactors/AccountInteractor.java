package com.nozimy.vegandelivery.interactors;

import android.content.Context;

import com.nozimy.vegandelivery.db.DataRepository;
import com.nozimy.vegandelivery.network.AccountApi;

public class AccountInteractor {
    private final Context mContext;
    private DataRepository mDataRepository;
    private AccountApi mAccountApi;

    public AccountInteractor(Context context){
        mContext = context;
        mDataRepository = new DataRepository(context);
    }

}
