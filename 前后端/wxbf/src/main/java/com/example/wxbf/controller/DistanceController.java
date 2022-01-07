package com.example.wxbf.controller;

import com.example.wxbf.po.Distance;
import com.example.wxbf.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping("xyz")
    public void xyz() throws Exception {
        distanceService.saveList();
    }

    //仅获取X轴数据
    @GetMapping("getDistanceX")
    public String[] getData() throws Exception {
        List<Distance> all = distanceService.distance();
        String[] xList=new String[all.size()];
        for (int i=0;i<all.size();i++) {
            xList[i]=all.get(i).getX();
        }
        for (int i=0;i<all.size();i++) {

        }

        return xList;
    }

    //仅获取X轴数据
    @GetMapping("getDistanceY")
    public String[] getData2() throws Exception {
        List<Distance> all = distanceService.distance();
        String[] yList=new String[all.size()];
        for (int i=0;i<all.size();i++) {
            yList[i]=all.get(i).getY();
        }
        return yList;
    }
    //仅获取Z轴数据
    @GetMapping("getDistanceZ")
    public String[] getData3() throws Exception {
        List<Distance> all = distanceService.distance();
        String[] ZList=new String[all.size()];
        for (int i=0;i<all.size();i++) {
            ZList[i]=all.get(i).getZ();
        }
        return ZList;
    }


}
