package com.bigbincome.bigbin.service;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.dao.TbUserDao;
import com.bigbincome.bigbin.model.TbUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbUserService {
    @Autowired
    TbUserDao tbUserDao;

    public List<TbUserEntity> findAll () {
        return tbUserDao.findAll();
    }

    public Map<String, Object> findAllPage(JSONObject jsonObject){
        Page<TbUserEntity> resultPage = tbUserDao.findAllPage(jsonObject);
        Long total = resultPage.getTotalElements();//数据总数
        Map<String , Object> resultMap = new HashMap<>();
        resultMap.put("total" , total);
        resultMap.put("data" , resultPage.getContent());
        return resultMap;
    }
}
