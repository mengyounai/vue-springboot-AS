package com.example.wxbf.service.impl;

import com.aliyun.iot20180120.models.QueryDevicePropertyDataRequest;
import com.aliyun.iot20180120.models.QueryDevicePropertyDataResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.wxbf.VO.DistanceVO;
import com.example.wxbf.dao.DeviceRepository;
import com.example.wxbf.dao.DistanceRepository;
import com.example.wxbf.po.Distance;
import com.example.wxbf.service.DistanceService;
import com.example.wxbf.util.DistanceUtil;
import com.example.wxbf.util.SortClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DistanceServiceImpl implements DistanceService {

    @Autowired
    private DistanceRepository distanceRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    private static String accessKeyId="LTAI5tGD9EP9YMEgrGLRWkH6";
    private static String accessKeySecret="R0SClrvQTlmzm299TRgVSyj6LkaQ0w";


    public static com.aliyun.iot20180120.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "iot.cn-shanghai.aliyuncs.com";
        return new com.aliyun.iot20180120.Client(config);
    }
    @Override
    public void save() {

    }

    @Override
    public void saveList() throws Exception {
        java.util.Date dt = new Date();
        System.out.println(dt.toString());   //java.util.Date的含义
        long lSysTime1 = dt.getTime() / 1000;   //得到秒数，Date类型的getTime()返回毫秒数
        System.out.println("秒数为："+lSysTime1);
        //1641383406418L
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        QueryDevicePropertyDataRequest queryDevicePropertyDataRequest = new QueryDevicePropertyDataRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey("grd9aRt5R4q")
                .setDeviceName("test-01")
                .setStartTime(1641376206418L)
                .setIdentifier("jl")
                .setAsc(0)
                .setEndTime(lSysTime1*1000)
                .setPageSize(10);
        client.queryDevicePropertyData(queryDevicePropertyDataRequest);
        QueryDevicePropertyDataResponse resp = client.queryDevicePropertyData(queryDevicePropertyDataRequest);
        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        List<Distance> distances=new ArrayList<>();
//        String productKey = resp.body.data.list.productInfo.get(1).productKey;
//        System.out.println(productKey);
        int length=resp.body.data.list.propertyInfo.size();
        DistanceUtil distanceUtil=new DistanceUtil();
        DistanceVO distanceVO=new DistanceVO();
//        Device device=new Device();
//        deviceRepository.findByDeviceName("test-01").getProductKey();
        for (int i=0;i<length;i++){
            Distance distance=new Distance();
            String time = resp.body.data.list.propertyInfo.get(i).time;
            String Value = resp.body.data.list.propertyInfo.get(i).value;
            distanceVO=distanceUtil.XYZ(Value);
            distance.setX(distanceVO.getX());
            distance.setY(distanceVO.getY());
            distance.setZ(distanceVO.getZ());
            distance.setCreateTime(time);
            System.out.println(distance);
            distanceRepository.save(distance);
        }

    }

    @Override
    public List<Distance> distance() throws Exception {
        java.util.Date dt = new Date();
//        System.out.println(dt.toString());   //java.util.Date的含义
        long lSysTime1 = dt.getTime() / 1000;   //得到秒数，Date类型的getTime()返回毫秒数
//        System.out.println("秒数为："+lSysTime1);
        //1641383406418L
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        QueryDevicePropertyDataRequest queryDevicePropertyDataRequest = new QueryDevicePropertyDataRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey("grd9aRt5R4q")
                .setDeviceName("test-01")
                .setStartTime(1641376206418L)
                .setIdentifier("cc")
                .setAsc(0)
                .setEndTime(lSysTime1*1000)
                .setPageSize(10);
        client.queryDevicePropertyData(queryDevicePropertyDataRequest);
        QueryDevicePropertyDataResponse resp = client.queryDevicePropertyData(queryDevicePropertyDataRequest);
//        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        List<Distance> distances=new ArrayList<>();
//        String productKey = resp.body.data.list.productInfo.get(1).productKey;
//        System.out.println(productKey);
        int length=resp.body.data.list.propertyInfo.size();
        DistanceUtil distanceUtil=new DistanceUtil();
        DistanceVO distanceVO=new DistanceVO();
//        Device device=new Device();
//        deviceRepository.findByDeviceName("test-01").getProductKey();
        for (int i=0;i<length;i++){
            Distance distance=new Distance();
            String time = resp.body.data.list.propertyInfo.get(i).time;
            String Value = resp.body.data.list.propertyInfo.get(i).value;
            distanceVO=distanceUtil.XYZ(Value);
            distance.setX(distanceVO.getX());
            distance.setY(distanceVO.getY());
            distance.setZ(distanceVO.getZ());
            distance.setCreateTime(time);
            distances.add(distance);
        }
//        for (Distance distance : distances) {
//            System.out.println(distance);
//        }

//        SortClass sort = new SortClass();
//        Collections.sort(distances,sort);
//        System.out.println("数组长度为:"+distances.size());
//        for(int i=0;i<distances.size();i++){
//            Distance temp = (Distance) distances.get(i);
//        }

//        for (Distance distance : distances) {
//            System.out.println(distance);
//        }
        return distances;
    }

    @Override
    public List<Distance> findAll() {
        return distanceRepository.findAll();
    }
}
