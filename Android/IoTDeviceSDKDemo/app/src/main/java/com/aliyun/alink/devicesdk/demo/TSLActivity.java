package com.aliyun.alink.devicesdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.alink.devicesdk.adapter.EventListAdapter;
import com.aliyun.alink.devicesdk.adapter.PropertyListAdapter;
import com.aliyun.alink.devicesdk.app.AppLog;
import com.aliyun.alink.devicesdk.app.DemoApplication;
import com.aliyun.alink.dm.api.BaseInfo;
import com.aliyun.alink.dm.api.DeviceInfo;
import com.aliyun.alink.dm.api.IDMCallback;
import com.aliyun.alink.dm.api.IThing;
import com.aliyun.alink.dm.api.InitResult;
import com.aliyun.alink.linkkit.api.LinkKit;
import com.aliyun.alink.linksdk.tmp.api.InputParams;
import com.aliyun.alink.linksdk.tmp.api.OutputParams;
import com.aliyun.alink.linksdk.tmp.device.payload.ValueWrapper;
import com.aliyun.alink.linksdk.tmp.devicemodel.Arg;
import com.aliyun.alink.linksdk.tmp.devicemodel.Event;
import com.aliyun.alink.linksdk.tmp.devicemodel.Property;
import com.aliyun.alink.linksdk.tmp.devicemodel.Service;
import com.aliyun.alink.linksdk.tmp.listener.IDevRawDataListener;
import com.aliyun.alink.linksdk.tmp.listener.IPublishResourceListener;
import com.aliyun.alink.linksdk.tmp.listener.ITResRequestHandler;
import com.aliyun.alink.linksdk.tmp.listener.ITResResponseCallback;
import com.aliyun.alink.linksdk.tmp.utils.ErrorInfo;
import com.aliyun.alink.linksdk.tmp.utils.GsonUtils;
import com.aliyun.alink.linksdk.tmp.utils.TmpConstant;
import com.aliyun.alink.linksdk.tools.AError;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
 * ??????????????? activity
 * ????????????????????????????????????????????????????????????????????????????????????????????????
 */

public class TSLActivity extends BaseActivity {
    private static final String TAG = "ControlPannelActivity";

    private Spinner mPropertySpinner = null;
    private Spinner mEventSpinner = null;
    private EditText mPropertyET = null;
    private EditText mEventET = null;
    private TextView mDeiviceTypeTV = null;
    private PropertyListAdapter adapter = null;
    private EventListAdapter eventAdapter = null;
    private List<Property> propertyList = null;
    private Map<String, ValueWrapper> reportData = null;
    private boolean isSubDev = false;

    private final static String SERVICE_SET = "set";
    private final static String SERVICE_GET = "get";
    private final static String CONNECT_ID = "LINK_PERSISTENT";

    final static Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");

    private final static int DEF_VALUE = Integer.MIN_VALUE;

    private BaseInfo mBaseInfo = new BaseInfo();
    private String productKey = null;
    private String deviceName = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_pannel);
        reportData = new HashMap<>();
        parseIntent();
        if (!isSubDev) {
            setServiceHandler();
        }
        initViews();
        // ???????????????????????????
        if (!isSubDev) {
            thingRawPropertiesPost();
        } else {
            initSubDeviceThingModel();
        }
    }

    private void parseIntent() {
        Intent intent = getIntent();

        try {
            if (intent == null || intent.getExtras() == null){
                AppLog.d(TAG, "intent with no data. Non sub device.");
                productKey = DemoApplication.productKey;
                deviceName = DemoApplication.deviceName;
                isSubDev = false;
                return;
            }
            productKey = intent.getExtras().getString("pk", DemoApplication.productKey);
            deviceName = intent.getExtras().getString("dn", DemoApplication.deviceName);
            isSubDev = intent.getExtras().getBoolean("sub", false);
            if (isSubDev) {
                mBaseInfo.productKey = productKey;
                mBaseInfo.deviceName = deviceName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSubDeviceThingModel() {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.productKey = productKey;
        deviceInfo.deviceName = deviceName;
//        deviceInfo.deviceSecret = "xxxx"; //TODO
        Map<String, ValueWrapper> subDevInitState = new HashMap<>();
//        subDevInitState.put(); //TODO
        LinkKit.getInstance().getGateway().initSubDeviceThing(null, deviceInfo, subDevInitState, new IDMCallback<InitResult>() {
            @Override
            public void onSuccess(InitResult initResult) {
                AppLog.d(TAG, "onSuccess() called with: initResult = [" + initResult + "]");
                showToast("????????????????????????");
                setServiceHandler();
                initValues();
            }

            @Override
            public void onFailure(AError aError) {
                AppLog.d(TAG, "onFailure() called with: aError = [" + (aError==null?null:(aError.getCode()+aError.getMsg())) + "]");
                showToast("????????????????????????");
            }
        });
    }

    private void initViews() {
        mPropertySpinner = findViewById(R.id.property_spinner);
        mEventSpinner = findViewById(R.id.event_spinner);

        mPropertyET = findViewById(R.id.value_text);
        mEventET = findViewById(R.id.event_value);

        mDeiviceTypeTV = findViewById(R.id.device_name);
        adapter = new PropertyListAdapter(this);
        mPropertySpinner.setAdapter(adapter);
        if (!isSubDev) {
            adapter.setListData(LinkKit.getInstance().getDeviceThing().getProperties());
            adapter.notifyDataSetChanged();
        }
        mPropertySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppLog.d(TAG, "property onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
                Property property = (Property) adapter.getItem(position);
                updatePropertyValue(property);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mPropertyET.setText("");
            }
        });
        eventAdapter = new EventListAdapter(this);
        mEventSpinner.setAdapter(eventAdapter);
        if (!isSubDev) {
            eventAdapter.setListData(LinkKit.getInstance().getDeviceThing().getEvents());
            eventAdapter.notifyDataSetChanged();
        }
        mEventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppLog.d(TAG, "event onItemSelected() called with: parent = [" + parent + "], view = [" + view + "], position = [" + position + "], id = [" + id + "]");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mEventET.setText("");
            }
        });
    }

    private boolean isValidDouble(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        try {
            if (pattern != null && pattern.matcher(value) != null) {
                if (pattern.matcher(value).matches()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Double getDouble(String value) {
        if (isValidDouble(value)) {
            return Double.parseDouble(value);
        }
        return null;
    }

    private boolean isValidInt(String value) {
        return !TextUtils.isEmpty(value);
    }


    private int getInt(String value) {
        if (isValidInt(value)) {
            try {
                return Integer.parseInt(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DEF_VALUE;
    }

    private void initValues() {
        if (isSubDev) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IThing thing = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first;
                    if (thing != null) {
                        adapter.setListData(thing.getProperties());
                        adapter.notifyDataSetChanged();
                        eventAdapter.setListData(thing.getEvents());
                        eventAdapter.notifyDataSetChanged();
                        mDeiviceTypeTV.setText(String.format(getResources().getString(R.string.control_pannel_device_name), productKey));
                    }
                }
            });
            return;
        }
        propertyList = LinkKit.getInstance().getDeviceThing().getProperties();
        adapter.setListData(propertyList);
        adapter.notifyDataSetChanged();
        mDeiviceTypeTV.setText(String.format(getResources().getString(R.string.control_pannel_device_name), productKey));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initValues();
//        connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void report(String identifier, ValueWrapper valueWrapper) {
        reportData.clear();
        Map<String, ValueWrapper> reportData  = new HashMap<>();
        ////
        reportData.put(identifier, valueWrapper);
//        reportData.put("LightLux",new ValueWrapper.IntValueWrapper(150));
        ///
        LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, resourceListener);
        if (!isSubDev) {
            try {
                LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, resourceListener);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("???????????? " + e);
            }
        } else {
            try {
                IThing thing = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first;
                if (thing == null){
                    showToast("??????????????????????????????");
                    return;
                }
                thing.thingPropertyPost(reportData, resourceListener);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("???????????? " + e);
            }
        }
    }

    private void report(Map<String, ValueWrapper> dataWrapper) {
        reportData.clear();
        reportData.putAll(dataWrapper);
        if (!isSubDev) {
            try {
                LinkKit.getInstance().getDeviceThing().thingPropertyPost(reportData, resourceListener);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("???????????? " + e);
            }
        } else {
            try {
                IThing thing = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first;
                if (thing == null){
                    showToast("??????????????????????????????");
                    return;
                }
                thing.thingPropertyPost(reportData, resourceListener);
            } catch (Exception e) {
                e.printStackTrace();
                showToast("???????????? " + e);
            }
        }
    }

    private static IPublishResourceListener resourceListener = new IPublishResourceListener() {
        @Override
        public void onSuccess(String s, Object o) {
            AppLog.d(TAG, "onSuccess() called with: s = [" + s + "], o = [" + o + "]");
            showToast("?????????????????????????????????code=200???????????????????????????????????????data???????????????????????????????????????");
        }

        @Override
        public void onError(String s, AError aError) {
            AppLog.d(TAG, "onError() called with: s = [" + s + "], aError = [" + aError + "]");
            showToast("????????????????????????");
        }
    };

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????
     * ???????????????????????????????????? Error ?????????
     */
    private void setServiceHandler() {
        AppLog.d(TAG, "setServiceHandler() called");
        IThing thing = null;
        if (!isSubDev) {
            thing = LinkKit.getInstance().getDeviceThing();
        } else {
            thing = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first;
        }
        if (thing == null){
            showToast("???????????????????????????");
            return;
        }

        List<Service> srviceList = thing.getServices();
        for (int i = 0; srviceList != null && i < srviceList.size(); i++) {
            Service service = srviceList.get(i);
            if (!isSubDev) {
                thing.setServiceHandler(service.getIdentifier(), resRequestHandler);
            } else{
                thing.setServiceHandler(service.getIdentifier(), resRequestHandler);
            }
        }
        //
    }

    static class ResRequestHandler implements ITResRequestHandler{
        WeakReference<ITResRequestHandler> handlerWakRef = null;

        public ResRequestHandler(ITResRequestHandler handler) {
            handlerWakRef = new WeakReference<>(handler);
        }

        @Override
        public void onProcess(String s, Object o, ITResResponseCallback itResResponseCallback) {
            if (handlerWakRef != null && handlerWakRef.get() != null) {
                handlerWakRef.get().onProcess(s, o, itResResponseCallback);
            }
        }

        @Override
        public void onSuccess(Object o, OutputParams outputParams) {
            if (handlerWakRef != null && handlerWakRef.get() != null) {
                handlerWakRef.get().onSuccess(o, outputParams);
            }
        }

        @Override
        public void onFail(Object o, ErrorInfo errorInfo) {
            if (handlerWakRef != null && handlerWakRef.get() != null) {
                handlerWakRef.get().onFail(o, errorInfo);
            }
        }
    }

    private ITResRequestHandler mCommonHandler = new ITResRequestHandler() {
        @Override
        public void onProcess(String identify, Object result, ITResResponseCallback itResResponseCallback) {
            AppLog.d(TAG, "onProcess() called with: s = [" + identify + "], o = [" + result + "], itResResponseCallback = [" + itResResponseCallback + "]");
            showToast("???????????????????????? " + identify);
            try {
                if (SERVICE_SET.equals(identify)) {
                    // TODO  ???????????????????????????????????????  ?????????????????????
                    // ??????????????????????????????????????????????????????????????????
                    // ?????????????????????????????????????????????????????? ??????????????????????????????
                    boolean isSetPropertySuccess = true;
                    if (isSetPropertySuccess){
                        if (result instanceof InputParams) {
                            Map<String, ValueWrapper> data = (Map<String, ValueWrapper>) ((InputParams) result).getData();
//                        data.get()

                            // ???????????? ??????????????????
                            itResResponseCallback.onComplete(identify, null, null);
                        } else {
                            itResResponseCallback.onComplete(identify, null, null);
                        }
                        updatePropertyValue((Property) mPropertySpinner.getSelectedItem());
                    } else {
                        AError error = new AError();
                        error.setCode(100);
                        error.setMsg("setPropertyFailed.");
                        itResResponseCallback.onComplete(identify, new ErrorInfo(error), null);
                    }

                } else if (SERVICE_GET.equals(identify)){
                    //  ???????????????????????????????????????????????????????????????????????????????????????????????????

                } else {
                    // ?????????????????????????????????????????????????????????????????????
                    showToast("?????????????????????????????????????????????????????????set??????");
                    OutputParams outputParams = new OutputParams();
//                    outputParams.put("op", new ValueWrapper.IntValueWrapper(20));
                    itResResponseCallback.onComplete(identify,null, outputParams);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showToast("TMP ????????????????????????");
            }
        }

        @Override
        public void onSuccess(Object o, OutputParams outputParams) {
            AppLog.d(TAG, "onSuccess() called with: o = [" + o + "], outputParams = [" + outputParams + "]");
            showToast("??????????????????");
        }

        @Override
        public void onFail(Object o, ErrorInfo errorInfo) {
            AppLog.d(TAG, "onFail() called with: o = [" + o + "], errorInfo = [" + errorInfo + "]");
            showToast("??????????????????");
        }
    };

    private ResRequestHandler resRequestHandler = new ResRequestHandler(mCommonHandler);

    private void updatePropertyValue(final Property property) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (property != null && property.equals(mPropertySpinner.getSelectedItem())) {
                    ValueWrapper valueWrapper = null;
                    if (!isSubDev) {
                        valueWrapper = LinkKit.getInstance().getDeviceThing().getPropertyValue(property.getIdentifier());
                    } else {
                        valueWrapper = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first.getPropertyValue(property.getIdentifier());
                    }
                    if (valueWrapper != null && valueWrapper.getValue() != null) {
                        String showVaue = String.valueOf(valueWrapper.getValue());
                        if (valueWrapper.getValue() instanceof List){
                            StringBuffer sb = new StringBuffer();
                            sb.append("[");
                            for (int i = 0; i < ((List<ValueWrapper>) valueWrapper.getValue()).size(); i++) {
                                ValueWrapper item = ((List<ValueWrapper>) valueWrapper.getValue()).get(i);
                                if ("string".equals(item.getType())){
                                    sb.append("\"").append(item.getValue()).append("\"");
                                } else {
                                    sb.append(item.getValue());
                                }
                                if (i != ((List) valueWrapper.getValue()).size() - 1){
                                    sb.append(",");
                                }
                            }
                            sb.append("]");
                            showVaue = sb.toString();
                        } else if (valueWrapper.getValue() instanceof Map){
                            StringBuffer sb = new StringBuffer();
                            sb.append("{");
                            for (Map.Entry<String, ValueWrapper> entry: ((Map<String, ValueWrapper>) valueWrapper.getValue()).entrySet()){
                                sb.append("\"").append(entry.getKey()).append("\"").append(":");
                                if ("string".equals(entry.getValue().getType())){
                                    sb.append("\"").append(entry.getValue().getValue()).append("\"");
                                } else {
                                    sb.append(entry.getValue().getValue());
                                }
                                sb.append(",");
                            }
                            if (sb.length() > 1){
                                sb.deleteCharAt(sb.length()-1);
                            }
                            sb.append("}");
                            showVaue = sb.toString();
                        }
                        mPropertyET.setText(showVaue);
                    } else {
                        mPropertyET.setText("");
                    }
                }
            }
        });
    }

    public void postProperty(View view) {
        try {
            Property property = (Property) mPropertySpinner.getSelectedItem();
            if (property == null) {
                showToast("?????????property??????");
                return;
            }
            String value = mPropertyET.getText().toString();
            if (property == null || value == null || property.getIdentifier() == null) {
                showToast("??????????????????");
                return;
            }
            if (property.getDataType() == null) {
                showToast("??????????????????");
                return;
            }
//        if (true){
//            ValueWrapper valueWrapper = GsonUtils.fromJson(value, new TypeToken<ValueWrapper>(){}.getType());
//            report(property.getIdentifier(), valueWrapper);
//            return;
//        }
            if (TmpConstant.TYPE_VALUE_INTEGER.equals(property.getDataType().getType())) {
                int parseData = getInt(value);
                if (parseData != DEF_VALUE) {
                    report(property.getIdentifier(), new ValueWrapper.IntValueWrapper(parseData));
                } else {
                    showToast("??????????????????");
                }
                return;
            }
            if (TmpConstant.TYPE_VALUE_FLOAT.equals(property.getDataType().getType())) {
                Double parseData = getDouble(value);
                if (parseData != null) {
                    report(property.getIdentifier(), new ValueWrapper.DoubleValueWrapper(parseData));
                } else {
                    showToast("??????????????????");
                }
                return;
            }
            if (TmpConstant.TYPE_VALUE_DOUBLE.equals(property.getDataType().getType())) {
                Double parseData = getDouble(value);
                if (parseData != null) {
                    report(property.getIdentifier(), new ValueWrapper.DoubleValueWrapper(parseData));
                } else {
                    showToast("??????????????????");
                }
                return;
            }
            if (TmpConstant.TYPE_VALUE_BOOLEAN.equals(property.getDataType().getType())) {
                int parseData = getInt(value);
                if (parseData == 0 || parseData == 1) {
                    report(property.getIdentifier(), new ValueWrapper.BooleanValueWrapper(parseData));
                } else {
                    showToast("??????????????????");
                }
                return;
            }
            if (TmpConstant.TYPE_VALUE_TEXT.equals(property.getDataType().getType())) {
                report(property.getIdentifier(), new ValueWrapper.StringValueWrapper(value));
                return;
            }
            if (TmpConstant.TYPE_VALUE_DATE.equals(property.getDataType().getType())) {
                report(property.getIdentifier(), new ValueWrapper.DateValueWrapper(value));
                return;
            }
            if(TmpConstant.TYPE_VALUE_ENUM.equalsIgnoreCase(property.getDataType().getType())){
                report(property.getIdentifier(),new ValueWrapper.EnumValueWrapper(getInt(value)));
                return;
            }
            if(TmpConstant.TYPE_VALUE_ARRAY.equalsIgnoreCase(property.getDataType().getType())){
                ValueWrapper.ArrayValueWrapper arrayValueWrapper = GsonUtils.fromJson(value,new TypeToken<ValueWrapper>(){}.getType());
                report(property.getIdentifier(),arrayValueWrapper);
                return;
            }
            // ?????????????????????  ??????????????????????????????????????????
            if (TmpConstant.TYPE_VALUE_STRUCT.equals(property.getDataType().getType())) {
                try {
                    List<Map<String, Object>> specsList = (List<Map<String, Object>>) property.getDataType().getSpecs();
                    if (specsList == null ||specsList.size() == 0){
                        showToast("???????????????struct????????????????????????????????????");
                        return;
                    }
                    JSONObject dataJson = JSONObject.parseObject(value);
                    Map<String, ValueWrapper> dataMap = new HashMap<>();
                    Map<String, Object> specsItem = null;
                    for (int i = 0; i < specsList.size(); i++) {
                        specsItem = specsList.get(i);
                        if (specsItem == null){
                            continue;
                        }
                        String idKey = (String) specsItem.get("identifier");
                        String dataType = (String) ((Map)specsItem.get("dataType")).get("type");
                        if (idKey != null && dataJson.containsKey(idKey) && dataType != null){
                            ValueWrapper valueItem = null;
                            if ("int".equals(dataType)){
                                valueItem = new ValueWrapper.IntValueWrapper(getInt(String.valueOf(dataJson.get(idKey))));
                            } else if ("text".equals(dataType)){
                                valueItem = new ValueWrapper.StringValueWrapper((String) dataJson.get(idKey));
                            } else if ("float".equals(dataType) || "double".equals(dataType)){
                                valueItem = new ValueWrapper.DoubleValueWrapper(getDouble(String.valueOf(dataJson.get(idKey))));
                            } else if ("bool".equals(dataType)){
                                valueItem = new ValueWrapper.BooleanValueWrapper(getInt(String.valueOf(dataJson.get(idKey))));
                            } else if ("date".equals(dataType)){
                                if (isValidInt(String.valueOf(dataJson.get(idKey)))) {
                                    valueItem = new ValueWrapper.DateValueWrapper(String.valueOf(dataJson.get(idKey)));
                                } else {
                                    showToast("??????????????????");
                                }
                            } else if ("enum".equals(dataType)){
                                valueItem = new ValueWrapper.EnumValueWrapper(getInt(String.valueOf(dataJson.get(idKey))));
                            } else {
                                showToast("?????????????????????");
                            }
                            if (valueItem != null) {
                                dataMap.put(idKey, valueItem);
                            }
                        }
                    }

                    report(property.getIdentifier(), new ValueWrapper.StructValueWrapper(dataMap));
                } catch (Exception e){
                    showToast("?????????????????????");
                }
                return;
            }
            showToast("?????????Demo?????????????????????????????????????????????????????????????????????");
        } catch (Exception e) {
            showToast("??????????????????");
            e.printStackTrace();
        }
    }

    public void postEvent(View view) {
        Event event = (Event) mEventSpinner.getSelectedItem();
        if (event == null) {
            showToast("?????????????????????");
            return;
        }

        HashMap<String, ValueWrapper> hashMap = new HashMap<>();
        try {
            String mapEventData = mEventET.getText().toString();
            JSONObject object = JSONObject.parseObject(mapEventData);
            if (object == null){
                showToast("??????????????????");
                return;
            }
            if (event.getOutputData() != null) {
                for (int i = 0; i < event.getOutputData().size(); i++) {
                    Arg arg = event.getOutputData().get(i);
                    if (arg == null || arg.getDataType() == null || arg.getIdentifier() == null){
                        continue;
                    }
                    String idnValue = String.valueOf(object.get(arg.getIdentifier()));
                    if (idnValue == null || object.get(arg.getIdentifier()) == null){
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_INTEGER.equals(arg.getDataType().getType())) {
                        int parseData = getInt(idnValue);
                        if (parseData != DEF_VALUE) {
                            hashMap.put(arg.getIdentifier(), new ValueWrapper.IntValueWrapper(parseData));
                        } else {
                            showToast("??????????????????");
                            break;
                        }
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_FLOAT.equals(arg.getDataType().getType())) {
                        Double parseData = getDouble(idnValue);
                        if (parseData != null) {
                            hashMap.put(arg.getIdentifier(), new ValueWrapper.DoubleValueWrapper(parseData));
                        } else {
                            showToast("??????????????????");
                            break;
                        }
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_DOUBLE.equals(arg.getDataType().getType())) {
                        Double parseData = getDouble(idnValue);
                        if (parseData != null) {
                            hashMap.put(arg.getIdentifier(), new ValueWrapper.DoubleValueWrapper(parseData));
                        } else {
                            showToast("??????????????????");
                            break;
                        }
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_BOOLEAN.equals(arg.getDataType().getType())) {
                        int parseData = getInt(idnValue);
                        if (parseData == 0 || parseData == 1) {
                            hashMap.put(arg.getIdentifier(), new ValueWrapper.BooleanValueWrapper(parseData));
                        } else {
                            showToast("??????????????????");
                            break;
                        }
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_TEXT.equals(arg.getDataType().getType())) {
                        hashMap.put(arg.getIdentifier(), new ValueWrapper.StringValueWrapper(idnValue));
                        continue;
                    }
                    if (TmpConstant.TYPE_VALUE_DATE.equals(arg.getDataType().getType())) {
                        hashMap.put(arg.getIdentifier(), new ValueWrapper.DateValueWrapper(idnValue));
                        continue;
                    }
                    if(TmpConstant.TYPE_VALUE_ENUM.equalsIgnoreCase(arg.getDataType().getType())){
                        hashMap.put(arg.getIdentifier(),new ValueWrapper.EnumValueWrapper(getInt(idnValue)));
                        continue;
                    }
                    if(TmpConstant.TYPE_VALUE_ARRAY.equalsIgnoreCase(arg.getDataType().getType())){
                        ValueWrapper.ArrayValueWrapper arrayValueWrapper = GsonUtils.fromJson(idnValue,new TypeToken<ValueWrapper>(){}.getType());
                        hashMap.put(arg.getIdentifier(),arrayValueWrapper);
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("??????????????????");
            return;
        }
        try {
            OutputParams params = new OutputParams(hashMap);
            if (!isSubDev) {
                LinkKit.getInstance().getDeviceThing().thingEventPost(event.getIdentifier(), params, resourceListener);
            } else {
                IThing thing = LinkKit.getInstance().getGateway().getSubDeviceThing(mBaseInfo).first;
                if (thing == null){
                    showToast("??????????????????????????????");
                    return;
                }
                thing.thingEventPost(event.getIdentifier(), params, resourceListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("???????????? " + e);
        }
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????
     */
    public void thingRawPropertiesPost() {
        try {
            byte[] rawData = {0x01,0x02,0x03,0x04,0x05,0x06,0x07};

            LinkKit.getInstance().getDeviceThing().thingRawPropertiesPost(rawData, devRawDataListener);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("???????????? " + e);
        }
    }

    private static IDevRawDataListener devRawDataListener =  new IDevRawDataListener() {
        @Override
        public void onSuccess(Object o, Object o1) {
            AppLog.d(TAG, "onSuccess() called with: s = [" + o1 + "], o = [" + o + "]");
            showToast("?????????????????????????????????????????????????????????????????????");
        }

        @Override
        public void onFail(Object o, ErrorInfo errorInfo) {
            AppLog.d(TAG, "onError() called with: s = [" + o + "], aError = [" + errorInfo + "]");
            showToast("????????????????????????");
        }
    };
}
