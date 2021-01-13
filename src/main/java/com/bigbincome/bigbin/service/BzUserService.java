package com.bigbincome.bigbin.service;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.dao.BZUserDao;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

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

    public void insertMessage(BZUserEntity bzUserEntity, HttpServletRequest request){
        bzUserEntity.setXlh(UUID.randomUUID().toString());
        bzUserEntity.setIp(IpUtil.getIpAddr(request));
        bzUserEntity.setZcsj(new Timestamp(new Date().getTime()));
        bzUserDao.save(bzUserEntity);
    }
}
