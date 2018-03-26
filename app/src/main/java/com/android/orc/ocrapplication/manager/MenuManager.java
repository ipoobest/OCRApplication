package com.android.orc.ocrapplication.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.poobest on 20/3/2018 AD.
 */

public class MenuManager {

    private Context mContext;
    private MenuDao dao;
//    MenuItemDao daoSaveState;

    public MenuManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public MenuDao getDao() {
        return dao;
    }

    public void setDao(MenuDao dao) {
        this.dao = dao;
    }


    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", (Parcelable) dao);
        return null;


    }

    public void onRestoreInstanceState(Bundle saveInstanceState) {
        dao = saveInstanceState.getParcelable("dao");
    }
}
