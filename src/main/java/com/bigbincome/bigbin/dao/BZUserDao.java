package com.bigbincome.bigbin.dao;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.model.BZUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;

public interface BZUserDao extends JpaRepository<BZUser, Long>, JpaSpecificationExecutor<BZUser>{

    List<BZUser> findAll();

    //模糊查询需要手动加%%
    List<BZUser> findAllByUsernameLike(String username);
    //模糊查询不需要手动加%%
    List<BZUser> findAllByUsernameContaining(String username);

    default Page<BZUser> findAllPage(JSONObject jsonObject){
        Pageable pageable = new PageRequest(jsonObject.getInteger("pageNo") - 1 ,jsonObject.getInteger("pageSize") , new Sort(Sort.Direction.DESC, "xlh"));
        return this.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            //附加的查询条件
            if (!StringUtils.isEmpty(jsonObject.getString("name"))){
                list.add(cb.like(root.get("username") , "%"+jsonObject.getString("name")+"%"));
            }
            Predicate[] p = new Predicate[list.size()];
            query.where(cb.and(list.toArray(p)));
            return query.getRestriction();
        } , pageable);
    }

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="delete from b_z_user where xlh = :xlh",nativeQuery = true)
    int deleteByXlh(@Param("xlh") String xlh);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value="update b_z_user set username = :#{#BZUser.username} ,email = :#{#BZUser.email} ," +
            "ip = :#{#BZUser.ip} ,password = :#{#BZUser.password} where xlh = :#{#BZUser.xlh}",nativeQuery = true)
    void updateMessage(BZUser BZUser);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from b_z_user where xlh in (:xlhs)",nativeQuery = true)
    void deleteByXlhList(@Param("xlhs") List<String> xlhs);

}
