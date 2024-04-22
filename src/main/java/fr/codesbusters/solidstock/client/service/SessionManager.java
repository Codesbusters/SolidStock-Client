package fr.codesbusters.solidstock.client.service;

import java.util.HashMap;

public class SessionManager {
    private static SessionManager instance;
    private HashMap<String, Object> sessionData;

    private SessionManager() {
        sessionData = new HashMap<>();
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setAttribute(String key, Object value) {
        sessionData.put(key, value);
    }

    public Object getAttribute(String key) {
        return sessionData.get(key);
    }

    public void removeAttribute(String key) {
        sessionData.remove(key);
    }
}