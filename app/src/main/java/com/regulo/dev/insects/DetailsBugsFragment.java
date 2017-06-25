package com.regulo.dev.insects;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.regulo.dev.insects.data.Insect;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsBugsFragment extends Fragment {

    private static final String EXTRA_INSECT_DETAILS = "insectExtra";
    private Insect mInsectDetails;
    private ImageView mInsectImageView;
    private TextView mCommonName;
    private TextView mScientificName;
    private TextView mClassification;
    private RatingBar mRatingBar;

    public DetailsBugsFragment() {
        // Required empty public constructor

    }

    public static DetailsBugsFragment newInstance(Insect mInsectDetails) {
        DetailsBugsFragment fragment = new DetailsBugsFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_INSECT_DETAILS, mInsectDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_bugs, container, false);
        mInsectImageView = (ImageView) view.findViewById(R.id.insectImage);
        mCommonName  = (TextView) view.findViewById(R.id.commonNameView);
        mScientificName = (TextView) view.findViewById(R.id.scientificNameView);
        mClassification = (TextView)view.findViewById(R.id.classification);
        mRatingBar  = (RatingBar) view.findViewById(R.id.rating);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCellValues();
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
        if(getArguments() != null ) {
            mInsectDetails = getArguments().getParcelable(EXTRA_INSECT_DETAILS);
        }
    }

    public void setInsectImage(final String imageName) {
        InputStream ims;
        try {
            ims = getActivity().getAssets().open(imageName);
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
