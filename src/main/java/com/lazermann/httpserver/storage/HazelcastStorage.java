package com.lazermann.httpserver.storage;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.lazermann.httpserver.model.UserResult;

import java.util.List;

import static com.lazermann.httpserver.Constants.RESULTS_LIST;

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
        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 30; j++)
            {
                res.add(new UserResult(i+"",j+"", i*j));
            }
        }
    }
}
