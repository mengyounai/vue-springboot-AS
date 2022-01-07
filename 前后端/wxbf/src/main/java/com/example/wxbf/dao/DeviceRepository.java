package com.example.wxbf.dao;

import com.example.wxbf.po.Device;
import com.example.wxbf.po.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceRepository extends JpaRepository<Device, Long> {



    }
