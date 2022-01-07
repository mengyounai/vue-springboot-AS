package com.example.wxbf.service;

import com.example.wxbf.po.Device;

import java.util.List;

public interface DeviceService {

    //查询指定产品下的所有设备列表
    List<Device> QueryDevice(String ProductKey,Integer PageSize,Integer CurrentPage) throws Exception;
}
