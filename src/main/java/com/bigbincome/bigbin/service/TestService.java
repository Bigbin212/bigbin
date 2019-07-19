package com.bigbincome.bigbin.service;

import com.bigbincome.bigbin.dao.TestDao;
import com.bigbincome.bigbin.model.BZUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestDao dao;

    public List<BZUserEntity> findByUserName(String username){
        return dao.findAllByUsername(username);
    };
}
