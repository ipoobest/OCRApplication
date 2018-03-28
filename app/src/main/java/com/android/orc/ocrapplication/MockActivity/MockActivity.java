package com.android.orc.ocrapplication.MockActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.orc.ocrapplication.R;

public class MockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        String request = getIntent().getStringExtra("stringRequest");

        TextView tv = findViewById(R.id.text_show);
        tv.setText(request);
    }
}
