package com.example.wxbf.util;

import com.example.wxbf.VO.DistanceVO;
import com.example.wxbf.po.Distance;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//"X-7.25Y0.1875Z-48.25"
public class DistanceUtil {

    /**
     * 根据时间排序（其他排序如根据id排序也类似）
     * @param list
     */
//    public void ListSort(List<Distance> list) {
//        //用Collections这个工具类传list进来排序
//        Collections.sort(list, new Comparator<Distance>() {
//            @Override
//            public int compare(Distance o1, Distance o2) {
////                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                try {
//                    Integer dt1 = Integer.valueOf(o1.getCreateTime());
//                    Integer dt2 = Integer.valueOf(o2.getCreateTime());
//                    if (dt1> dt2) {
//                        return 1;//小的放前面
//                    }else {
//                        return -1;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return 0;
//            }
//        });
//    }



    public DistanceVO XYZ(String xzy){
        DistanceVO distanceVO=new DistanceVO();
        int xdt=xzy.indexOf("X");
        int ydt=xzy.indexOf("Y");
        int zdt=xzy.indexOf("Z");
        String x,y,z;
        x=xzy.substring(xdt+1,ydt-1);
        y=xzy.substring(ydt+1,zdt-1);
        z=xzy.substring(zdt+1,xzy.length()-1);
        distanceVO.setX(x);
        distanceVO.setY(y);
        distanceVO.setZ(z);
        return distanceVO;
    }
}
