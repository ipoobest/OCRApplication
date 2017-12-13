package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;


/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class MenuHolder extends RecyclerView.ViewHolder{

    public TextView textMenu;
    public ImageView imgMenu;

    public MenuHolder(View itemView) {
        super(itemView);

        textMenu = itemView.findViewById(R.id.text_name_menu);
        imgMenu = itemView.findViewById(R.id.image_menu_list);

    }

}
