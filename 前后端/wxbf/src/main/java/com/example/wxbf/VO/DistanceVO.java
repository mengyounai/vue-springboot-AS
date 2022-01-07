package com.example.wxbf.VO;

import javax.persistence.Entity;


public class DistanceVO {

    private String x;
    private String y;
    private String z;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "DistanceVO{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }
}
