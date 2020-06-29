package com.bigbincome.bigbin.dao;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.model.TbUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.persistence.criteria.Predicate;
import java.util.*;

public interface TbUserDao extends JpaRepository<TbUserEntity, Long>, JpaSpecificationExecutor<TbUserEntity>{
    List<TbUserEntity> findAll();

    default Page<TbUserEntity> findAllPage(JSONObject jsonObject){
        Pageable pageable = new PageRequest(jsonObject.getInteger("pageNo") - 1 ,jsonObject.getInteger("pageSize") , new Sort(Sort.Direction.DESC, "updatedTime"));
        return this.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            //附加的查询条件
            if (!StringUtils.isEmpty(jsonObject.getString("name"))){
                list.add(cb.like(root.get("name") , "%"+jsonObject.getString("name")+"%"));
            }
            Predicate[] p = new Predicate[list.size()];
            query.where(cb.and(list.toArray(p)));
            return query.getRestriction();
        } , pageable);
    }
}
