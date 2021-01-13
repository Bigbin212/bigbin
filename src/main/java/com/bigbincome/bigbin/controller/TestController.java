package com.bigbincome.bigbin.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.bigbincome.bigbin.model.BZUserEntity;
import com.bigbincome.bigbin.service.BzUserService;
import com.bigbincome.bigbin.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 这里的@RestController   相当于@ResponseBody + @Controller
 */
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class TestController {

    @Autowired
    BzUserService bzUserService;

    @CrossOrigin
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
//        log.debug(ip);
        return ip;
    }

    @CrossOrigin
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public Object searchForm(@RequestParam("username") String username){
        JSONObject jsonObject = new JSONObject();
        List<BZUserEntity> list = bzUserService.findByUserName(username);
        jsonObject.put("result",list);
        return  jsonObject;
    }

    /**
     * 测试分页查询用户信息
     * @param name
     * @param pageSize
     * @param pageNo
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public Map<String , Object> findAll(@RequestParam("name") String name,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        if (StringUtils.isEmpty(pageSize)){
            jsonObject.put("pageSize" , 10);
        }else{
            jsonObject.put("pageSize",pageSize);
        }
        if (StringUtils.isEmpty(pageNo)){
            jsonObject.put("pageNo" , 1);
        }else{
            jsonObject.put("pageNo",pageNo);
        }
        Map<String , Object> resultMap = bzUserService.findAllPage(jsonObject);
        return resultMap;
    }

    /**
     * 新增用户
     * @param bzUserEntity
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping(value="/addUser",method = RequestMethod.POST)
    public Object insertMapping(@RequestBody BZUserEntity bzUserEntity,HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success",true);
        try{
            bzUserService.insertMessage(bzUserEntity,request);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }
        return jsonObject;
    }
}
