package com.aliyun.alink.devicesdk.demo;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.alink.devicesdk.app.AppLog;
import com.aliyun.alink.devicesdk.app.DemoApplication;
import com.aliyun.alink.devicesdk.app.DeviceInfoData;
import com.aliyun.alink.devicesdk.manager.DASHelper;
import com.aliyun.alink.devicesdk.manager.IDemoCallback;
import com.aliyun.alink.devicesdk.manager.InitManager;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.AMessage;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.base.ConnectState;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectNotifyListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.tools.AError;
import com.aliyun.alink.linksdk.tools.log.IDGenerater;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicInteger;


/*
 * Copyright (c) 2014-2016 Alibaba Group. All rights reserved.
 * License-Identifier: Apache-2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

public class DemoActivity extends BaseActivity implements View.OnClickListener, SensorListener {
    private static final String TAG = "DemoActivity";
    int id = 0;
    SensorManager sm = null;
    TextView View1 = null;
    TextView View2 = null;
    TextView View3 = null;
    TextView View4 = null;
    TextView View5 = null;
    TextView View6 = null;
    TextView View7 = null;
    TextView View8 = null;
    TextView View9 = null;
    TextView View10 = null;
    TextView View11 = null;
    TextView View12 = null;
    private TextView errorTV = null;
    private AtomicInteger testDeviceIndex = new AtomicInteger(0);
    boolean flag = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppLog.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        errorTV = findViewById(R.id.id_error_info);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        View1 = (TextView) findViewById(R.id.edt1);
        View2 = (TextView) findViewById(R.id.edt2);
        View3 = (TextView) findViewById(R.id.edt3);
        View4 = (TextView) findViewById(R.id.edt4);
        View5 = (TextView) findViewById(R.id.edt5);
        View6 = (TextView) findViewById(R.id.edt6);
        View7 = (TextView) findViewById(R.id.edt7);
        View8 = (TextView) findViewById(R.id.edt8);
        View9 = (TextView) findViewById(R.id.edt9);
        View10 = (TextView) findViewById(R.id.edt10);
        View11 = (TextView) findViewById(R.id.edt11);
        setListener();


        subMsg();
        LinkKit.getInstance().registerOnPushListener(notifyListener);
        Log.e("----------------------","?????????");
        flag = true;
    }
    /**
     * ???????????????????????? MQTT ????????????????????????????????????
     */
    private static IConnectNotifyListener notifyListener = new IConnectNotifyListener() {
        /**
         * onNotify ????????????????????? shouldHandle ???????????????????????????topic
         * @param connectId ??????????????????????????????????????? connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param topic ?????????topic
         * @param aMessage ?????????????????????
         */
        @Override
        public void onNotify(String connectId, String topic, AMessage aMessage) {
            String data = new String((byte[]) aMessage.data);
            // ???????????????????????????  data = {"method":"thing.service.test_service","id":"123374967","params":{"vv":60},"version":"1.0.0"}
            Log.d("*****************",data);
        }

        /**
         * @param connectId ??????????????????????????????????????? connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param topic ??????topic
         * @return ?????????????????????topic????????????true??????????????????onNotify????????????false???onNotify??????????????????topic?????????????????????????????????true???
         */
        @Override
        public boolean shouldHandle(String connectId, String topic) {
            return true;
        }

        /**
         * @param connectId ??????????????????????????????????????? connectId == ConnectSDK.getInstance().getPersistentConnectId()
         * @param connectState {@link ConnectState}
         *     CONNECTED, ????????????
         *     DISCONNECTED, ?????????
         *     CONNECTING, ?????????
         *     CONNECTFAIL; ????????????
         */
        @Override
        public void onConnectStateChange(String connectId, ConnectState connectState) {
            Log.d(TAG, "onConnectStateChange() called with: connectId = [" + connectId + "], connectState = [" + connectState + "]");
        }
    };
    void sendData(String str1,String str2,String str3,String str4){
            // ??????
            MqttPublishRequest request = new MqttPublishRequest();
    // ???????????????????????????
            request.isRPC = false;
    // ??????topic??????????????????Topic?????????????????????????????????
            request.topic = "/sys/grd9aRt5R4q/test-01/thing/event/property/post";
    // ?????? qos
            request.qos = 0;

    // data ??????????????????????????? json String?????????id???????????????????????????
    //?????? ???????????? {"id":"160865432","method":"thing.event.property.post","params":{"LightSwitch":1},"version":"1.0"}
            request.payloadObj = "{\"id\":\""+id+++"\",\"property\":\""+str4+"\",\"params\":{\""+str4+"\":\"X"+str1+"Y"+str2+"Z"+str3+"\"},\"version\":\"1.0\"}";

            LinkKit.getInstance().publish(request, new IConnectSendListener() {
                @Override
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    Log.e("aaaaa","???????????????");
                }
                @Override
                public void onFailure(ARequest aRequest, AError aError) {
                    // ????????????
                    Log.e("aaaaa","???????????????");

                }
            });
    }
    //????????????
    void subMsg(){
        // ??????
        MqttPublishRequest request = new MqttPublishRequest();
// ???????????????????????????
        request.isRPC = false;
// ??????topic??????????????????Topic?????????????????????????????????
        request.topic = "/sys/grd9aRt5R4q/test-01/thing/event/property/post_reply";
// ?????? qos
        request.qos = 0;
// data ??????????????????????????? json String?????????id???????????????????????????
//?????? ???????????? {"id":"160865432","method":"thing.event.property.post","params":{"LightSwitch":1},"version":"1.0"}
//        request.payloadObj = data;

        LinkKit.getInstance().publish(request, new IConnectSendListener() {
            @Override
            public void onResponse(ARequest aRequest, AResponse aResponse) {
                Log.e("eeeeee","????????????");
                // ????????????
            }
            @Override
            public void onFailure(ARequest aRequest, AError aError) {
                // ????????????
                Log.e("eeeeee","????????????");
            }
        });

    }
    public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {
            String str = "X???" + values[0] + "???Y???" + values[1] + "???Z???" + values[2];
            switch (sensor){
                case Sensor.TYPE_ACCELEROMETER:
                    View1.setText("????????????" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","jsd");
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    View2.setText("?????????" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","cc");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    View8.setText("?????????" + str);
                    sendData(values[0]+"",values[1]+"",values[2]+"","jl");
                    break;
//                case Sensor.TYPE_LIGHT:
//                    View5.setText("?????????" + str);
//                    //sendData(values[0]+"",1+"",2+"","gz");
//                    break;
//                case Sensor.TYPE_PRESSURE:
//                    View6.setText("?????????" + str);
//                    break;
            }
        }
    }
    public void onAccuracyChanged(int sensor, int accuracy) {
        Log.d(TAG,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }


    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }
    private void setListener() {
        try {
            LinearLayout demoLayout = findViewById(R.id.id_demo_layout);
            int size = demoLayout.getChildCount();
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    break;
                }
                View child = demoLayout.getChildAt(i);
                child.setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLog.w(TAG, "setListener exception " + e);
        }
    }

    public void startOTATest(View view) {
        if (!checkReady()) {
            return;
        }

        Intent intent = new Intent(this, OTAActivity.class);
        startActivity(intent);
    }


    public void startBreezeOTATest(View view) {
        if (!checkReady()) {
            return;
        }

//        Intent intent = new Intent(this, BreezeOtaActivity.class);
//        startActivity(intent);
    }

    public void startLPTest(View view) {
        if (!checkReady()) {
            return;
        }
        if (LinkKit.getInstance().getDeviceThing() == null) {
            showToast("????????????????????????");
            return;
        }
        Intent intent = new Intent(this, TSLActivity.class);
        startActivity(intent);
    }

    public void startLabelTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, LabelActivity.class);
        startActivity(intent);
    }

    public void startCOTATest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, COTAActivity.class);
        startActivity(intent);
    }

    public void startShadowTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, ShadowActivity.class);
        startActivity(intent);
    }

    public void startGatewayTest(View view) {
        if (!checkReady()) {
            return;
        }
        if (LinkKit.getInstance().getGateway() == null) {
            showToast("?????????????????????");
            return;
        }
        Intent intent = new Intent(this, GatewayActivity.class);
        startActivity(intent);
    }

    private boolean checkReady() {
        if (DemoApplication.userDevInfoError) {
            showToast("?????????????????????res/raw/deviceinfo????????????");
            return false;
        }
        if (!DemoApplication.isInitDone) {
            showToast("???????????????????????????????????????");
            return false;
        }
        errorTV.setVisibility(View.GONE);
        return true;
    }

    public void startH2FileTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, H2FileManagerActivity.class);
        startActivity(intent);
    }

    public void startLogPush(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, LogPushActivity.class);
        startActivity(intent);
    }

    public void startMqttTest(View view) {
        if (!checkReady()) {
            return;
        }
        Intent intent = new Intent(this, MqttActivity.class);
        startActivity(intent);
    }

    private void startResetTest(View v) {
        Intent intent = new Intent(this, ResetActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_start_LP:
                startLPTest(v);
                break;
            case R.id.id_start_label:
                startLabelTest(v);
                break;
            case R.id.id_start_cota:
                startCOTATest(v);
                break;
            case R.id.id_start_shadow:
                startShadowTest(v);
                break;
            case R.id.id_start_gateway:
                startGatewayTest(v);
                break;
            case R.id.id_start_ota:
                startOTATest(v);
                break;
            case R.id.id_start_breeze_ota:
                startBreezeOTATest(v);
                break;
            case R.id.id_start_h2_file:
                startH2FileTest(v);
                break;
            case R.id.id_test_init:
                connect();
                break;
            case R.id.id_test_deinit:
                deinit();
                break;
            case R.id.id_mqtt_test:
//                testJniLeakWithCoAP();
                startMqttTest(v);
                break;
            case R.id.id_test_reset:
                startResetTest(v);
                break;
            case R.id.id_log_push:
                startLogPush(v);
                break;
        }
    }

    private static ArrayList<DeviceInfoData> getTestDataList() {
        ArrayList<DeviceInfoData> infoDataArrayList = new ArrayList<DeviceInfoData>();

        DeviceInfoData test6 = new DeviceInfoData();
        test6.productKey = DemoApplication.productKey;
        test6.deviceName = DemoApplication.deviceName;
        test6.deviceSecret = DemoApplication.deviceSecret;
        infoDataArrayList.add(test6);
        return infoDataArrayList;
    }

    /**
     * ?????????
     * ???????????????????????????????????????
     */
    private void connect() {
        AppLog.d(TAG, "connect() called");
        // SDK?????????
        DeviceInfoData deviceInfoData = getTestDataList().get(testDeviceIndex.getAndIncrement() % getTestDataList().size());
        DemoApplication.productKey = deviceInfoData.productKey;
        DemoApplication.deviceName = deviceInfoData.deviceName;
        DemoApplication.deviceSecret = deviceInfoData.deviceSecret;
        new Thread(new Runnable() {
            @Override
            public void run() {
                InitManager.init(DemoActivity.this, DemoApplication.productKey, DemoApplication.deviceName,
                        DemoApplication.deviceSecret, DemoApplication.productSecret, new IDemoCallback() {

                            @Override
                            public void onError(AError aError) {
                                AppLog.d(TAG, "onError() called with: aError = [" + InitManager.getAErrorString(aError) + "]");
                                // ????????????????????????????????????????????????????????????????????????
                                // ?????????????????????????????????????????????????????????????????????????????????????????????

                                if (aError != null) {
//                                    AppLog.d(TAG, "?????????????????????????????????" + aError.getCode() + "-" + aError.getSubCode() + ", " + aError.getMsg());
                                    showToast("?????????????????????????????????" + aError.getCode() + "-" + aError.getSubCode() + ", " + aError.getMsg());
                                } else {
//                                    AppLog.d(TAG, "???????????????");
                                    showToast("???????????????");
                                }
                            }

                            @Override
                            public void onInitDone(Object data) {
                                AppLog.d(TAG, "onInitDone() called with: data = [" + data + "]");
                                DemoApplication.isInitDone = true;
                                showToast("???????????????");
//                                AppLog.d(TAG, "???????????????");
                            }
                        });
            }
        }).start();
    }

    /**
     * ???????????????????????????????????????
     * ????????????????????????
     */
    private void deinit() {
        AppLog.d(TAG, "deinit");
        DemoApplication.isInitDone = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // ????????????
                LinkKit.getInstance().deinit();
                DASHelper.getInstance().deinit();
                showToast("??????????????????");
//                AppLog.d(TAG, "??????????????????");
            }
        }).start();
    }

    private void publishTest() {
        try {
            AppLog.d(TAG, "publishTest called.");
            MqttPublishRequest request = new MqttPublishRequest();
            // ?????? 0 ??? 1??? ??????0
            request.qos = 1;
            request.isRPC = false;
            request.topic = "/" + DemoApplication.productKey + "/" + DemoApplication.deviceName + "/user/update";
            request.msgId = String.valueOf(IDGenerater.generateId());
            // TODO ?????????????????????????????? ????????????
            request.payloadObj = "{\"id\":\"" + request.msgId + "\", \"version\":\"1.0\"" + ",\"params\":{\"state\":\"1\"} }";
            LinkKit.getInstance().publish(request, new IConnectSendListener() {
                @Override
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    AppLog.d(TAG, "onResponse() called with: aRequest = [" + aRequest + "], aResponse = [" + aResponse + "]");
                    showToast("????????????");
                }

                @Override
                public void onFailure(ARequest aRequest, AError aError) {
                    AppLog.d(TAG, "onFailure() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
                    showToast("???????????? " + (aError != null ? aError.getCode() : "null"));
                }
            });
        } catch (Exception e) {
            showToast("???????????? ");
        }
    }


    private ScheduledFuture future = null;

    @Override
    protected void onResume() {
        super.onResume();
//        testJniLeak();
//        future =future ThreadPool.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                publishTest();
//            }
//        }, 0, 15, TimeUnit.SECONDS);
//        super.onResume();
        sm.registerListener(this,
                Sensor.TYPE_ACCELEROMETER |
                        Sensor.TYPE_MAGNETIC_FIELD |
                        Sensor.TYPE_ORIENTATION |
                        Sensor.TYPE_GYROSCOPE |
                        Sensor.TYPE_LIGHT |
                        Sensor.TYPE_PRESSURE |
                        Sensor.TYPE_TEMPERATURE |
                        Sensor.TYPE_PROXIMITY |
                        Sensor.TYPE_GRAVITY |
                        Sensor.TYPE_LINEAR_ACCELERATION |
                        Sensor.TYPE_ROTATION_VECTOR,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (future != null) {
                future.cancel(true);
                future = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
