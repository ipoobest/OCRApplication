package com.android.orc.ocrapplication.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.android.orc.ocrapplication.model.dao.ReviewListItem;

public class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    private ItemClickCallback mListener;
    public TextView facebookName, review;

    public ReviewHolder(View itemView, ItemClickCallback listener) {
        super(itemView);

        mListener = listener;
        itemView.setOnClickListener(this);

        facebookName = itemView.findViewById(R.id.name_user);
        review = itemView.findViewById(R.id.review_user);

    }

    @Override
    public void onClick(View v) {
        mListener.onClick(v, getAdapterPosition());
    }

    public void setData(ReviewListItem reviews, int position) {

        this.facebookName.setText(reviews.facebookName);
        this.review.setText(reviews.review);
    }
}
