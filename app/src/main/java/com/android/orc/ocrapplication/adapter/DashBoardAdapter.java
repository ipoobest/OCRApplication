package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.holder.MenuListHolder;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class DashBoardAdapter extends RecyclerView.Adapter<MenuListHolder> {
    Context context;
    List<MenuItemDao> dao;

    private RecyclerViewClickListener mListener;

    public DashBoardAdapter(Context context, RecyclerViewClickListener listener ) {
        this.context = context;
        this.mListener = listener;
    }

    public void updateData(List<MenuItemDao> dataset) {
        dao.clear();
        dao.addAll(dataset);
        notifyDataSetChanged();

    }

    public void setDao(List<MenuItemDao> dao) {
        this.dao = dao;
    }

    @Override
    public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_item, parent, false);

        int height = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(height);

        return new MenuListHolder(view, mListener);


    }

    @Override
    public void onBindViewHolder(MenuListHolder holder, int position) {

        MenuItemDao item = dao.get(position);
        holder.getMenuName().setText(item.getNameThai());

        Glide.with(context)
                .load(item.getImgUrl())
                .into(holder.getMenuImage());
    }

    @Override
    public int getItemCount() {
        if (dao == null) {
            return 0;
        }
        if (dao.size() == 0) {
            return 0;
        }
        return dao.size();

    }
}
