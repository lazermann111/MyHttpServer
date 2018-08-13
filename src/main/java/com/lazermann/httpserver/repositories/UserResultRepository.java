package com.lazermann.httpserver.repositories;


import com.lazermann.httpserver.model.UserResult;

import java.util.List;

public interface UserResultRepository
{
     List<UserResult> getUserInfo(String userId);
     List<UserResult> getLevelInfo(String levelId);
     void saveUserResult(UserResult result);
}
