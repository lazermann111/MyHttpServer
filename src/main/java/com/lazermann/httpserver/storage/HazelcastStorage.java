package com.lazermann.httpserver.storage;


import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastStorage
{
    private static HazelcastInstance instance;

    public static HazelcastInstance getInstance()
    {
        if(instance == null)
        {
            Config cfg = new Config();
            instance  = Hazelcast.newHazelcastInstance(cfg);
        }

        return instance;
    }
}
