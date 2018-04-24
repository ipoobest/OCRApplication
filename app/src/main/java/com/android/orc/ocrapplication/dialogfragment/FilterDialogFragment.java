package com.android.orc.ocrapplication.dialogfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.Filters;
import com.android.orc.ocrapplication.dashboard.DashBoardActivity;

public class FilterDialogFragment extends DialogFragment implements View.OnClickListener {


    Spinner mCategorySpinner;
    Spinner mSortSpinner;
    Spinner mLimit;
    Button btnSort;
    Button btnCancel;

    Filters filters;

    public static final String TAG = "FilterDialog";


    interface FilterListener {

        void onFilter(Filters filters);

    }

    private View rootView;


    private FilterListener mFilterListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_filters, container, false);

        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        mCategorySpinner = rootView.findViewById(R.id.spinner_category);
        mSortSpinner = rootView.findViewById(R.id.spinner_sort);
        mLimit = rootView.findViewById(R.id.spinner_limit);
        btnSort = rootView.findViewById(R.id.button_search);
        btnCancel = rootView.findViewById(R.id.button_cancel);
//
//        if (filters == null) {
//            filters.setFilter("all");
//            filters.setSort("rating");
//            filters.setLimit(5);
//        } else {
//            filters.setFilter(mCategorySpinner.getSelectedItem().toString());
//            filters.setSort(mSortSpinner.getSelectedItem().toString());
//            filters.setLimit(10);
//        }

        btnSort.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnSort) {
            onSortClicked();
        } else if (v == btnCancel) {
            onCancelClicked();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void onSortClicked() {


        Intent intent = new Intent(getContext(), DashBoardActivity.class);
        filters = new Filters();
        if (mCategorySpinner.getSelectedItem() == null || mSortSpinner.getSelectedItem() == null) {
            filters.setFilter("all");
            filters.setSort("rating");
        }
        filters.setFilter(mCategorySpinner.getSelectedItem().toString());
        filters.setSort(mSortSpinner.getSelectedItem().toString());
        filters.setLimit(Integer.parseInt(mLimit.getSelectedItem().toString()));
        intent.putExtra("filters", filters);
        startActivity(intent);


//        if (mFilterListener != null) {
//            mFilterListener.onFilter(getFilters());
//
//
//        }

        dismiss();
    }

    public void onCancelClicked() {
        dismiss();
    }

    @Nullable
    private String getSelectedCategory() {
        String selected = (String) mCategorySpinner.getSelectedItem();
        if (getString(R.string.value_any_category).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }


//    @Nullable
//    private String getSelectedSortBy() {
//        String selected = (String) mSortSpinner.getSelectedItem();
//        if (getString(R.string.sort_by_rating).equals(selected)) {
//            return Restaurant.FIELD_AVG_RATING;
//        }
//        if (getString(R.string.sort_by_price).equals(selected)) {
//            return Restaurant.FIELD_PRICE;
//        }
//        if (getString(R.string.sort_by_popularity).equals(selected)) {
//            return Restaurant.FIELD_POPULARITY;
//        }
//
//        return null;
//    }

//    @Nullable
//    private Query.Direction getSortDirection() {
//        String selected = (String) mSortSpinner.getSelectedItem();
//        if (getString(R.string.sort_by_rating).equals(selected)) {
//            return Query.Direction.DESCENDING;
//        }
//        if (getString(R.string.sort_by_price).equals(selected)) {
//            return Query.Direction.ASCENDING;
//        }
//        if (getString(R.string.sort_by_popularity).equals(selected)) {
//            return Query.Direction.DESCENDING;
//        }
//
//        return null;
//    }

    public void resetFilters() {
        if (rootView != null) {
            mCategorySpinner.setSelection(0);
            mSortSpinner.setSelection(0);
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

//        if (rootView != null) {
//            filters.setCategory(getSelectedCategory());
//            filters.setCity(getSelectedCity());
//            filters.setPrice(getSelectedPrice());
//            filters.setSortBy(getSelectedSortBy());
//            filters.setSortDirection(getSortDirection());
//        }

        return filters;
    }


}
