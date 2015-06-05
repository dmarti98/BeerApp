package com.argumedo.kevin.beerapp;

import android.provider.BaseColumns;

/**
 * Created by mark on 5/7/15.
 */
//String id, owner, secret, server, farm, title;
//        Boolean isPublic, isFriend, isFamily;

public class Contract {
    public static final String DATABASE_NAME = "kevinsucks1.db";

    public static final class PhotoEntry implements BaseColumns {
        //beerId, name, description, abv, pic, styleId

        public static final String TABLE_NAME = "photo_entry";

        public static final String _ID = "id";
        public static final String NAME = "name";
        public static final String ABV = "abv";
        public static final String STYLE = "styleId";
        public static final String TYPE = "type";


    }
}