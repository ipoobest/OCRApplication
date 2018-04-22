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
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by j.poobest on 16/12/2017 AD.
 */

public class ResultListAdapter extends RecyclerView.Adapter<MenuItemHolder> {

    Context context;
    List<MenuDao> dao;

    private RecyclerViewClickListener mListener;

    public ResultListAdapter(Context context, RecyclerViewClickListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public void setDao(List<MenuDao> dao) {
        this.dao = dao;
    }

    @Override
    public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);

//        int height = parent.getMeasuredHeight() / 3;
//        view.setMinimumHeight(height);

        return new MenuItemHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemHolder holder, int position) {
        MenuDao item = dao.get(position);
        holder.getMenuName().setText(item.getName());
        if (item.getQuantityRating() == null){
            holder.getMaterialRatingBar().setNumStars(0);
        }else {
            holder.getMaterialRatingBar().setNumStars(item.getQuantityRating().intValue());
        }


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
