package io.xiaoyi311.seaper.model;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 临时 Session
 */
public class TempSession implements HttpSession {
    Map<String, Object> data = new HashMap<>();

    public static TempSession temp = new TempSession();

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {

    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public Object getAttribute(String s) {
        return data.get(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {
        data.put(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        data.remove(s);
    }

    @Override
    public void invalidate() {
        data.clear();
    }

    @Override
    public boolean isNew() {
        return data.isEmpty();
    }
}
