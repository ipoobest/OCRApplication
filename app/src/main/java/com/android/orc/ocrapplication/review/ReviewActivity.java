package com.android.orc.ocrapplication.review;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;

public class ReviewActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView facebookName;
    TextView review;
    String name,post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        name = getIntent().getStringExtra("recyclerReviewName");
        post = getIntent().getStringExtra("recyclerReviewReview");
//        Toast.makeText(this,name + " " + post,Toast.LENGTH_SHORT).show();

        showReview();
        initInstance();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showReview() {
        facebookName = findViewById(R.id.facebookName);
        review = findViewById(R.id.review);
        facebookName.setText(name);
        review.setText(post);

    }
}
