package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.model.ItemClickCallback;

public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ItemClickCallback mListener;
    ImageView menuImage;
    TextView menuName;
    ImageView star;

    public ItemClickCallback getmListener() {
        return mListener;
    }

    public ImageView getMenuImage() {
        return menuImage;
    }

    public TextView getMenuName() {
        return menuName;
    }

    public ImageView getStar() {
        return star;
    }

    public ReviewHolder(View itemView, ItemClickCallback listener) {
        super(itemView);

        mListener = listener;
        itemView.setOnClickListener(this);

        menuImage = itemView.findViewById(R.id.menu_image);
        menuName = itemView.findViewById(R.id.menu_name);
        star = itemView.findViewById(R.id.star);

    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }

}
