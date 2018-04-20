package com.android.orc.ocrapplication.result.ocrresult;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dao.MenuDao;

public class OrcDescriptionActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ocr);

        toolbar = findViewById(R.id.result_ocr_toolbar);
        setSupportActionBar(toolbar);

        MenuDao dao = getIntent().getParcelableExtra("dao");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_result_ocr, OcrDescriptionFragment.newInstance(dao))
                    .commit();
        }
    }
}