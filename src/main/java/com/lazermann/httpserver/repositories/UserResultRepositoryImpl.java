package com.lazermann.httpserver.repositories;


import com.lazermann.httpserver.model.TopResults;
import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;

import java.util.Collection;
import java.util.Map;

import static com.lazermann.httpserver.Constants.LEVEL_MAP;
import static com.lazermann.httpserver.Constants.USERS_MAP;

public class UserResultRepositoryImpl implements UserResultRepository
{
    @Override
    public Collection<UserResult> getTopUserInfo(String userId) {

        Map<String,TopResults> res = HazelcastStorage.getInstance().getReplicatedMap(USERS_MAP);
        return res.get(userId).getResults();
    }

    @Override
    public Collection<UserResult> getTopLevelInfo(String levelId)
    {
        Map<String,TopResults> res = HazelcastStorage.getInstance().getReplicatedMap(LEVEL_MAP);
        return res.get(levelId).getResults();
    }

    @Override
    public void saveUserResult(UserResult result)
    {
        Map<String,TopResults> usersMap = HazelcastStorage.getInstance().getReplicatedMap(USERS_MAP);
        Map<String,TopResults> levelsMap = HazelcastStorage.getInstance().getReplicatedMap(LEVEL_MAP);

        usersMap.computeIfAbsent(result.getUserId(), k -> new TopResults());
        usersMap.get(result.getUserId()).addNewResult(result);

        levelsMap.computeIfAbsent(result.getLevelId(), k -> new TopResults());
        levelsMap.get(result.getLevelId()).addNewResult(result);
    }
}
