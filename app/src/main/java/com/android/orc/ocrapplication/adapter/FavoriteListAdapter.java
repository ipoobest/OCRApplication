package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.FavoriteListItem;
import com.android.orc.ocrapplication.holder.FavoriteHolder;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteHolder> {

    private ItemClickCallback mListener;
    private List<FavoriteListItem> list;
    Context context;

    public FavoriteListAdapter(Context context, List<FavoriteListItem> list, ItemClickCallback listener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new FavoriteHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        FavoriteListItem favoriteListItem = list.get(position);

        holder.getMenuName().setText(favoriteListItem.nameThai);

        holder.getStar().setImageResource(R.drawable.ic_toggle_star_24);

        Glide.with(context)
                .load(favoriteListItem.getImgUrl())
                .into(holder.getMenuImage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
