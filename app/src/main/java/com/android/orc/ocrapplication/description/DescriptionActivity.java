package com.android.orc.ocrapplication.description;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;


public class DescriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        MenuDao dao = getIntent().getParcelableExtra("menudao");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_description, DescriptionFragment.newInstance(dao))
                    .commit();
        }
    }


}
