package com.android.orc.ocrapplication.dashboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.dashboard.fragment.DescriptionFragment;


public class DescriptionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_description,
                    DescriptionFragment.newInstance()).commit();

    }

}
