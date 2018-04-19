package com.android.orc.ocrapplication.result;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.orc.ocrapplication.R;

public class ResultOcrActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ocr);

        toolbar = findViewById(R.id.result_ocr_toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        String request = getIntent().getStringExtra("stringRequest");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_result_ocr, ResultOcrFragment.newInstance(request))
                    .commit();
        }
    }
}
