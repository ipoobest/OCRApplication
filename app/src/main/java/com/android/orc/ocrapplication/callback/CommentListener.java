package com.android.orc.ocrapplication.callback;

import com.android.orc.ocrapplication.dao.CommentDao;

public interface CommentListener {
    void onSubmitComment(CommentDao commentDao);
}
