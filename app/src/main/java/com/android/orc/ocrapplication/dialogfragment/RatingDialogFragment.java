/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.orc.ocrapplication.dialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.Rating;
import com.android.orc.ocrapplication.manager.HttpManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Dialog Fragment containing rating form.
 */
public class RatingDialogFragment extends DialogFragment implements View.OnClickListener {

    MaterialRatingBar mRatingBar;
    EditText mRatingText;
    Button btnSubmit;
    Button btnCancel;
    Rating rating;

    RequestBody requestBody;

    public static final String TAG = "RatingDialog";


    interface RatingListener {

        void onRating(Rating rating);

    }

    private RatingListener mRatingListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_rating, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        mRatingBar = rootView.findViewById(R.id.menu_form_rating);
        mRatingText = rootView.findViewById(R.id.restaurant_form_text);
        btnSubmit = rootView.findViewById(R.id.restaurant_form_button);
        btnCancel = rootView.findViewById(R.id.restaurant_form_cancel);



        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof RatingListener) {
            mRatingListener = (RatingListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            Rating rating = new Rating(
                    FirebaseAuth.getInstance().getCurrentUser(),
                    mRatingBar.getRating(),
                    mRatingText.getText().toString());

            if (mRatingListener != null) {
                mRatingListener.onRating(rating);

                addComment();
            }

            dismiss();
        } else if (v == btnCancel) {
            dismiss();
        }

    }

    private void addComment() {
        Call<List<Rating>> call = HttpManager.getInstance().getService().addComment(requestBody);
        call.enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                //TODO: @PUT retrofir comment
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {

            }
        });
    }

}
