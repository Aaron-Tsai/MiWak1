package com.example.android.miwak1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.miwak1.FamilyFragment;
import com.example.android.miwak1.NumbersFragment;
import com.example.android.miwak1.R;

public class FamilyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FamilyFragment())
                .commit();
    }
}