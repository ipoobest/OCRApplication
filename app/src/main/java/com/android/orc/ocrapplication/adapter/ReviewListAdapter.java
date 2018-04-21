package com.android.orc.ocrapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.CommentDao;
import com.android.orc.ocrapplication.holder.ReviewHolder;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewHolder> {

    private List<CommentDao> dao;
    Context context;


    public ReviewListAdapter(List<CommentDao> dao, Context context) {
        this.dao = dao;
        this.context = context;
    }


    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        CommentDao item = dao.get(position);
        holder.getTvUserName().setText(item.getUserName());
        holder.getTvDate().setText(item.getDateTime().toString());
        holder.getRatingBar().setNumStars(item.getRating().intValue());
        holder.getTvComment().setText(item.getComment());

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
