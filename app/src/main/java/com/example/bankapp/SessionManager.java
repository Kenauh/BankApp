package com.example.bankapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREFS = "bankapp_session";
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_NAME = "name";

    private final SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void saveLogin(String account, String name) {
        preferences.edit()
                .putString(KEY_ACCOUNT, account)
                .putString(KEY_NAME, name)
                .apply();
    }

    public String getAccount() {
        return preferences.getString(KEY_ACCOUNT, null);
    }

    public String getName() {
        return preferences.getString(KEY_NAME, "Usuario");
    }

    public void clear() {
        preferences.edit().clear().apply();
    }
}
