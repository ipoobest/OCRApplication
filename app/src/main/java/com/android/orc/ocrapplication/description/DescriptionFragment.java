package com.android.orc.ocrapplication.description;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.orc.ocrapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by RUNGNUENG on 9/10/2560.
 */

public class DescriptionFragment extends Fragment {

    public DatabaseReference myRef;
    private TextView text_description_description;
    private TextView text_ingredient_menu_description;
    private TextView text_name_menu_description;
    private String urlImage;
    ImageView image_menu_description;
    String item, key, nameMenu, data;
    String[] name;
    String datas;

    public DescriptionFragment() {
        super();
    }

    public static DescriptionFragment newInstance() {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        item = getActivity().getIntent().getStringExtra("result");
        nameMenu = getActivity().getIntent().getStringExtra("recyclerMenu");
        data = getActivity().getIntent().getStringExtra("DAO");

        name = data.split("\n");

        for (int i = 0; i < name.length; i++) {
            datas = name[i];
        }


        initFirebase();
    }

    private void initFirebase() {
        myRef = FirebaseDatabase.getInstance().getReference("menu");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }


    private void init(Bundle savedInstanceState) {
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {


        image_menu_description = rootView.findViewById(R.id.image_menu_description);
        text_ingredient_menu_description = rootView.findViewById(R.id.text_ingredient_menu_description);
        text_name_menu_description = rootView.findViewById(R.id.text_name_menu_description);
        text_description_description = rootView.findViewById(R.id.text_description_description);

        if (nameMenu != null) {
            myRef.orderByChild("name")
                    .equalTo("" + nameMenu)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                key = childSnapshot.getKey();
//                            Toast.makeText(getContext(), "" + key, Toast.LENGTH_LONG).show();
                                initQueryFirebase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (item != null) {
            myRef.orderByChild("name")
                    .equalTo("" + item)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                key = childSnapshot.getKey();
//                            Toast.makeText(getContext(), "" + key, Toast.LENGTH_LONG).show();
                                initQueryFirebase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (datas != null) {
            myRef.orderByChild("nameThai")
                    .equalTo("" + datas)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                key = childSnapshot.getKey();
//                            Toast.makeText(getContext(), "" + key, Toast.LENGTH_LONG).show();
                                initQueryFirebase();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void initQueryFirebase() {

        Query query = myRef.child("" + key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();

                urlImage = String.valueOf(map.get("imgUrl"));
                //Toast.makxeText(getContext(),urlImage,Toast.LENGTH_LONG).show();

                String valueName = String.valueOf(map.get("name"));
                text_name_menu_description.setText(valueName);

                String valueDescription = String.valueOf(map.get("description"));
                text_description_description.setText(valueDescription);

                String valueIngredient = String.valueOf(map.get("ingredient"));
                text_ingredient_menu_description.setText(valueIngredient);

                Glide.with(getContext()).load(urlImage).into(image_menu_description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }
}
