package com.example.wxbf.service.impl;

import com.aliyun.iot20180120.models.*;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.wxbf.dao.ProductRepository;
import com.example.wxbf.po.Product;
import com.example.wxbf.service.AliYunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AliYunServiceImpl implements AliYunService {

    private static String accessKeyId="LTAI5tGD9EP9YMEgrGLRWkH6";
    private static String accessKeySecret="R0SClrvQTlmzm299TRgVSyj6LkaQ0w";

    @Autowired
    ProductRepository productRepository;

    public static com.aliyun.iot20180120.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "iot.cn-shanghai.aliyuncs.com";
        return new com.aliyun.iot20180120.Client(config);
    }

    @Override
    public void deleteProduct2( String ProductKey) throws Exception {

        com.aliyun.iot20180120.Client client =AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);

        DeleteProductRequest deleteProductRequest = new DeleteProductRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey(ProductKey);

        client.deleteProduct(deleteProductRequest);
    }

    @Override
    public boolean addProduct(String ProductName, Integer Node) throws Exception {
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        CreateProductRequest createProductRequest = new CreateProductRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductName(ProductName)
                .setNodeType(Node);
        // 复制代码运行请自行打印 API 的返回值
        CreateProductResponse resp = client.createProduct(createProductRequest);
        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));
        return resp.body.success;

    }

    @Override
    public void addDevice(String ProductKey, String DeviceName) throws Exception {
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        RegisterDeviceRequest registerDeviceRequest = new RegisterDeviceRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey(ProductKey)
                .setDeviceName(DeviceName);
        // 复制代码运行请自行打印 API 的返回值
        client.registerDevice(registerDeviceRequest);

    }

    @Override
    public void UpdateProduct(String Description, String ProductKey, String ProductName) throws Exception {
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        UpdateProductRequest updateProductRequest = new UpdateProductRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setProductKey(ProductKey)
                .setProductName(ProductName)
                .setDescription(Description);
        // 复制代码运行请自行打印 API 的返回值
        client.updateProduct(updateProductRequest);
    }

    @Override
    public List<Product> QueryProductList(Integer PageSize, Integer CurrentPage) throws Exception {
        com.aliyun.iot20180120.Client client = AliYunServiceImpl.createClient(accessKeyId, accessKeySecret);
        QueryProductListRequest queryProductListRequest = new QueryProductListRequest()
                .setIotInstanceId("iot-06z00et4xyo88k6")
                .setPageSize(PageSize)
                .setCurrentPage(CurrentPage);
        // 复制代码运行请自行打印 API 的返回值
        client.queryProductList(queryProductListRequest);

        QueryProductListResponse resp = client.queryProductList(queryProductListRequest);
        com.aliyun.teaconsole.Client.log(Common.toJSONString(TeaModel.buildMap(resp)));

//        List<Product> productNow=productRepository.findAll();
//        int l=productNow.size();
        List<Product> products=new ArrayList<>();
        int length=resp.body.data.list.productInfo.size();
        for (int i=0;i<length;i++){
            Product product = new Product();
            String productKey = resp.body.data.list.productInfo.get(i).productKey;
            String productName = resp.body.data.list.productInfo.get(i).productName;
            product.setProductName(productName);
            product.setProductKey(productKey);
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            products.add(product);
//            System.out.println(product);
//            productRepository.save(product);
        }
//        for (int i=0;i<l;i++){
//
//        }
//        productRepository.findProductsByProductKey("1");

        return products;
    }

}


