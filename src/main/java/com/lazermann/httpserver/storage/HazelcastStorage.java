package com.lazermann.httpserver.storage;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.lazermann.httpserver.model.TopResults;
import com.lazermann.httpserver.model.UserResult;

import java.util.HashMap;
import java.util.Map;

import static com.lazermann.httpserver.Constants.LEVEL_MAP;
import static com.lazermann.httpserver.Constants.USERS_MAP;

public class HazelcastStorage
{
    private static HazelcastInstance instance;

    public static HazelcastInstance getInstance()
    {
        if(instance == null)
        {
            init();
        }

        return instance;
    }

    public static void init()
    {
        Config cfg = new Config();
        instance  = Hazelcast.newHazelcastInstance(cfg);
    }

    public static void addTestData()
    {
        Map<String,TopResults> usersMap = HazelcastStorage.getInstance().getReplicatedMap(USERS_MAP);



        Map<String,TopResults> levelsMap = HazelcastStorage.getInstance().getReplicatedMap(LEVEL_MAP);

        Map<String,UserResult> levelsMap3 = HazelcastStorage.getInstance().getMap("SSDSD");

        Map<String,TopResults> levelsMap2= new HashMap<>();

        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                UserResult result =  new UserResult(i+"",j+"", i*j);
                usersMap.computeIfAbsent(result.getUserId(), k -> new TopResults());
                usersMap.get(result.getUserId()).addNewResult(result);

                levelsMap.computeIfAbsent(result.getLevelId(), k -> new TopResults());
                levelsMap.get(result.getLevelId()).addNewResult(result);

                levelsMap2.putIfAbsent(result.getLevelId(), new TopResults());
                levelsMap2.get(result.getLevelId()).addNewResult(result);


                levelsMap3.put(result.getLevelId(), result);
            }
        }
    }
}
