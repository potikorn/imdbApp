package com.example.potikorn.movielists;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance(savedInstanceState);
    }

    private void initInstance(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            Fragment fragment = MainFragment.newInstance();
            assert fragment != null;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, fragment, fragment.getClass().getName())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


}
