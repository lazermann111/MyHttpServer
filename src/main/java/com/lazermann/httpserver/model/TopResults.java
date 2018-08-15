package com.lazermann.httpserver.model;


import java.io.Serializable;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import static com.lazermann.httpserver.Constants.MAX_RESULTS_SIZE;

/*
 * Class that is responsible for storing top users result
 */
public class TopResults implements Serializable
{

    private SortedSet<UserResult> results = new ConcurrentSkipListSet<>(ResultsComparator.INSTANCE);

    public void addNewResult(UserResult result)
    {
        results.add(result);
        if(results.size() > MAX_RESULTS_SIZE)
        {
            results = new ConcurrentSkipListSet<>(results.stream().limit(20).collect(Collectors.toList()));
        }
    }

    public SortedSet<UserResult> getResults()
    {
        return results;
    }
}
