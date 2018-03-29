package com.android.orc.ocrapplication.result;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.callback.FragmentListener;
import com.android.orc.ocrapplication.dao.MenuDao;
import com.android.orc.ocrapplication.dao.MenuItemDao;
import com.android.orc.ocrapplication.description.DescriptionActivity;

public class ResultOcrActivity extends AppCompatActivity implements FragmentListener {

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

    @Override
    public void onMenuItemClick(MenuItemDao dao) {

    }

    @Override
    public void onMenuClick(MenuDao dao) {

        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("menudao", dao);
        startActivity(intent);
    }
}
