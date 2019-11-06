package com.dalididilo.moviehome.module.login.dao;


import com.dalididilo.moviehome.module.login.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {

    User findUserByName(@Param(value = "userName") String userName);
}
