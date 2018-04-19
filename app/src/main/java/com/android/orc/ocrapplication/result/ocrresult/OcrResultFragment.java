package com.android.orc.ocrapplication.result.ocrresult;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dialogfragment.RatingDialogFragment;
import com.bumptech.glide.Glide;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class OcrResultFragment extends Fragment implements View.OnClickListener {

    ImageView imgMenu;
    TextView tvNameMenu;
    TextView tvDescription;
    TextView tvIngredient;
    FloatingActionButton floatingActionButton;
    View bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    RatingDialogFragment mRatingDialog;

    MenuDao dao;

    public static OcrResultFragment newInstance(MenuDao dao) {
        OcrResultFragment fragment = new OcrResultFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = getArguments().getParcelable("dao");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        initInstances(rootView);
        return rootView;

    }

    private void initInstances(View rootView) {
        //find view by id
        imgMenu = rootView.findViewById(R.id.image_menu_description);
        tvNameMenu = rootView.findViewById(R.id.text_name_menu_description);
        tvDescription = rootView.findViewById(R.id.text_description_description);
        tvIngredient = rootView.findViewById(R.id.text_ingredient_menu_description);

        tvNameMenu.setText(dao.getName());
        tvDescription.setText(dao.getDescription());
        tvIngredient.setText(dao.getIngredient());

        Glide.with(OcrResultFragment.this)
                .load(dao.getImgUrl())
                .into(imgMenu);


        floatingActionButton = rootView.findViewById(R.id.bottom_sheet_fab);
        floatingActionButton.setOnClickListener(this);
        bottomSheet = rootView.findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mRatingDialog = new RatingDialogFragment();

        bottomSheets();


    }

    private void bottomSheets() {
        // init the bottom sheet behavior
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);



    }

    @Override
    public void onClick(View v) {
        if (v == floatingActionButton) {
            mRatingDialog.show(getChildFragmentManager(), RatingDialogFragment.TAG);
        }
    }

    //TODO:: REcyclerView review
}
