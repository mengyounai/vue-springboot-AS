package com.example.wxbf.dao;

import com.example.wxbf.po.Device;
import com.example.wxbf.po.Distance;
import com.example.wxbf.po.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DistanceRepository extends JpaRepository<Distance, Long> {

//    @Query("select * from Distance order by createTime desc limit 10")
//    Device dateDesc();

}
