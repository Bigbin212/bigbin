package com.bigbincome.bigbin.controller;

import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.service.TestService;
import com.bigbincome.bigbin.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 这里的@RestController   相当于@ResponseBody + @Controller
 */
@RestController
@Slf4j
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService testService;

    @CrossOrigin
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        log.debug(ip);
        return ip;
    }

    @CrossOrigin
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Object searchForm(@RequestParam("username") String username){
        JSONObject jsonObject = new JSONObject();
        List<BZUserEntity> list = testService.findByUserName(username);
        jsonObject.put("result",list);
        return  jsonObject;
    }

}
