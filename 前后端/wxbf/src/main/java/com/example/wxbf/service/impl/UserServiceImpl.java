package com.example.wxbf.service.impl;

import com.aliyun.iot20180120.models.QueryDeviceRequest;
import com.aliyun.iot20180120.models.QueryDeviceResponse;
import com.aliyun.teaopenapi.models.Config;
import com.example.wxbf.dao.DeviceRepository;
import com.example.wxbf.dao.UserRepository;
import com.example.wxbf.po.Device;
import com.example.wxbf.po.User;
import com.example.wxbf.service.DeviceService;
import com.example.wxbf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static String accessKeyId = "LTAI5tGD9EP9YMEgrGLRWkH6";
    private static String accessKeySecret = "R0SClrvQTlmzm299TRgVSyj6LkaQ0w";

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user= userRepository.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public User saveUser(String username, String password) {
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(new Date());
        return userRepository.save(user);
    }
}
