package com.example.wxbf.controller;

import com.example.wxbf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
@ResponseBody
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody Map<String, Object> info) throws Exception {
        System.out.println(info);
        String username= info.get("username").toString();
        String password= info.get("password").toString();
        if (userService.checkUser(username,password)!=null){
            return true;
        }else {
            return false;
        }
    }
    @RequestMapping("/reg")
    @ResponseBody
    public boolean reg(@RequestBody Map<String, Object> info) throws Exception {
        System.out.println(info);
        String username= info.get("username").toString();
        String password= info.get("password").toString();
        if (userService.saveUser(username,password)!=null){
            return true;
        }else {
            return false;
        }
    }
    @GetMapping("/test2")
    @ResponseBody
    public String QueryProductList(String data) {
        System.out.println(data);
        return "测试案例sadsad";
    }
}
