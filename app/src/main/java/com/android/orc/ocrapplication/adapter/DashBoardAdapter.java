package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.holder.MenuListHolder;
import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class DashBoardAdapter extends RecyclerView.Adapter<MenuListHolder> {
    Context context;
    List<MenuItemDao> dao;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

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

        holder.getStar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getStar().setImageResource(R.drawable.ic_toggle_star_24);

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("favorite");

                addFavoriteList(position);
            }
        });
    }

    private void addFavoriteList(int position){
        Profile profile = Profile.getCurrentProfile();
        String id = myRef.push().getKey();
        myRef.child(id).child("name").setValue(dao.get(position).getName());
        myRef.child(id).child("nameThai").setValue(dao.get(position).getNameThai());
        myRef.child(id).child("description").setValue(dao.get(position).getDescription());
        myRef.child(id).child("ingredient").setValue(dao.get(position).getIngredient());
        myRef.child(id).child("imgUrl").setValue(dao.get(position).getImgUrl());
        myRef.child(id).child("facebookName").setValue(constructWelcomeMessage(profile));
        myRef.child(id).child("star").setValue("true");
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append(profile.getName());
        }
        return stringBuffer.toString();

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
