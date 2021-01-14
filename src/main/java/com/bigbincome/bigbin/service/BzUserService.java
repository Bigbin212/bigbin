package com.bigbincome.bigbin.service;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.dao.BZUserDao;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.util.DESUtil;
import com.bigbincome.bigbin.util.DateUtils;
import com.bigbincome.bigbin.util.IpUtil;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Service
public class BzUserService {

    private static final String DEFAULT_PASSWORD = "88888888";

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

    /**
     * 新增或者修改
     * @param bzUserEntity
     * @param request
     */
    public void insertMessage(BZUserEntity bzUserEntity, HttpServletRequest request){
        bzUserEntity = initModel(bzUserEntity,request);
        bzUserDao.save(bzUserEntity);
    }

    /**
     * 根据xlh删除数据
     * @param xlh
     */
    public void deleteUser(String xlh) {
        bzUserDao.deleteByXlh(xlh);
    }

    /**
     * 测试实体类的局部更新
     * @param bzUserEntity
     * @param request
     */
    public void updateMessage(BZUserEntity bzUserEntity, HttpServletRequest request) {
        bzUserEntity = initModel(bzUserEntity,request);
        bzUserDao.updateMessage(bzUserEntity);
    }

    public BZUserEntity initModel(BZUserEntity bzUserEntity, HttpServletRequest request){
        if(StringUtil.isNullOrEmpty(bzUserEntity.getXlh())){
            bzUserEntity.setXlh(UUID.randomUUID().toString());
        }
        if(!StringUtil.isNullOrEmpty(bzUserEntity.getPassword())){
            bzUserEntity.setPassword(DESUtil.encryptBasedDes(bzUserEntity.getPassword()));
        }else{
            bzUserEntity.setPassword(DESUtil.encryptBasedDes(DEFAULT_PASSWORD));
        }
        System.out.println(DateUtils.formatDate2String(new Date(),null));
        bzUserEntity.setIp(IpUtil.getIpAddr(request));
        if(StringUtil.isNullOrEmpty(DateUtils.formatDate2String(bzUserEntity.getZcsj(),null))){
            bzUserEntity.setZcsj(new Date());
        }
        return bzUserEntity;
    }
}
