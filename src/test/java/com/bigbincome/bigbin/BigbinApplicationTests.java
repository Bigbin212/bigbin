package com.bigbincome.bigbin;

import com.bigbincome.bigbin.common.util.DateUtils;
import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.config.UnitTaskConfig;
import com.bigbincome.bigbin.vo.ScheduleResultDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.util.internal.StringUtil;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
class Task{
    private String taskId;
    private String taskName;
}

@Builder
@Data
class TaskTo{
    private String id;
    private String name;

    public static TaskTo of(Task t){
        return TaskTo.builder().id(t.getTaskId()).name(t.getTaskName()).build();
    }
}

@Builder
@Data
class LastPeriodResult{
    private String personId;
    private String personName;
    private  Integer consecutiveRestDay;
    private  Integer consecutiveWorkDay;
    private  Integer consecutiveNightShiftDay;
    private  Integer endTime;
    private  Integer alreadyWorkedTimeFirstWeek;
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class BigbinApplicationTests {

    @Autowired
    UnitTaskConfig unitTaskConfig;

    @Autowired
    private ApplicationContext ctx;

    @Test
    public void contextLoads() {

        List<Task> list = new ArrayList<>();
        list.add(Task.builder().taskId("1").taskName("NAME1").build());
        list.add(Task.builder().taskId("1").taskName("NAME2").build());
        list.add(Task.builder().taskId("3").taskName("NAME3").build());
        list.add(Task.builder().taskId("4").taskName("NAME4").build());

        //List转Map
        Map<String, String> map = list.stream().collect(Collectors.toMap(Task::getTaskId,Task::getTaskName,(o1, o2)->o2));
        System.out.println("List转Map " + map);

        Map<String, String> mapTask = new HashMap<>();
        map.forEach((k,v)->{
            mapTask.put(v,k);
        });
        System.out.println("mapTask " + mapTask);


        //List转Map
        Map<String, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getTaskId,t->t,(o1, o2)->o2));
        System.out.println("taskMap " + taskMap);

        Map<String,List<Task>> map1 = list.stream().collect(Collectors.groupingBy(Task::getTaskId));
        System.out.println(map1);

        /**
         * Map转List
         */
        List<ObjectNode> testList = taskMap.entrySet().stream()
                .map(t-> JsonUtils.object().put("id",t.getKey()).put("name",t.getValue().getTaskName()))
                .collect(Collectors.toList());

        System.out.println("testList " + testList);

        List<ObjectNode> list1 =  taskMap.entrySet()
                .stream().map(t->JsonUtils.object().put(t.getValue().getTaskName(),t.getKey()))
                .collect(Collectors.toList());
        System.out.println("Map转List " + list1);


        //List转List
        List<TaskTo> taskToList = list.stream().map(l->TaskTo.of(l)).collect(Collectors.toList());
        System.out.println("taskToList " + taskToList);

        System.out.println(unitTaskConfig.getTask("11000078","46994b87-a1f1-441e-93d1-c31a5baf6a2e"));

        String nodestr = "[{\"unitId\":\"865af4cb-e348-4533-9302-63895b0bba27\",\"tasks\":[{\"taskId\":\"dccc7375-90ba-48ce-bf1e-80903dbc6dbc\",\"taskName\":\"用户安全治理\",\"color\":\"171,184,195,1\"}]}]";
        ArrayNode nodesArray = (ArrayNode) JsonUtils.from(nodestr);
        JsonNode arrayNode = JsonUtils.toJson(Collections.EMPTY_LIST);
        System.out.println("arrayNode: "+arrayNode);
        System.out.println(nodesArray);

        //iterator 遍历删除
        Iterator<JsonNode> iterator = nodesArray.iterator();
        while (iterator.hasNext()){
            JsonNode jsonNode = iterator.next();
            if("865af4cb-e348-4533-9302-63895b0bba27".equalsIgnoreCase(jsonNode.get("unitId").textValue())){
                System.out.println(jsonNode.get("tasks"));
                iterator.remove();
            }
        }
        System.out.println(nodesArray);

        System.out.println(WorkingDays.get(12));

        System.out.println(Month.of(Integer.valueOf("5")));


        Resource resource = ctx.getResource("classpath:test.json");
        String startDate = "2021-03-01";
        //上月第一天和最后一天
        Date earlyLastMonth =  DateUtils.adjustDateTime(DateUtils.formatString2Date(startDate,DateUtils.DATE_FORMAT_YYYYMMDD),Calendar.MONTH,-1);
        Date endLastMonth = DateUtils.adjustDay(DateUtils.formatString2Date(startDate,DateUtils.DATE_FORMAT_YYYYMMDD),-1);

        try {
            JsonNode jsonNode = JsonUtils.from(resource.getInputStream());
            List<ScheduleResultDetail> scheduleResultDetailList = JsonUtils.toList(jsonNode,ScheduleResultDetail.class);
//            List<LastPeriodResult> lastPeriodResultList = lastPeriodResultList(scheduleResultDetailList,endLastMonth,startDate);
//            System.out.println(JsonUtils.toJson(lastPeriodResultList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(DateUtils.formatString2Date("12:00:00","HH:mm:ss"));

        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(LocalTime.parse("12:00:00"));

        System.out.println(LocalDate.now().getDayOfWeek());
        System.out.println(LocalTime.now().getMinute());
        System.out.println(LocalDateTime.now().getHour());
        System.out.println(new Date().getHours());

        System.out.println(DateUtils.adjustDay(new Date(),-workDayOfWeek(new Date())));

        System.out.println(strReturn("hello world ackerman"));
        System.out.println(new StringBuilder("hello world ackerman").reverse().toString());
    }

    /**
     * 算两个时间的小时差
     * @param endDate
     * @param startDate
     * @return
     */
    private Integer getDatePoor(Date endDate, Date startDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少小时
        long hour = diff % nd / nh;
        return (int) hour;
    }

    /**
     * 算当天之前的工作天数
     * @param date
     * @return
     */
    private Integer workDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        return w - 1;
    }

    /**
     * LastPeriodResult 集合
     * @param scheduleResultDetailList
     * @param endLastMonth
     * @param startDate
     * @return
     */
    private List<LastPeriodResult> lastPeriodResultList (List<ScheduleResultDetail> scheduleResultDetailList,Date endLastMonth,String startDate){
        //按persionId作为key整合数据
        Map<String,List<ScheduleResultDetail>> map = scheduleResultDetailList.stream().collect(Collectors.groupingBy(ScheduleResultDetail::getPersonId));

        List<LastPeriodResult> lastPeriodResultList = new ArrayList<>();
        //上月最后一天的开始时间结束时间
        Date endLastMonthStartTime = DateUtils.getStartOfDay(endLastMonth);
        Date endLastMonthEndTime = DateUtils.getEndOfDay(endLastMonth);

        //上月底在当前月初工作的总天数
        int workDayOfWeek = workDayOfWeek(DateUtils.formatString2Date("2021-03-01",DateUtils.DATE_FORMAT_YYYYMMDD));
        //算出与当前月初同一周周一的日期
        Date mondayWork = DateUtils.adjustDay( DateUtils.formatString2Date("2021-03-01",DateUtils.DATE_FORMAT_YYYYMMDD),-workDayOfWeek);

        map.forEach((k,v)->{
            v = v.stream().sorted((t1,t2)-> t2.getStartTime().compareTo(t1.getStartTime())).collect(Collectors.toList());

            int consecutiveRestDay = 0; //上一个排班周期最后一个连续休息天数
            int consecutiveWorkDay = 0; //上一个排班周期最后一个连续工作天数
            int consecutiveNightShiftDay = 0; //上一个排班周期最后一个连续夜班天数
            int endTime = 0; //上一个排班周期最后一个班结束时间
            int alreadyWorkedTimeFirstWeek = 0; //排班周期内第一周已经排班分钟数

            //排班最后一天的班次开始结束时间
            Date lastPeriodEndTime = DateUtils.formatString2Date(v.get(0).getEndTime(),"yyyy-MM-dd HH:mm");
            Date lastPeriodStartTime = DateUtils.formatString2Date(v.get(0).getStartTime(),"yyyy-MM-dd HH:mm");
            //排班周期内第一周已经排班分钟数
            if(lastPeriodStartTime.after(mondayWork) && workDayOfWeek > 0){
                alreadyWorkedTimeFirstWeek += getDatePoor(lastPeriodEndTime,lastPeriodStartTime)*60;
            }

            //判断当前排班周期最后一天是否有排班
            if (lastPeriodStartTime.after(endLastMonthStartTime)){
                consecutiveWorkDay++;
                //当前排班周期最后一天是否有跨夜班
                if(lastPeriodEndTime.after(endLastMonthEndTime)){
                    endTime = 1440 + 60 * lastPeriodEndTime.getHours();
                    consecutiveNightShiftDay++;
                }else{
                    endTime = 60 * lastPeriodEndTime.getHours();
                }
            }else {//直接算出连续休息天数
                double betweenDate = Math.ceil((double) (endLastMonthStartTime.getTime() - lastPeriodStartTime.getTime())/(60*60*24*1000));
//                System.out.println(v.get(0).getPersonName() + "  " + betweenDate +" " + (double) (endLastMonthStartTime.getTime() - lastPeriodStartTime.getTime())/(60*60*24*1000));
                consecutiveRestDay += betweenDate;
            }

            LastPeriodResult lastPeriodResult = lastPeriodResult(endLastMonth,v,lastPeriodStartTime,
                    lastPeriodEndTime,workDayOfWeek,mondayWork,alreadyWorkedTimeFirstWeek,consecutiveWorkDay,consecutiveNightShiftDay);
            lastPeriodResult.setPersonId(k);
            lastPeriodResult.setPersonName(v.get(0).getPersonName());
            lastPeriodResult.setConsecutiveRestDay(consecutiveRestDay);
            lastPeriodResult.setEndTime(endTime);
            lastPeriodResultList.add(lastPeriodResult);
        });
        return lastPeriodResultList;
    }

    /**
     *计算出几个参数
     * @param endLastMonth
     * @param list
     * @param lastPeriodStartTime
     * @param lastPeriodEndTime
     * @param workDayOfWeek
     * @param mondayWork
     * @param alreadyWorkedTimeFirstWeek
     * @param consecutiveWorkDay
     * @param consecutiveNightShiftDay
     * @return
     */
    private LastPeriodResult lastPeriodResult(Date endLastMonth,List<ScheduleResultDetail> list, Date lastPeriodStartTime,
                                              Date lastPeriodEndTime,int workDayOfWeek,Date mondayWork,int alreadyWorkedTimeFirstWeek,
                                              int consecutiveWorkDay,int consecutiveNightShiftDay){
        String endLastMonthStr = DateUtils.formatDate2String(endLastMonth,DateUtils.DATE_FORMAT_YYYYMMDD);
        //排班周期最后一天
        int maxDay = Integer.valueOf(endLastMonthStr.split("-")[2]);
        for (int i = maxDay-1;i>=1;i--){
            //拼日期 倒序 方便计算
            String day = endLastMonthStr.split("-")[0] + "-" +endLastMonthStr.split("-")[1] + "-";
            if(i<10){
                day = day + "0" + i;
            }else{
                day = day + i;
            }

            int j = maxDay - i;
            Date iDateTime =  DateUtils.formatString2Date(day,DateUtils.DATE_FORMAT_YYYYMMDD);
            Date iStartTime = DateUtils.getStartOfDay(iDateTime);
            Date iEndTime = DateUtils.getEndOfDay(iDateTime);

            if(list.size()>j){
                Date vStartTime = DateUtils.formatString2Date(list.get(j).getStartTime(),"yyyy-MM-dd HH:mm");
                Date vEndTime = DateUtils.formatString2Date(list.get(j).getEndTime(),"yyyy-MM-dd HH:mm");

                if((workDayOfWeek == 0 || vStartTime.before(mondayWork))&& vStartTime.before(iStartTime)){
                    break;
                }

                if(vStartTime.after(mondayWork)){
                    alreadyWorkedTimeFirstWeek += getDatePoor(lastPeriodEndTime,lastPeriodStartTime)*60;
                }

                //实际排班 在某天范围内 并且最后一天是工作天
                if(vStartTime.after(iStartTime) && vStartTime.before(iEndTime) && consecutiveWorkDay > 0){
                    consecutiveWorkDay++;
                    //判断是否是夜班
                    if(vEndTime.after(iEndTime) && consecutiveNightShiftDay > 0){
                        consecutiveNightShiftDay++;
                    }
                }
            }
        }
        return LastPeriodResult.builder()
                .consecutiveWorkDay(consecutiveWorkDay)
                .consecutiveNightShiftDay(consecutiveNightShiftDay)
                .alreadyWorkedTimeFirstWeek(alreadyWorkedTimeFirstWeek)
                .build();
    }

    private String strReturn(String s){
        String res = "";
        if (StringUtil.isNullOrEmpty(s)){
            return s;
        }
        String [] sArray = s.split("");
        for (int i = sArray.length-1;i>=0;i--){
            res += sArray[i];
        }
        return res;
    }
}
