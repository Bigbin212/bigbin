package com.bigbincome.bigbin.service;

import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.dao.BZUserDao;
import com.bigbincome.bigbin.model.BZUser;
import com.bigbincome.bigbin.common.util.DESUtil;
import com.bigbincome.bigbin.common.util.DateUtils;
import com.bigbincome.bigbin.common.util.IpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//@Log4j2
@Transactional
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

    public List<BZUser> findAll () {
        return bzUserDao.findAll();
    }

    /**
     * 这边使用like 需要自己手动拼上%否则按全文匹配查询
     * @param username
     * @return
     */
    public List<BZUser> findAllByUsernameLike(String username){
        StringBuilder stringBuilder = new StringBuilder();
        if(!StringUtil.isNullOrEmpty(username)){
            username = String.valueOf(stringBuilder.append("%").append(username).append("%"));
        }
        return bzUserDao.findAllByUsernameLike(username);
    };

    /**
     * 这边的Containing就是like查询自动加上%%
     * @param username
     * @return
     */
    public List<BZUser> findAllByUsernameContaining(String username){
        return bzUserDao.findAllByUsernameContaining(username);
    };

    /**
     * 分页查询
     * @param jsonObject
     * @return
     */
    public Map<String, Object> findAllPage(ObjectNode jsonObject){
        Page<BZUser> resultPage = bzUserDao.findAllPage(jsonObject);
        Long total = resultPage.getTotalElements();//数据总数
        Map<String , Object> resultMap = new HashMap<>();
        resultMap.put("total" , total);
        resultMap.put("data" , resultPage.getContent());
        return resultMap;
    }

    /**
     * 新增或者修改
     * save 先保存在内存中->发flush或者commit命令
     * saveAndFlush 方法立马提交生效
     * @param BZUser
     * @param request
     */
    public void insertMessage(BZUser BZUser, HttpServletRequest request){
        BZUser = initModel(BZUser,request);
//        bzUserDao.save(BZUser);
        bzUserDao.saveAndFlush(BZUser);
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
     * @param BZUser
     * @param request
     */
    public void updateMessage(BZUser BZUser, HttpServletRequest request) {
        BZUser = initModel(BZUser,request);
        bzUserDao.updateMessage(BZUser);
    }

    /**
     * 实体类初始化
     * @param BZUser
     * @param request
     * @return
     */
    public BZUser initModel(BZUser BZUser, HttpServletRequest request){
       /* if(StringUtil.isNullOrEmpty(BZUser.getXlh())){
            BZUser.setXlh(UUID.randomUUID().toString());
        }*/
        if(!StringUtil.isNullOrEmpty(BZUser.getPassword())){
            BZUser.setPassword(DESUtil.encryptBasedDes(BZUser.getPassword()));
        }else{
            BZUser.setPassword(DESUtil.encryptBasedDes(DEFAULT_PASSWORD));
        }
        log.info(DateUtils.formatDate2String(new Date(),null));
        BZUser.setIp(IpUtil.getIpAddr(request));
        if(StringUtil.isNullOrEmpty(DateUtils.formatDate2String(BZUser.getZcsj(),null))){
            BZUser.setZcsj(new Date());
        }
        if(StringUtil.isNullOrEmpty(BZUser.getYhqx())){
            BZUser.setYhqx(DEFAULT_ONE);
        }
        return BZUser;
    }

    /**
     * 批量新增或修改
     * @param list1
     */
    public void insertPatch(List<BZUser> list1) {
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
    public JsonNode queryByPage(int pageNo, int pageSize, String userName, String zcsjStart, String zcsjEnd) {
        ObjectNode jsonObject = JsonUtils.object();
        List<BZUser> result = null;
        long total = 0;
        List<Sort.Order> orderList = new ArrayList<>();
        // 构造自定义查询条件
        Specification<BZUser> queryCondition = new Specification<BZUser>() {
            @Override
            public Predicate toPredicate(Root<BZUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        //尝试多个排序条件 仅作测试用
        orderList.add(new Sort.Order(Sort.Direction.ASC,"xlh"));
        orderList.add(new Sort.Order(Sort.Direction.DESC,"zcsj"));
        Sort sort = new Sort(orderList);
        // 分页的话按创建时间降序
        try {
            if(pageNo == 0 && pageSize == 0){
                //直接根据查询条件查全部的数据
                result = bzUserDao.findAll(queryCondition,sort);
            }else{
                //根据相关的查询条件以及分页相关的信息
                result = bzUserDao.findAll(queryCondition, PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "zcsj"))).getContent();
            }
            //直接统计符合条件的数据的个数
            total = bzUserDao.count(queryCondition);
            jsonObject.set("list",JsonUtils.toJson(result));
            jsonObject.put("total",total);
        } catch (Exception e) {
            log.error("--queryByPage-- error : ", e);
        }

        return jsonObject;
    }

    /**
     * 批量删除
     * @param list
     */
    public void deleteUserList(List<String> list) {
        bzUserDao.deleteAllByXlhIn(list);
    }
}
