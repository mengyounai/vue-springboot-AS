package com.example.wxbf.aliyun;

import com.example.wxbf.dao.DistanceRepository;
import com.example.wxbf.service.DistanceService;
import com.example.wxbf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Test {

    @Autowired
    DistanceRepository distanceRepository;

    @Autowired
    DistanceService distanceService;

    @Autowired
    UserService userService;

//    @org.junit.jupiter.api.Test
//    void InputProduct() throws Exception {
//        sample.inputproduct("LTAI5tGD9EP9YMEgrGLRWkH6","R0SClrvQTlmzm299TRgVSyj6LkaQ0w","demo101",0);
//        assertNotNull("n");
//    }

    @org.junit.jupiter.api.Test
    void InputProduct() throws Exception {
        System.out.println(userService.checkUser("s","123456"));
        assertNotNull("n");
    }


}
