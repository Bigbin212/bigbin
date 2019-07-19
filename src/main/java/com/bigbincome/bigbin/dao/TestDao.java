package com.bigbincome.bigbin.dao;

import com.bigbincome.bigbin.model.BZUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface TestDao extends JpaRepository<BZUserEntity,Long> {
     List<BZUserEntity> findAllByUsername(String username) ;
}
