package com.android.orc.ocrapplication.result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.HomeAdapter;
import com.android.orc.ocrapplication.adapter.ReviewListAdapter;
import com.android.orc.ocrapplication.callback.CommentListener;
import com.android.orc.ocrapplication.dao.CommentDao;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dialogfragment.CommentDialogFragment;
import com.android.orc.ocrapplication.manager.HttpManager;
import com.android.orc.ocrapplication.manager.MenuListManager;
import com.bumptech.glide.Glide;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by j.poobest on 19/3/2018 AD.
 */

public class HomeResultFragment extends Fragment implements View.OnClickListener, CommentListener {

    ImageView imgMenu;
    TextView tvNameMenu;
    TextView tvDescription;
    TextView tvIngredient;
    TextView tvRating;
    MaterialRatingBar materialRatingBar;
    FloatingActionButton floatingActionButton;
    View bottomSheet;
    BottomSheetBehavior bottomSheetBehavior;
    CommentDialogFragment mRatingDialog;

    MenuListManager menuListManager;
    RecyclerView recyclerView;
    ReviewListAdapter adapter;

    MenuDao dao;

    public static HomeResultFragment newInstance(MenuDao dao) {
        HomeResultFragment fragment = new HomeResultFragment();
        Bundle args = new Bundle();
        args.putParcelable("dao", dao);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = getArguments().getParcelable("dao");
        menuListManager = new MenuListManager();


    }

    @Override
    public void onResume() {
        super.onResume();

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
        tvRating = rootView.findViewById(R.id.menu_num_ratings);
        materialRatingBar = rootView.findViewById(R.id.menu_rating);

        tvNameMenu.setText(dao.getName());
        tvDescription.setText(dao.getDescription());
        tvIngredient.setText(dao.getIngredient());

        if (dao.getQuantityRating() == null ) {
            tvRating.setText("0");
            materialRatingBar.setNumStars(0);

        } else {
            tvRating.setText(dao.getQuantityRating().toString());
            materialRatingBar.setNumStars(dao.getRating().intValue());
        }

        Glide.with(HomeResultFragment.this)
                .load(dao.getImgUrl())
                .into(imgMenu);


        floatingActionButton = rootView.findViewById(R.id.bottom_sheet_fab);
        floatingActionButton.setOnClickListener(this);
        bottomSheet = rootView.findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mRatingDialog = CommentDialogFragment.newInstance(dao.getNameThai());

        //TODO : set notfifysetchange
        recyclerView = rootView.findViewById(R.id.review_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new ReviewListAdapter(dao.getReview());
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        if (v == floatingActionButton) {
            mRatingDialog.show(getChildFragmentManager(), CommentDialogFragment.TAG);
        }
    }

    @Override
    public void onSubmitComment(String nameThai, CommentDao commentDao) {
        Call<MenuDao> call = HttpManager.getInstance().getService().addComment(nameThai, commentDao);
        call.enqueue(new Callback<MenuDao>() {
            @Override
            public void onResponse(Call<MenuDao> call, Response<MenuDao> response) {
                if (response.isSuccessful()){
                    loadComment();
                }
            }

            @Override
            public void onFailure(Call<MenuDao> call, Throwable t) {

            }
        });
    }

    private void loadComment(){
        Call<List<MenuDao>> call = HttpManager.getInstance().getService().loadMenuItem();
        call.enqueue(new Callback<List<MenuDao>>() {
            @Override
            public void onResponse(Call<List<MenuDao>> call, Response<List<MenuDao>> response) {
                if (response.isSuccessful()){
                    List<MenuDao> dao = response.body();
                    List<CommentDao> commentDaos = dao.get(0).getReview();
                    menuListManager.setDao(dao);
                    adapter.setDao(commentDaos);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<MenuDao>> call, Throwable t) {

            }
        });
    }
}
