package com.example.wxbf.service;

import com.example.wxbf.po.Product;

import java.util.List;

public interface AliYunService {

    //删除产品
    void deleteProduct2(String ProductKey) throws Exception;

    //新建产品
    boolean addProduct(String ProductName,Integer Node) throws Exception;

    //新建设备
    void addDevice(String ProductKey,String DeviceName) throws Exception;

    //修改指定产品的信息，ProductKey要更新的产品的ProductKey，ProductName修改后的产品名称
    void UpdateProduct(String Description,String ProductKey,String ProductName) throws Exception;

    //查看所有产品列表
    List<Product> QueryProductList(Integer PageSize, Integer CurrentPage) throws Exception;
}
