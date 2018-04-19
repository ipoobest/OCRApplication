package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.holder.MenuItemHolder;
import com.android.orc.ocrapplication.holder.MenuListHolder;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by j.poobest on 16/12/2017 AD.
 */

public class ResultListAdapter extends RecyclerView.Adapter<MenuItemHolder> {

    @NonNull
    @Override
    public MenuItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
