package com.example.wxbf.po;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "t_data")
public class PhoneData {

    @Id
    @GeneratedValue
    private Long id;
    private String light;
    private String pressure ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Phone phone;

    public PhoneData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PhoneData{" +
                "id=" + id +
                ", light='" + light + '\'' +
                ", pressure='" + pressure + '\'' +
                ", createTime=" + createTime +
                ", phone=" + phone +
                '}';
    }
}
