package com.example.wxbf.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_device")
public class Device {
    @Id
    @GeneratedValue
    private Long id;

    private String ProductKey;

    private String DeviceName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private Product product;

    @OneToMany(mappedBy = "device")
    private List<Distance> distances = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductKey() {
        return ProductKey;
    }

    public void setProductKey(String productKey) {
        ProductKey = productKey;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", ProductKey='" + ProductKey + '\'' +
                ", DeviceName='" + DeviceName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", product=" + product +
                ", distances=" + distances +
                '}';
    }
}
