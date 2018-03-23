package com.android.orc.ocrapplication.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;


import com.android.orc.ocrapplication.dao.MenuItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.poobest on 20/3/2018 AD.
 */

public class MenuListManager {

    private Context mContext;
    private List<MenuItemDao> dao;
//    MenuItemDao daoSaveState;

    public MenuListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public List<MenuItemDao> getDao() {
        return dao;
    }

    public void setDao(List<MenuItemDao> dao) {
        this.dao = dao;
    }


    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("dao", (ArrayList<? extends Parcelable>) dao);
        return null;


    }

    public void onRestoreInstanceState(Bundle saveInstanceState) {
        dao = saveInstanceState.getParcelableArrayList("dao");
    }
}
