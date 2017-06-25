package com.regulo.dev.insects;

import android.app.FragmentTransaction;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;

import com.regulo.dev.insects.data.Insect;


public class MainActivity extends AppCompatActivity implements ListBugsFragment.IOnClickListener{
    private boolean mDualPane;
    private static final String EXTRA_INSECT_DETAILS = "insectExtra";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = findViewById(R.id.details_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDualPane = view != null && view.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onItemSelected(Insect insect) {
        if(mDualPane){
            DetailsBugsFragment newDetails = DetailsBugsFragment.newInstance(insect);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.details_container, newDetails)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }else {
            Intent intent = new Intent(this, InsectDetailsActivity.class);
            intent.putExtra(EXTRA_INSECT_DETAILS, insect);
            startActivity(intent);
        }
    }

}
