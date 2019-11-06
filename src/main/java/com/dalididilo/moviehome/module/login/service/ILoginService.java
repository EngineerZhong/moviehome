package com.dalididilo.moviehome.module.login.service;

import com.dalididilo.moviehome.module.login.bean.User;

public interface ILoginService {

    User findUserByName(String userName);
}
