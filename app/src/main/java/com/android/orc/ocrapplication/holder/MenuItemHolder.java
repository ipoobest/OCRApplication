package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RecyclerViewClickListener;

public class MenuItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView imgMenu;
    TextView nameMenu;

    RecyclerViewClickListener mListener;

    public ImageView getImgMenu() {
        return imgMenu;
    }

    public TextView getNameMenu() {
        return nameMenu;
    }

    public MenuItemHolder(View itemView, RecyclerViewClickListener mListener) {
        super(itemView);

        nameMenu = itemView.findViewById(R.id.tv_menu_item);
        imgMenu = itemView.findViewById(R.id.img_menu_item);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mListener.onClick(view, getLayoutPosition());
    }
}
