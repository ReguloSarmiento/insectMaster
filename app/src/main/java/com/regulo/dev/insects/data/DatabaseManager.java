package com.regulo.dev.insects.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.regulo.dev.insects.data.InsectContract.Bugs.DANGER_LEVEL_DESCENDING;
import static com.regulo.dev.insects.data.InsectContract.Bugs.NAMES_ASCENDING_ALPHABETICALLY;
import static com.regulo.dev.insects.data.InsectContract.Bugs.ORDER_BY_DANGER_LEVEL;
import static com.regulo.dev.insects.data.InsectContract.Bugs.ORDER_BY_NAMES;
import static com.regulo.dev.insects.data.InsectContract.Bugs.PROJECTION;
import static com.regulo.dev.insects.data.InsectContract.Bugs.TABLE_NAME;

/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private BugsDbHelper mBugsDbHelper;
    private SQLiteDatabase db;

    private DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
        db = mBugsDbHelper.getReadableDatabase();
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public Cursor queryAllInsects(String sortOrder) {
        return db.query(
                TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                sortOrder
        );
    }

    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        return db.query(TABLE_NAME,
                null, _ID + "=?",
                new String[]{ String.valueOf(id) },
                null,
                null,
                null);
    }


    public Cursor queryOrderBy(int sortOrder){
        switch (sortOrder){
            case ORDER_BY_NAMES:
                return db.query(
                        TABLE_NAME,
                        PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        NAMES_ASCENDING_ALPHABETICALLY
                );

            case ORDER_BY_DANGER_LEVEL:
                return db.query(
                        TABLE_NAME,
                        PROJECTION,
                        null,
                        null,
                        null,
                        null,
                        DANGER_LEVEL_DESCENDING
                );
        }

       return null;
    }


}
