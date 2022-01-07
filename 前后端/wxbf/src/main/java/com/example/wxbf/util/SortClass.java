package com.example.wxbf.util;

import com.example.wxbf.po.Distance;

import java.util.Comparator;

public class SortClass implements Comparator {
    public int compare(Object arg0, Object arg1) {
        Distance distance = (Distance) arg0;
        Distance distance2 = (Distance) arg1;
        int flag = distance.getCreateTime().compareTo(distance2.getCreateTime());
        return flag;
    }
}
