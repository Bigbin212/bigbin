package com.bigbincome.bigbin.controller;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 这里的@RestController   相当于@ResponseBody + @Controller
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String test(){
        return "卜憨憨";
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Object searchForm(@RequestParam("username") String username){
        JSONObject jsonObject = new JSONObject();
        List<BZUserEntity> list = testService.findByUserName(username);
        jsonObject.put("result",list);
        return  jsonObject;
    }

}
