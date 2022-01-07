package com.example.wxbf.service;


import com.example.wxbf.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    //新增手机类型
    Type savaType(Type type);

    //列出所有类型
    List<Type> listType();

    //更新手机类型
    Type updateType(Long id, Type type);

}
