package com.lazermann.httpserver.model;


public class UserResult
{
    private long userId;
    private long levelId;
    private long result;


    public UserResult(long userId, long levelId, long result) {
        this.userId = userId;
        this.levelId = levelId;
        this.result = result;
    }
}
