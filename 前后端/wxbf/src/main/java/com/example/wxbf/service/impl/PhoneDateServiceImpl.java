package com.example.wxbf.service.impl;

import com.example.wxbf.po.PhoneData;
import com.example.wxbf.service.PhoneDataService;
import com.example.wxbf.service.PhoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneDateServiceImpl implements PhoneDataService {
    @Override
    public List<PhoneData> findAll() {
        return null;
    }

    @Override
    public Page<PhoneData> listPhoneDataByUserId(Long blogId, Pageable pageable) {
        return null;
    }

    @Override
    public PhoneData savePhoneData(PhoneData phoneData) {
        return null;
    }

    @Override
    public PhoneData add(String time, String value, String datatype, String identifier, String unit, String name) {
        return null;
    }

    @Override
    public void addlist() throws Exception {

    }
}
