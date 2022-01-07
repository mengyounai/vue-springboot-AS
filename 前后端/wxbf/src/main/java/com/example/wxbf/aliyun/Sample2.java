package com.example.wxbf.aliyun;

import com.aliyun.iot20180120.models.QueryDevicePropertyStatusRequest;
import com.aliyun.iot20180120.models.QueryDevicePropertyStatusResponse;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;

//当前数据
public class Sample2 {          //获取实时数据（一个）
    public String[] qiege3(String text){
        int l=0;
        int n=0;
        // FileTest file=new FileTest();
        String[] strarray=text.split(",");
        boolean a=false;
        boolean b=false;

//        for (int i = 0; i < strarray.length; i++) {
//            System.out.println(strarray[i]);
//        }

        //获取有多少数据
        for (int i = 0; i < strarray.length; i++) {
            if (strarray[i].contains("Value")){
                n++;
            }
        }
        System.out.println(n);
        String[] strings=new String[n];

        for (int i = 0; i < strarray.length; i++) {
            if (strarray[i].contains("Identifier")){
                a=true;
            }
            if (strarray[i].contains("Value")){
                a=true;
            }
            if (strarray[i].contains("DataType")){
                a=true;
            }
            if (strarray[i].contains("Time")){
                a=true;
            }
            if (strarray[i].contains("Unit")){
                a=true;
            }
            if (strarray[i].contains("Name")){
                b=true;
            }
            if (a){
//                int index=strarray[i].lastIndexOf(":");
                strings[l]+=strarray[i].substring(1,strarray[i].length());
                a=false;
            }
            if (b){
                strings[l]+=""+strarray[i];
                b=false;
                l++;
            }
        }
        String[] result=new String[strings.length];
        String[] result2=new String[strings.length];
        for (int i = 0; i < strings.length; i++) {
            result[i]=strings[i].replace("null", "")
                    .replace("List\":{\"PropertyInfo\":[","")
            .replace("Data\":{\"List\":{\"PropertyStatusInfo\":[{","");

//            System.out.println(result);
        }
        for (int i = 0; i < result.length; i++) {
            result2[i]=result[i];
        }
        return result2;
    }
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
        com.aliyun.iot20180120.Client client = Sample.createClient("LTAI5tGD9EP9YMEgrGLRWkH6", "R0SClrvQTlmzm299TRgVSyj6LkaQ0w");
        QueryDevicePropertyStatusRequest queryDevicePropertyStatusRequest = new QueryDevicePropertyStatusRequest()
                .setProductKey("grd9aRt5R4q")
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setDeviceName("test-01");
        QueryDevicePropertyStatusResponse resp = client.queryDevicePropertyStatus(queryDevicePropertyStatusRequest);
        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        String s= Common.toJSONString(TeaModel.buildMap(resp));
        Sample2 sample2=new Sample2();
       String[] strings= sample2.qiege3(s);
        for (String string : strings) {
            System.out.println(string);
        }

    }
}

