package com.example.wxbf.controller;

import com.example.wxbf.dao.ProductRepository;
import com.example.wxbf.po.Device;
import com.example.wxbf.po.Product;
import com.example.wxbf.service.AliYunService;
import com.example.wxbf.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
@ResponseBody
public class AliYunController {

    @Autowired
    AliYunService aliYunService;

    @Autowired
    DeviceService deviceService;

    //新建产品
    @RequestMapping("/inputproduct")
    @ResponseBody
    public boolean inputproduct(@RequestBody Map<String, Object> info) throws Exception {
//        ProductName = "APITEST";
        int NodeType = 0;
        String productName = info.get("productName").toString();
        return aliYunService.addProduct(productName, NodeType);
    }

    //删除产品
    @RequestMapping("/deleteproduct")
    @ResponseBody
    public String deleteproduct(@RequestBody Map<String, Object> info) throws Exception {
//         String productKey="grd9JB1WqzH";
        String productKey = info.get("productKey").toString();
        System.out.println(productKey);
        aliYunService.deleteProduct2(productKey);
        return "删除产品成功！";
    }

    //新增设备
    @GetMapping("/inputdevice")
    public String inputdevice(String ProductKey, String DeviceName) throws Exception {
        ProductKey = "grd9aRt5R4q";
        DeviceName = "AddDeviceNameTest";
        aliYunService.addDevice(ProductKey, DeviceName);
        return "新建设备成功！";
    }

    //修改产品
    @RequestMapping("/UpdateProduct")
    @ResponseBody
    public String UpdateProduct(@RequestBody Map<String, Object> info) throws Exception {
        String Description = "UpdateTest";
//        System.out.println(info);
        String productKey = info.get("productKey").toString();
        System.out.println(productKey);
        String ProductName = info.get("productName").toString();
        System.out.println(ProductName);
        aliYunService.UpdateProduct(Description, productKey, ProductName);
        return "修改产品成功！";
    }


    //分页查询所有产品
    @GetMapping("/QueryProductList")
    public List<Product> QueryProductList(Integer PageSize, Integer CurrentPage) throws Exception {
        PageSize = 50;
        CurrentPage = 1;
        List<Product> productList = aliYunService.QueryProductList(PageSize, CurrentPage);
//        aliYunService.QueryProductList(PageSize,CurrentPage);
        return productList;

    }

    //查询指定产品下的所有设备列表
    @RequestMapping("/QueryDevice")
    @ResponseBody
    public List<Device> QueryDevice(@RequestBody Map<String, Object> info) throws Exception {
//        ProductKey = "grd9LOU2N75";
        Integer PageSize = 10;
        Integer CurrentPage = 1;
        String productKey = info.get("productKey").toString();
//        System.out.println(productKey);
        return deviceService.QueryDevice(productKey, PageSize, CurrentPage);
//        return null;
    }


    //纯测试案例
    @GetMapping("/test")
//    @ResponseBody
    public String QueryProductList(String data) throws Exception {
//        String productKey="grd9JB1WqzH";
////        String productKey = info.get("productKey").toString();
//        System.out.println(productKey);
//        aliYunService.deleteProduct(productKey);
        return "删除产品成功！";
    }

    @GetMapping("/getData")
    public List<Product> getData() {
//        List<Product> all = productRepository.findAll();
        return null;
    }

}
