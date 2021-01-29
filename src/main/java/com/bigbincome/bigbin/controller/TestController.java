package com.bigbincome.bigbin.controller;

import com.alibaba.druid.util.StringUtils;
import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.model.BZUser;
import com.bigbincome.bigbin.service.BzUserService;
import com.bigbincome.bigbin.common.util.DESUtil;
import com.bigbincome.bigbin.common.util.IpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 这里的@RestController   相当于@ResponseBody + @Controller
 */
@RestController
//@Log4j2
@RequestMapping(value = "/api")
public class TestController {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BzUserService bzUserService;

    @CrossOrigin
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String test(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        log.debug(ip);
        return ip;
    }

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public JsonNode searchForm(@RequestParam(value = "username" ,required = false) String username){
        ObjectNode jsonObject = JsonUtils.object();
        List<BZUser> list = bzUserService.findAllByUsernameContaining(username);//bzUserService.findAllByUsernameLike(username);
        jsonObject.set("result", JsonUtils.toJson(list));
        return  jsonObject;
    }

    /**
     * 查询所有参数
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/searchAll",method = RequestMethod.GET)
    public JsonNode searchAll(){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        try{
            jsonObject.set("list",JsonUtils.toJson(bzUserService.findAll()));
        }catch(Exception e){
            jsonObject.put("success",false);
        }
        return jsonObject;
    }

    /**
     * 测试分页查询用户信息
     * @param name
     * @param pageSize
     * @param pageNo
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/searchMessage",method = RequestMethod.GET)
    public Map<String , Object> findAll(@RequestParam("name") String name,@RequestParam("pageSize") String pageSize,@RequestParam("pageNo") String pageNo){
        ObjectNode jsonObject = JsonUtils.object();
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
     * 测试分页查询用户信息
     * @param name
     * @param pageSize
     * @param pageNo
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryByPage",method = RequestMethod.GET)
    public Object queryByPage(@RequestParam("name") String name,@RequestParam(value = "pageSize",defaultValue = "10") String pageSize,@RequestParam(value = "pageNo",defaultValue = "1") String pageNo){
        ObjectNode jsonObject = JsonUtils.object();
        List<BZUser> list = new ArrayList<>();
        jsonObject.put("success",true);
        try{
            return bzUserService.queryByPage(Integer.valueOf(pageNo),Integer.valueOf(pageSize),name,null,null);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("message",e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 新增、修改用户
     * RequestMapping：
     *     1、不设置method 默认任何请求方式都接收
     *     2、可以配置多个url映射
     * @param BZUser
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping({"/addUser","/updateUser"})
    public JsonNode insertMapping(@RequestBody BZUser BZUser,HttpServletRequest request){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        try{
            bzUserService.insertMessage(BZUser,request);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 测试实体类的局部更新
     * @param BZUser
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUserMessage",method = RequestMethod.PUT)
    public JsonNode updateMapping(@RequestBody BZUser BZUser,HttpServletRequest request){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        try{
            bzUserService.updateMessage(BZUser,request);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }
        return jsonObject;
    }

    /**
     * 单个删除
     * @param xlh
     * @return
     */
    @RequestMapping(value = "/deleteUser",method = RequestMethod.DELETE)
    public JsonNode deleteUser(@RequestParam("xlh") String xlh){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        if (StringUtil.isNullOrEmpty(xlh)){
            jsonObject.put("success",false);
            jsonObject.put("message","删除主键不可为空！");
        }
        try{
            bzUserService.deleteUser(xlh);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }

        return jsonObject;
    }

    /**
     * 批量删除
     * @param list
     * @return
     */
    @RequestMapping(value = "/deleteUserList",method = RequestMethod.DELETE)
    public JsonNode deleteUserList(@RequestBody List<String> list){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        try{
            bzUserService.deleteUserList(list);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }

        return jsonObject;
    }


    /**
     * 测试批量新增和数据排重
     * @param list
     * @param request
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/addUserList", method = RequestMethod.POST)
    public Object addUserList(@RequestBody List<BZUser> list,HttpServletRequest request){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        List<BZUser> list1 = new ArrayList<BZUser>();
        try{
            list.forEach(e->{
               /* if(StringUtil.isNullOrEmpty(e.getXlh())){
                    e.setXlh(UUID.randomUUID().toString());
                }*/
                e.setIp(IpUtil.getIpAddr(request));
                e.setPassword(DESUtil.encryptBasedDes(e.getPassword()));
            });

            // 多个对象去重
            list1 = list.stream().collect(
                    Collectors.collectingAndThen(
                            Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o->o.getXlh()+ ";" + o.getUsername()))),
                            ArrayList::new
                    )
            );
            log.info(JsonUtils.toJson(list).toString());
            log.info(JsonUtils.toJson(list1).toString());
            bzUserService.insertPatch(list);
        }catch (Exception e){
            jsonObject.put("success",false);
            jsonObject.put("result",e.getMessage());
        }
        jsonObject.set("list",JsonUtils.toJson(list1));
        return jsonObject;
    }

    @CrossOrigin
    @PostMapping(value = "/importExcel")
    public JsonNode importExcel(@RequestParam("file")MultipartFile file){
        ObjectNode jsonObject = JsonUtils.object();
        jsonObject.put("success",true);
        System.out.println("file = " + file.getName());
        return jsonObject;
    }

}
