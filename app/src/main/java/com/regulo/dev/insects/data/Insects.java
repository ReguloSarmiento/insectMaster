package com.regulo.dev.insects.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by regulosarmiento on 30/05/2017.
 */

/* This class was created to send an ArrayList<Insect> between components(e.g: Activities, IntentServices)
 through Intents.*/
public class Insects  extends ArrayList<Parcelable> implements Parcelable {

    private List<Insect> mInsectsList;

    public Insects(List<Insect> insectList){
        this.mInsectsList = insectList;
    }

    public List<Insect> getInsectsList() {
        return mInsectsList;
    }

    @Override
    public Parcelable get(int index) {
        return super.get(index);
    }

    public Insects(Parcel in) {
        mInsectsList = in.createTypedArrayList(Insect.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mInsectsList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Insects> CREATOR = new Creator<Insects>() {
        @Override
        public Insects createFromParcel(Parcel in) {
            return new Insects(in);
        }

        @Override
        public Insects[] newArray(int size) {
            return new Insects[size];
        }
    };
}
