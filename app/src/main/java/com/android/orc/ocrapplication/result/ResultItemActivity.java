package com.android.orc.ocrapplication.result;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.RatingListener;
import com.android.orc.ocrapplication.dao.CommentDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.dialogfragment.CommentDialogFragment;


public class ResultItemActivity extends AppCompatActivity  {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_item);

        toolbar = findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        MenuItemDao dao = getIntent().getParcelableExtra("dao");


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container_result, ResultFragment.newInstance(dao))
                    .commit();
        }
    }


}
