package com.lazermann.httpserver.model;


import java.io.Serializable;

public class UserResult implements Serializable, Comparable<UserResult>
{
    private String userId;
    private String levelId;
    private long result;


    public UserResult(String userId, String levelId, long result) {
        this.userId = userId;
        this.levelId = levelId;
        this.result = result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public long getResult() {
        return result;
    }

    public void setResult(long result) {
        this.result = result;
    }

    @Override
    public int compareTo(UserResult r) {
        return this.getResult() < r.getResult() ? 1 : -1;
    }
}
