package com.example.wxbf.service;

import com.example.wxbf.po.Distance;

import java.util.List;

public interface DistanceService {

    //新增单条数据
    void save();

    //新增多条数据
    void saveList() throws Exception;

    //获取前十条数据
    List<Distance> distance() throws Exception;

    //查询全部数据
    List<Distance> findAll();
}
