package com.adeo.rxjava.demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.adeo.rxjava.demo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.activity_main, new MainFragment())
                .commit();
    }
}
