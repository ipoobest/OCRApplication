package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.holder.MenuHolder;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.android.orc.ocrapplication.model.dao.MenuListItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuHolder> {

    private ItemClickCallback mListener;
    private List<MenuListItem> list;
    private Context context;

    public MenuListAdapter(Context context, List<MenuListItem> list, ItemClickCallback listener) {
        this.list = list;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new MenuHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(final MenuHolder holder, int position) {

        MenuListItem menues = list.get(position);

        if (holder instanceof MenuHolder) {
            holder.textMenu.setText(menues.name);
            Glide.with(context).load(menues.getImgUrl()).into(holder.imgMenu);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}