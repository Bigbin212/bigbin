package com.bigbincome.bigbin.service;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.dao.BZUserDao;
import com.bigbincome.bigbin.model.BZUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BzUserService {
    @Autowired
    BZUserDao bzUserDao;

    public List<BZUserEntity> findAll () {
        return bzUserDao.findAll();
    }

    public List<BZUserEntity> findByUserName(String username){
        return bzUserDao.findAllByUsername(username);
    };

    public Map<String, Object> findAllPage(JSONObject jsonObject){
        Page<BZUserEntity> resultPage = bzUserDao.findAllPage(jsonObject);
        Long total = resultPage.getTotalElements();//数据总数
        Map<String , Object> resultMap = new HashMap<>();
        resultMap.put("total" , total);
        resultMap.put("data" , resultPage.getContent());
        return resultMap;
    }
}
