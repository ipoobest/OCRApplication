package com.android.orc.ocrapplication.callback;


import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;

/**
 * Created by j.poobest on 21/3/2018 AD.
 */

public interface FragmentListener {
    void onMenuItemClick(MenuItemDao dao);

    void onMenuItemClick(MenuDao dao);
}
