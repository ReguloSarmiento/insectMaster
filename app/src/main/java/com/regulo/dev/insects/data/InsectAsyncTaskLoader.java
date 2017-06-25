package com.regulo.dev.insects.data;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import static com.regulo.dev.insects.ListBugsFragment.ALL_INSECTS_LOADER;
import static com.regulo.dev.insects.ListBugsFragment.MOST_DANGER_LOADER;
import static com.regulo.dev.insects.ListBugsFragment.NAMES_ALPHABETICALLY_LOADER;
import static com.regulo.dev.insects.data.InsectContract.Bugs.ORDER_BY_DANGER_LEVEL;
import static com.regulo.dev.insects.data.InsectContract.Bugs.ORDER_BY_NAMES;

/**
 * Created by regulosarmiento on 30/05/2017.
 */

// Performs database queries in a background thread.
public class InsectAsyncTaskLoader extends AsyncTaskLoader<Cursor> {

    private final int mWhichQuery;
    private final DatabaseManager mDatabaseManager;

    public InsectAsyncTaskLoader(Context context, int whichQuery) {
        super(context);
        this.mWhichQuery = whichQuery;
        this.mDatabaseManager = DatabaseManager.getInstance(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Cursor loadInBackground() {
        switch (mWhichQuery) {
            // All insects
            case ALL_INSECTS_LOADER:
                return mDatabaseManager.queryAllInsects(null);

            //Order alphabetically
            case NAMES_ALPHABETICALLY_LOADER:
                return mDatabaseManager.queryOrderBy(ORDER_BY_NAMES);

            //Order most danger first
            case MOST_DANGER_LOADER:
                return mDatabaseManager.queryOrderBy(ORDER_BY_DANGER_LEVEL);
        }
        return mDatabaseManager.queryAllInsects(null);
    }
}
