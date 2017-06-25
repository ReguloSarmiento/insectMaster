package com.regulo.dev.insects.data;

import android.provider.BaseColumns;

/**
 * Created by regulosarmiento on 29/05/2017.
 */

public class InsectContract {

    public static final class Bugs implements BaseColumns {

        public static final String TABLE_NAME = "bugs";

        public static final String COLUMN_FRIENDLY_NAME = "friendlyName";
        public static final String COLUMN_SCIENTIFIC_NAME = "scientificName";
        public static final String COLUMN_CLASSIFICATION = "classification";
        public static final String COLUMN_IMAGE_ASSET = "imageAsset";
        public static final String COLUMN_DANGER_LEVEL = "dangerLevel";

        public static final String[] PROJECTION = new String[]{
                _ID,
                COLUMN_FRIENDLY_NAME,
                COLUMN_SCIENTIFIC_NAME,
                COLUMN_CLASSIFICATION,
                COLUMN_IMAGE_ASSET,
                COLUMN_DANGER_LEVEL
        };

        public static final int INDEX_COLUMN_FRIENDLY_NAME = 1;
        public static final int INDEX_COLUMN_SCIENTIFIC_NAME = 2;
        public static final int INDEX_COLUMN_CLASSIFICATION = 3;
        public static final int INDEX_COLUMN_IMAGE_ASSET = 4;
        public static final int INDEX_COLUMN_DANGER_LEVEL = 5;

        public static final String NAMES_ASCENDING_ALPHABETICALLY = COLUMN_FRIENDLY_NAME + " " + "ASC";
        public static final String DANGER_LEVEL_DESCENDING = COLUMN_DANGER_LEVEL + " " + "DESC";
        public static final int ORDER_BY_NAMES = 0;
        public static final int ORDER_BY_DANGER_LEVEL = 1;
    }
}
