package com.android.orc.ocrapplication.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import com.android.orc.ocrapplication.dao.CommentDao;
import com.android.orc.ocrapplication.dao.MenuDao;

import java.util.List;

public class CommentManager {

    private Context mContext;
    private CommentDao dao;
//    MenuItemDao daoSaveState;

    public CommentManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public CommentDao getDao() {
        return dao;
    }

    public void setDao(CommentDao dao) {
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
