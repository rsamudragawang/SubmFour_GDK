package com.ganargatul.submfour.db;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_MOVIE = "MOVIE";

    static final class MovieColoumn implements BaseColumns{
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String IMAGE = "IMAGE";
    }

    static String TABLE_TV="TV";

    static final class TvColoumn implements BaseColumns{
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String IMAGE = "IMAGE";
    }
}
