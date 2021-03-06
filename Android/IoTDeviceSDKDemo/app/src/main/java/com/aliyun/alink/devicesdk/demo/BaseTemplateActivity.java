package com.aliyun.alink.devicesdk.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.alink.devicesdk.app.AppLog;
import com.aliyun.alink.devicesdk.app.DemoApplication;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttPublishRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttRrpcRegisterRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttRrpcRequest;
import com.aliyun.alink.linksdk.cmp.connect.channel.MqttSubscribeRequest;
import com.aliyun.alink.linksdk.cmp.core.base.ARequest;
import com.aliyun.alink.linksdk.cmp.core.base.AResponse;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcHandle;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectRrpcListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSendListener;
import com.aliyun.alink.linksdk.cmp.core.listener.IConnectSubscribeListener;
import com.aliyun.alink.linksdk.tools.AError;

import java.io.UnsupportedEncodingException;

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

public abstract class BaseTemplateActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "BaseTemplateActivity";

    protected RelativeLayout funcRL1, funcRL2, funcRL3, funcRL4, funcRL5, funcRL6;
    protected TextView funcTV1, funcTV2, funcTV3, funcTV4, funcTV5, funcTV6;
    protected EditText funcET1, funcET2, funcET3, funcET4, funcET5, funcET6;
    protected Button funcBT1, funcBT2, funcBT3, funcBT4, funcBT5, funcBT6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_template);
        initViews();
        initViewData();
    }

    protected abstract void initViewData();

    private void initViews() {
        funcRL1 = findViewById(R.id.func1);
        funcTV1 = findViewById(R.id.func1_name);
        funcET1 = findViewById(R.id.func1_param);
        funcBT1 = findViewById(R.id.func1_exec);
        funcBT1.setOnClickListener(this);

        funcRL2 = findViewById(R.id.func2);
        funcTV2 = findViewById(R.id.func2_name);
        funcET2 = findViewById(R.id.func2_param);
        funcBT2 = findViewById(R.id.func2_exec);
        funcBT2.setOnClickListener(this);

        funcRL3 = findViewById(R.id.func3);
        funcTV3 = findViewById(R.id.func3_name);
        funcET3 = findViewById(R.id.func3_param);
        funcBT3 = findViewById(R.id.func3_exec);
        funcBT3.setOnClickListener(this);

        funcRL4 = findViewById(R.id.func4);
        funcTV4 = findViewById(R.id.func4_name);
        funcET4 = findViewById(R.id.func4_param);
        funcBT4 = findViewById(R.id.func4_exec);
        funcBT4.setOnClickListener(this);

        funcRL5 = findViewById(R.id.func5);
        funcTV5 = findViewById(R.id.func5_name);
        funcET5 = findViewById(R.id.func5_param);
        funcBT5 = findViewById(R.id.func5_exec);
        funcBT5.setOnClickListener(this);

        funcRL6 = findViewById(R.id.func6);
        funcTV6 = findViewById(R.id.func6_name);
        funcET6 = findViewById(R.id.func6_param);
        funcBT6 = findViewById(R.id.func6_exec);
        funcBT6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.func1_exec:
                onFunc1Click();
                break;
            case R.id.func2_exec:
                onFunc2Click();
                break;
            case R.id.func3_exec:
                onFunc3Click();
                break;
            case R.id.func4_exec:
                onFunc4Click();
                break;
            case R.id.func5_exec:
                onFunc5Click();
                break;
            case R.id.func6_exec:
                onFunc6Click();
                break;
        }
    }

    protected abstract void onFunc1Click();

    protected abstract void onFunc2Click();

    protected void onFunc3Click() {
    }

    protected void onFunc4Click() {
    }

    protected void onFunc5Click() {
    }

    protected void onFunc6Click() {
    }


    protected static IConnectSendListener mConnectSendListener = new IConnectSendListener() {
        @Override
        public void onResponse(ARequest aRequest, AResponse aResponse) {
            AppLog.d(TAG, "onResponse() called with: aRequest = [" + aRequest + "], aResponse = [" + (aResponse == null ? null : aResponse.data) + "]");
            if (aRequest instanceof MqttPublishRequest) {
                showToast(((MqttPublishRequest) aRequest).topic + "??????");
                if (((MqttPublishRequest) aRequest).topic != null && ((MqttPublishRequest) aRequest).topic.contains("thing/reset")) {
                    DemoApplication.isInitDone = false;
                }
                return;
            }
            if (aRequest instanceof MqttRrpcRegisterRequest) {
                showToast(((MqttRrpcRegisterRequest) aRequest).topic + "??????");
                return;
            }
            if (aRequest instanceof MqttSubscribeRequest) {
                showToast(((MqttSubscribeRequest) aRequest).topic + "??????");
                return;
            }
            showToast("????????????");
        }

        @Override
        public void onFailure(ARequest aRequest, AError aError) {
            AppLog.d(TAG, "onFailure() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
            if (aRequest instanceof MqttPublishRequest) {
                showToast(((MqttPublishRequest) aRequest).topic + "??????");
                if (((MqttPublishRequest) aRequest).topic != null && ((MqttPublishRequest) aRequest).topic.contains("thing/reset")) {
                    DemoApplication.isInitDone = false;
                }
                return;
            }
            if (aRequest instanceof MqttRrpcRegisterRequest) {
                showToast(((MqttRrpcRegisterRequest) aRequest).topic + "??????");
                return;
            }
            if (aRequest instanceof MqttSubscribeRequest) {
                showToast(((MqttSubscribeRequest) aRequest).topic + "??????");
                return;
            }
            showToast("????????????");
        }
    };

    protected static IConnectSubscribeListener mSubscribeListener = new IConnectSubscribeListener() {
        @Override
        public void onSuccess() {
            showToast("????????????");
        }

        @Override
        public void onFailure(AError aError) {
            showToast("????????????");
        }
    };

    protected static IConnectRrpcListener mConnectRrpcListener = new IConnectRrpcListener() {
        @Override
        public void onSubscribeSuccess(ARequest aRequest) {
            AppLog.d(TAG, "onSubscribeSuccess() called with: aRequest = [" + aRequest + "]");
            showToast("????????????");
        }

        @Override
        public void onSubscribeFailed(ARequest aRequest, AError aError) {
            AppLog.d(TAG, "onSubscribeFailed() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
            showToast("????????????");
        }

        @Override
        public void onReceived(ARequest aRequest, IConnectRrpcHandle iConnectRrpcHandle) {
            AppLog.d(TAG, "onReceived() called with: aRequest = [" + aRequest + "], iConnectRrpcHandle = [" + iConnectRrpcHandle + "]");
            showToast("?????????????????????");
            if (aRequest instanceof MqttRrpcRequest){
                // ?????????????????? ??????
                try {
                    String data = new String((byte[]) (((MqttRrpcRequest) aRequest).payloadObj), "UTF-8");
                    AppLog.d(TAG, "payloadObj=" + data);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            // ????????????  ??????????????????
            /*{
                "id": "123",
                "version": "1.0",
                "code": 200,
                "data": {
                "configId": "123dagdah",
                    "configSize": 1234565,
                    "sign": "123214adfadgadg",
                    "signMethod": "Sha256",
                    "url": "https://iotx-config.oss-cn-shanghai.aliyuncs.com/xxx",
                    "getType": "file"
                }
            }*/

            // ??????PermitJoin ??????????????????
            //{"id":"",    //??????id
//                            "version":"1.0",    //ALink??????????????????
//                            "params":{
            //                        "productKey":"xxx",   //?????????????????????????????????????????????????????????????????????????????????
            //                                "time":60    //????????????????????????????????????????????????int?????????????????????
//                              }
//                    }


            // ??????????????????
            // ???????????????json????????????id???????????????????????????????????????????????????????????????????????????????????????
//                if (iConnectRrpcHandle != null){
//                    String topic = null;
//                    if (aRequest instanceof MqttRrpcRequest) {
//                        topic = ((MqttRrpcRequest) aRequest).topic;
//                    }
//                    if (TextUtils.isEmpty(topic)) {
//                        return;
//                    }
//                    AResponse aResponse = new AResponse();
//                    // ???????????????????????????????????????????????????????????????????????????data?????????
////                    aResponse.data = "{\"id\":\"" + 123 + "\", \"code\":\"200\"" + ",\"data\":{} }";
//                    iConnectRrpcHandle.onRrpcResponse(topic, aResponse);
//                }

            // ??????????????????json???????????????????????????????????????
            MqttPublishRequest rrpcResponse = new MqttPublishRequest();
            if (aRequest instanceof MqttRrpcRequest) {
                rrpcResponse.topic = ((MqttRrpcRequest) aRequest).topic;
            }
            rrpcResponse.payloadObj ="{\"id\":\"123\", \"code\":\"200\"" + ",\"data\":{} }";

            LinkKit.getInstance().publish(rrpcResponse, new IConnectSendListener() {
                @Override
                public void onResponse(ARequest aRequest, AResponse aResponse) {
                    AppLog.d(TAG, "onResponse() called with: aRequest = [" + aRequest + "], aResponse = [" + aResponse + "]");
                }

                @Override
                public void onFailure(ARequest aRequest, AError aError) {
                    AppLog.d(TAG, "onFailure() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
                }
            });

        }

        @Override
        public void onResponseSuccess(ARequest aRequest) {
            AppLog.d(TAG, "onResponseSuccess() called with: aRequest = [" + aRequest + "]");
        }

        @Override
        public void onResponseFailed(ARequest aRequest, AError aError) {
            AppLog.d(TAG, "onResponseFailed() called with: aRequest = [" + aRequest + "], aError = [" + aError + "]");
        }
    };
}
