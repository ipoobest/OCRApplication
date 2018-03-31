package com.android.orc.ocrapplication.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.adapter.ReviewListAdapter;
import com.android.orc.ocrapplication.dao.ReviewListItem;
import com.android.orc.ocrapplication.model.ItemClickCallback;
import com.android.orc.ocrapplication.review.ReviewActivity;
import com.facebook.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by j.poobest on 9/24/2017 AD.
 */

public class ReviewFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ReviewListItem> listResult;
    private ReviewListAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    String first_name,last_name;
    TextView facebookName;
    EditText review;
    Button post;
    CardView cardView;

    public ReviewFragment() {
        super();
    }

    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        first_name = getActivity().getIntent().getStringExtra("name");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review_dashboard, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        //set firebase recyclerview
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("review");

        //set cardview
        cardView = rootView.findViewById(R.id.cardview_review);

        //set user review
        facebookName = rootView.findViewById(R.id.facebookName);
        if (first_name == null){
            Profile profile = Profile.getCurrentProfile();
            facebookName.setText(constructWelcomeMessage(profile));
        }else {
            facebookName.setText(first_name);
        }

        // set review add
        review = rootView.findViewById(R.id.review);
        post = rootView.findViewById(R.id.post);


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReviewList();
            }
        });

        listResult = new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler_view_review);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        ItemClickCallback listener = (view, position) -> {
            Intent intent = new Intent(getActivity(), ReviewActivity.class);
//            Toast.makeText(getContext(),listResult.get(position).getFacebookName() + " " + listResult.get(position).getReview(),Toast.LENGTH_LONG).show();
            intent.putExtra("recyclerReviewName", listResult.get(position).getFacebookName());
            intent.putExtra("recyclerReviewReview",listResult.get(position).getReview());
            startActivity(intent);
        };

        updateList();
        adapter = new ReviewListAdapter(getContext(),listResult,listener);
        recyclerView.setAdapter(adapter);
    }

    private String constructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append(profile.getName());
        }
        return stringBuffer.toString();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void addReviewList() {

        String FacebookName = facebookName.getText().toString().trim();
        String Review = review.getText().toString().trim();

        if (TextUtils.isEmpty(Review)) {
            Toast.makeText(getContext(), "Please Review", Toast.LENGTH_SHORT).show();
        } else {
            String id = myRef.push().getKey();
            ReviewListItem reviewListItem = new ReviewListItem();
            myRef.child(id).child("facebookName").setValue(FacebookName.toString());
            myRef.child(id).child("review").setValue(Review.toString());
            Toast.makeText(getContext(), "Review is Post", Toast.LENGTH_SHORT).show();
            Cleartxt();
        }

    }

    private void Cleartxt() {
        review.setText("");
    }

    private void updateList() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listResult.removeAll(listResult);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ReviewListItem list = snapshot.getValue(ReviewListItem.class);
                    listResult.add(list);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
