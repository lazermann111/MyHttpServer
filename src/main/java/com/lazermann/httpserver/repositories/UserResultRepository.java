package com.lazermann.httpserver.repositories;


import com.lazermann.httpserver.model.UserResult;

import java.util.Collection;

public interface UserResultRepository
{
     Collection<UserResult> getTopUserInfo(String userId);
     Collection<UserResult> getTopLevelInfo(String levelId);
     void saveUserResult(UserResult result);
}
