package com.lazermann.httpserver.model;


import java.io.Serializable;

public class UserResult implements Serializable
{
    private String userId;
    private String levelId;
    private long result;


    public UserResult(String userId, String levelId, long result) {
        this.userId = userId;
        this.levelId = levelId;
        this.result = result;
    }
}
