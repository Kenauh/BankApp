package com.example.bankapp;

import android.content.Context;

/**
 * SessionManager.java  -- VERSION SIN PERSISTENCIA
 *
 * Datos de sesion guardados SOLO en memoria estatica.
 * Cuando el proceso muere (usuario cierra la app), todo se borra
 * y la proxima vez se pide login de nuevo.
 */
public class SessionManager {

    private static String sToken   = null;
    private static String sUserId  = null;
    private static String sAccount = null;
    private static String sName    = "";

    public SessionManager(Context context) { /* no necesita contexto */ }

    public void saveSession(String token, String userId, String account, String name) {
        sToken   = token;
        sUserId  = userId;
        sAccount = account;
        sName    = name != null ? name : "";
    }

    public void clearSession() {
        sToken   = null;
        sUserId  = null;
        sAccount = null;
        sName    = "";
    }

    public String getToken()   { return sToken; }
    public String getUserId()  { return sUserId; }
    public String getAccount() { return sAccount; }
    public String getName()    { return sName; }

    public boolean isLoggedIn() { return sToken != null; }
}
