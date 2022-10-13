package com.chat.lightchat.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPreferencesManager {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private Context context;
    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void putValue(String key, Object value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Integer){
            editor.putInt(key, (int) value);
        } else if(value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if(value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if(value instanceof String) {
            editor.putString(key, value.toString());
        } else if(value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if(value instanceof Set) {
            editor.putStringSet(key, (Set<String>) value);
        }
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
    public Boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void clearSharedPreferences (String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(key).apply();
    }
}
