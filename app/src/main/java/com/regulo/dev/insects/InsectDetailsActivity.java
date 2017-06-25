package com.regulo.dev.insects;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.regulo.dev.insects.data.Insect;

import java.io.IOException;
import java.io.InputStream;

public class InsectDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_INSECT_DETAILS = "insectExtra";
    private Insect mInsectDetails;
    private ImageView mInsectImageView;
    private TextView mCommonName;
    private TextView mScientificName;
    private TextView mClassification;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insect_details);
        setupActionBar();
        mInsectImageView = (ImageView) findViewById(R.id.insectImage);
        mCommonName  = (TextView) findViewById(R.id.commonNameView);
        mScientificName = (TextView) findViewById(R.id.scientificNameView);
        mClassification = (TextView)findViewById(R.id.classification);
        mRatingBar  = (RatingBar) findViewById(R.id.rating);
        setCellValues();

    }

    public void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    public void setCellValues(){
        getInsectDetails();
        setInsectImage(mInsectDetails.imageAsset);
        setCommonName(mInsectDetails.name);
        setScientificName(mInsectDetails.scientificName);
        setClassification(mInsectDetails.classification);
        setDangerLevel(mInsectDetails.dangerLevel);
    }

    public void getInsectDetails() {
        if(getIntent().getParcelableExtra(EXTRA_INSECT_DETAILS) != null ) {
            mInsectDetails = getIntent().getParcelableExtra(EXTRA_INSECT_DETAILS);
        }
    }

    public void setInsectImage(final String imageName) {
        InputStream ims;
        try {
            ims = getAssets().open(imageName);
            mInsectImageView.setImageDrawable(Drawable.createFromStream(ims, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCommonName(final String commonName) {
        mCommonName.setText(commonName);
    }

    public void setScientificName(final String scientificName) {
        mScientificName.setText(scientificName);
    }

    public void setClassification(final String classification) {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.classification), classification);
        mClassification.setText(text);
    }

    public void setDangerLevel(int level){
        mRatingBar.setRating((float)level);
    }

}
