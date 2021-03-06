package com.android.orc.ocrapplication.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.MenuDao;


public class HomeResultItemActivity extends AppCompatActivity  {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_item);
        innitInstance();

        MenuDao dao = getIntent().getParcelableExtra("dao");


        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_container_result, HomeResultFragment.newInstance(dao))
                    .commit();
        }
    }

    private void innitInstance() {
        toolbar = findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);
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
}
