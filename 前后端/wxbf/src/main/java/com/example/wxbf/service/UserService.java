package com.example.wxbf.service;


import com.example.wxbf.po.User;

public interface UserService {

    //检查账号密码
    User checkUser(String username, String password);

    //注册用户
    User saveUser(String username, String password);
}
