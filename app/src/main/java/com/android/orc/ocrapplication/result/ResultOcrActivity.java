package com.android.orc.ocrapplication.result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.orc.ocrapplication.R;

public class ResultOcrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ocr);

        String request = getIntent().getStringExtra("stringRequest");

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_result_ocr, ResultOcrFragment.newInstance(request))
                    .commit();
        }
    }
}
