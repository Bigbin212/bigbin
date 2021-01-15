package com.bigbincome.bigbin.service;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.dao.BZUserDao;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.util.DESUtil;
import com.bigbincome.bigbin.util.DateUtils;
import com.bigbincome.bigbin.util.IpUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//@Log4j2
@Service
public class BzUserService {

    private static final String DEFAULT_PASSWORD = "88888888";
    public static final String DEFAULT_ONE = "1";

    protected Logger log = LoggerFactory.getLogger(getClass());
    /*@Value("88888888")
    String DEFAULT_PASSWORD;
    @Value("1")
    String DEFAULT_ONE;*/

    @Autowired
    BZUserDao bzUserDao;

    public List<BZUserEntity> findAll () {
        return bzUserDao.findAll();
    }

    public List<BZUserEntity> findByUserName(String username){
        return bzUserDao.findAllByUsername(username);
    };

    /**
     * 分页查询
     * @param jsonObject
     * @return
     */
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

    /**
     * 实体类初始化
     * @param bzUserEntity
     * @param request
     * @return
     */
    public BZUserEntity initModel(BZUserEntity bzUserEntity, HttpServletRequest request){
        if(StringUtil.isNullOrEmpty(bzUserEntity.getXlh())){
            bzUserEntity.setXlh(UUID.randomUUID().toString());
        }
        if(!StringUtil.isNullOrEmpty(bzUserEntity.getPassword())){
            bzUserEntity.setPassword(DESUtil.encryptBasedDes(bzUserEntity.getPassword()));
        }else{
            bzUserEntity.setPassword(DESUtil.encryptBasedDes(DEFAULT_PASSWORD));
        }
        log.info(DateUtils.formatDate2String(new Date(),null));
        bzUserEntity.setIp(IpUtil.getIpAddr(request));
        if(StringUtil.isNullOrEmpty(DateUtils.formatDate2String(bzUserEntity.getZcsj(),null))){
            bzUserEntity.setZcsj(new Date());
        }
        if(StringUtil.isNullOrEmpty(bzUserEntity.getYhqx())){
            bzUserEntity.setYhqx(DEFAULT_ONE);
        }
        return bzUserEntity;
    }

    /**
     * 批量新增或修改
     * @param list1
     */
    public void insertPatch(List<BZUserEntity> list1) {
        bzUserDao.saveAll(list1);
    }


    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param userName
     * @param zcsjStart
     * @param zcsjEnd
     * @return
     */
    public JSONObject queryByPage(int pageNo, int pageSize, String userName,String zcsjStart,String zcsjEnd) {
        JSONObject jsonObject = new JSONObject();
        List<BZUserEntity> result = null;
        long total = 0;
        // 构造自定义查询条件
        Specification<BZUserEntity> queryCondition = new Specification<BZUserEntity>() {
            @Override
            public Predicate toPredicate(Root<BZUserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                if (!StringUtil.isNullOrEmpty(userName)) {
//                    predicateList.add(criteriaBuilder.equal(root.get("userName"), userName));
                    predicateList.add(criteriaBuilder.like(root.get("username") , "%"+userName+"%"));
                }
                if (!StringUtil.isNullOrEmpty(zcsjStart) && !StringUtil.isNullOrEmpty(zcsjEnd)) {
                    predicateList.add(criteriaBuilder.between(root.get("zcsj"), zcsjStart, zcsjEnd));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

        // 分页的话按创建时间降序
        try {
            if(pageNo == 0 && pageSize == 0){
                //直接根据查询条件查全部的数据
                result = bzUserDao.findAll(queryCondition,Sort.by(Sort.Direction.DESC, "zcsj"));
            }else{
                //根据相关的查询条件以及分页相关的信息
                result = bzUserDao.findAll(queryCondition, PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "zcsj"))).getContent();
            }
            //直接统计符合条件的数据的个数
            total = bzUserDao.count(queryCondition);
            jsonObject.put("list",result);
            jsonObject.put("total",total);
        } catch (Exception e) {
            log.error("--queryByPage-- error : ", e);
        }

        return jsonObject;
    }
}
