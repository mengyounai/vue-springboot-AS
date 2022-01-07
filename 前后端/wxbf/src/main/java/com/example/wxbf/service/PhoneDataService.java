package com.example.wxbf.service;


import com.example.wxbf.po.Phone;
import com.example.wxbf.po.PhoneData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhoneDataService {

    //查询所有数据
    List<PhoneData> findAll();

    //根据用户id查询前xx条数据
    Page<PhoneData> listPhoneDataByUserId(Long blogId, Pageable pageable);

    //新增数据
    PhoneData savePhoneData(PhoneData phoneData);

    //新增数据
    PhoneData add(String time, String value, String datatype, String identifier, String unit, String name);

    //新增多条数据
    void addlist() throws Exception;

}
