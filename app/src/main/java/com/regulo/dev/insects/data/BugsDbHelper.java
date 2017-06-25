package com.regulo.dev.insects.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.regulo.dev.insects.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.regulo.dev.insects.data.InsectContract.Bugs;

import static com.regulo.dev.insects.data.InsectContract.Bugs.COLUMN_CLASSIFICATION;
import static com.regulo.dev.insects.data.InsectContract.Bugs.COLUMN_DANGER_LEVEL;
import static com.regulo.dev.insects.data.InsectContract.Bugs.COLUMN_FRIENDLY_NAME;
import static com.regulo.dev.insects.data.InsectContract.Bugs.COLUMN_IMAGE_ASSET;
import static com.regulo.dev.insects.data.InsectContract.Bugs.COLUMN_SCIENTIFIC_NAME;
import static com.regulo.dev.insects.data.InsectContract.Bugs.TABLE_NAME;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "insects.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INSECTS = "insects";
    public static final String FRIENDLY_NAME = "friendlyName";
    public static final String SCIENTIFIC_NAME = "scientificName";
    public static final String CLASSIFICATION = "classification";
    public static final String IMAGE_ASSET = "imageAsset";
    public static final String DANGER_LEVEL = "dangerLevel";

    private static final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " +
            Bugs.TABLE_NAME + " (" +
            Bugs._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FRIENDLY_NAME + " TEXT NOT NULL, " +
            Bugs.COLUMN_SCIENTIFIC_NAME + " TEXT NOT NULL, " +
            Bugs.COLUMN_CLASSIFICATION + " TEXT NOT NULL, " +
            Bugs.COLUMN_IMAGE_ASSET + " TEXT NOT NULL, " +
            Bugs.COLUMN_DANGER_LEVEL + " INTEGER NOT NULL " + ");";

    //Used to read data from res/ and assets/
    private Resources mResources;

    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BUGS_TABLE);
        try {
            readInsectsFromResources(db);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(SQL_CREATE_BUGS_TABLE);
        }
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();

        JSONObject jsonObject = new JSONObject(rawJson);
        JSONArray jsonArray = jsonObject.getJSONArray(INSECTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FRIENDLY_NAME, jsonArray.getJSONObject(i).getString(FRIENDLY_NAME));
            values.put(COLUMN_SCIENTIFIC_NAME, jsonArray.getJSONObject(i).getString(SCIENTIFIC_NAME));
            values.put(COLUMN_CLASSIFICATION, jsonArray.getJSONObject(i).getString(CLASSIFICATION));
            values.put(COLUMN_IMAGE_ASSET, jsonArray.getJSONObject(i).getString(IMAGE_ASSET));
            values.put(COLUMN_DANGER_LEVEL, Integer.parseInt(jsonArray.getJSONObject(i).getString(DANGER_LEVEL)));
            db.insert(TABLE_NAME, null, values);
        }
    }
}
