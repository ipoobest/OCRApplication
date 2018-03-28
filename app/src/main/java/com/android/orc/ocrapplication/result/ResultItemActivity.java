package com.android.orc.ocrapplication.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.MenuItemDao;


public class ResultItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_item);

        MenuItemDao dao = getIntent().getParcelableExtra("dao");

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container_result, ResultFragment.newInstance(dao))
                    .commit();
        }
    }
}
