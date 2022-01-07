// This file is auto-generated, don't edit it. Thanks.
package com.example.wxbf.aliyun;

import com.aliyun.iot20180120.models.*;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.*;

public class SampleTest {

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
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

    public static void main(String[] args_) throws Exception {
        java.util.List<String> args = java.util.Arrays.asList(args_);
        com.aliyun.iot20180120.Client client = SampleTest.createClient("LTAI5tGD9EP9YMEgrGLRWkH6", "R0SClrvQTlmzm299TRgVSyj6LkaQ0w");
        QueryDeviceRequest queryDeviceRequest = new QueryDeviceRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey("grd9LOU2N75")
                .setPageSize(5)
                .setCurrentPage(1);
        // 复制代码运行请自行打印 API 的返回值
        client.queryDevice(queryDeviceRequest);
        QueryDeviceResponse res = client.queryDevice(queryDeviceRequest);
        String s=res.body.data.deviceInfo.get(0).deviceName;
        System.out.println(s);

    }
}