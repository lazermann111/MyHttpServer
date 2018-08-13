package com.lazermann.httpserver.repositories;


import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.lazermann.httpserver.Constants.RESULTS_LIST;

public class UserResultRepositoryImpl implements UserResultRepository
{


    // todo add caching!!
    //todo switch to map??


    @Override
    public List<UserResult> getUserInfo(String userId) {
        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);

        return res.stream().filter(a -> a.getUserId().equals(userId)).sorted(new Comparator<UserResult>() {
            @Override
            public int compare(UserResult userResult, UserResult t1) {
                return userResult.getResult() >= t1.getResult() ? 1 : -1;
            }
        }).limit(20).collect(Collectors.toList());
    }

    @Override
    public List<UserResult> getLevelInfo(String levelId)
    {
        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);

        return res.stream().filter(a -> a.getLevelId().equals(levelId)).sorted(new Comparator<UserResult>() {
            @Override
            public int compare(UserResult userResult, UserResult t1) {
                return userResult.getResult() >= t1.getResult() ? 1 : -1;
            }
        }).limit(20).collect(Collectors.toList());
    }

    @Override
    public void saveUserResult(UserResult result)
    {

    }
}
