package com.example.antonellab.unitatidecazare;

import android.provider.BaseColumns;

/**
 * Created by AntonellaB on 20-Oct-16.
 */

public final class HotelsDBSchema {
    private HotelsDBSchema () {}
    public static class HotelsTable implements BaseColumns {
        public static final String HOTEL_TABLE_NAME = "hotel";
        public static final String HOTEL_COLUMN_ID = "_id";
        public static final String HOTEL_COLUMN_NAME = "name";
        public static final String HOTEL_COLUMN_ADDRESS = "address";
        public static final String HOTEL_COLUMN_WEBPAGE = "webpage";
        public static final String HOTEL_COLUMN_PHONE = "phone";
    }
}