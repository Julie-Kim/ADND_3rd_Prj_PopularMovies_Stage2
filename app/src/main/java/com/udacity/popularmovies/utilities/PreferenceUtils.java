package com.udacity.popularmovies.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public final class PreferenceUtils {

    private static final String TAG = PreferenceUtils.class.getSimpleName();

    private static final String PREF_NAME = "pref";
    private static final String PREF_KEY_SORT_BY = "sort_by";
    private static final int SORT_BY_DEFAULT_VALUE = 0;

    public static void setSoftBySettingValue(Context context, int value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_KEY_SORT_BY, value);
        editor.apply();
    }

    public static int getSoftBySettingValue(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        int value = pref.getInt(PREF_KEY_SORT_BY, SORT_BY_DEFAULT_VALUE);

        if (value < SORT_BY_DEFAULT_VALUE) {
            value = SORT_BY_DEFAULT_VALUE;
        }
        return value;
    }
}
