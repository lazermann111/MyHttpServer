package com.lazermann.httpserver.model;


import java.util.Comparator;

public enum ResultsComparator implements Comparator<UserResult> {

    INSTANCE;

    @Override
    public int compare(UserResult result, UserResult result2)
    {
        return result.getResult() < result2.getResult() ? 1 : -1;
    }

}
