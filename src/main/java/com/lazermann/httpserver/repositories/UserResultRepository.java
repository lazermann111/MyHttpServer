package com.lazermann.httpserver.repositories;


import com.lazermann.httpserver.model.UserResult;

import java.util.List;

public interface UserResultRepository
{
     List<UserResult> getTopUserInfo(String userId);
     List<UserResult> getTopLevelInfo(String levelId);
     void saveUserResult(UserResult result);
}
