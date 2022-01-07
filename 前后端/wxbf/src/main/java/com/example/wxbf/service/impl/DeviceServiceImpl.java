package com.example.wxbf.service.impl;

import com.aliyun.iot20180120.models.QueryDeviceRequest;
import com.aliyun.iot20180120.models.QueryDeviceResponse;
import com.aliyun.iot20180120.models.QueryProductListResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.wxbf.aliyun.SampleTest;
import com.example.wxbf.dao.DeviceRepository;
import com.example.wxbf.po.Device;
import com.example.wxbf.po.PhoneData;
import com.example.wxbf.po.Product;
import com.example.wxbf.service.DeviceService;
import com.example.wxbf.service.PhoneDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static String accessKeyId="LTAI5tGD9EP9YMEgrGLRWkH6";
    private static String accessKeySecret="R0SClrvQTlmzm299TRgVSyj6LkaQ0w";

    @Autowired
    private DeviceRepository deviceRepository;

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
    public List<Device> QueryDevice(String ProductKey, Integer PageSize, Integer CurrentPage) throws Exception {
        com.aliyun.iot20180120.Client client = DeviceServiceImpl.createClient(accessKeyId, accessKeySecret);
        QueryDeviceRequest queryDeviceRequest = new QueryDeviceRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey(ProductKey)
                .setPageSize(PageSize)
                .setCurrentPage(CurrentPage);
        // 复制代码运行请自行打印 API 的返回值
        client.queryDevice(queryDeviceRequest);
        QueryDeviceResponse resp = client.queryDevice(queryDeviceRequest);
        int length=resp.body.data.deviceInfo.size();
        List<Device> devices=new ArrayList<>();
        for (int i=0;i<length;i++){
            Device device = new Device();
            String DeviceName=resp.body.data.deviceInfo.get(i).deviceName;
            String productKey=resp.body.data.deviceInfo.get(i).productKey;
            device.setDeviceName(DeviceName);
            device.setProductKey(productKey);
            devices.add(device);
        }
        return devices;
    }
}
