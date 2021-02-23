package com.bigbincome.bigbin;

import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.config.UnitTaskConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Month;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class BigbinApplicationTests {

    @Autowired
    UnitTaskConfig unitTaskConfig;

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
        ArrayNode arrayNode = new ObjectMapper().createArrayNode();
        System.out.println("arrayNode: "+arrayNode);
        System.out.println(nodesArray);

        //iterator 遍历删除
        Iterator<JsonNode> iterator = nodesArray.iterator();
        while (iterator.hasNext()){
            JsonNode jsonNode = iterator.next();
            if("865af4cb-e348-4533-9302-63895b0bba27".equalsIgnoreCase(jsonNode.get("unitId").textValue())){
                System.out.println(jsonNode);
            }
        }

        System.out.println(WorkingDays.get(12));

        System.out.println(Month.of(Integer.valueOf("5")));



    }
}
