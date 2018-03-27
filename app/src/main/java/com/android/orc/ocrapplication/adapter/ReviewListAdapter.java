package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.holder.ReviewHolder;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.android.orc.ocrapplication.model.dao.ReviewListItem;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private ItemClickCallback mListener;
    private List<ReviewListItem> list;
    private Context context;


    public ReviewListAdapter(Context context, List<ReviewListItem> list, ItemClickCallback listener) {
        this.list = list;
        this.context = context;
        this.mListener = listener;
    }


    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item, parent, false);
        return new ReviewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(final ReviewHolder holder, int position) {

        ReviewListItem reviews = list.get(position);

        holder.facebookName.setText(reviews.facebookName);
        holder.review.setText(reviews.review);
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(holder.getAdapterPosition(),0,0,"abc");
                menu.add(holder.getAdapterPosition(),1,0,"def");
            }
        });

//        if (holder instanceof ReviewHolder) {
//            holder.facebookName.setText(reviews.facebookName);
//            holder.review.setText(reviews.review);
//
//
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
