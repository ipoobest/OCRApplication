package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.model.ItemClickCallback;


/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class MenuHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickCallback mListener;

    public TextView textMenu;
    public ImageView imgMenu;

    public MenuHolder(View itemView, ItemClickCallback listener) {
        super(itemView);

        mListener = listener;
        itemView.setOnClickListener(this);

        textMenu = itemView.findViewById(R.id.text_name_menu);
        imgMenu = itemView.findViewById(R.id.image_menu_list);

    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }
}
