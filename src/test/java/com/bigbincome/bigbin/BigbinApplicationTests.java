package com.bigbincome.bigbin;

import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.config.UnitTaskConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

        //List转Map
        Map<String, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getTaskId,t->t,(o1, o2)->o2));
        System.out.println(taskMap);

        //Map遍历
        taskMap.forEach((k,v)->{
            System.out.println(k + "_" + v.getTaskName());
        });

        //List转List
        System.out.println(list.stream().map(l->TaskTo.of(l)).collect(Collectors.toList()));

        String json = "{\n" + "	\"files\": [\"ASW_labor_999.txt\"]\n" + "}";

        ObjectNode o = (ObjectNode) JsonUtils.from(json);

        String[] files = JsonUtils.to(o.get("files"), String[].class);

        System.out.println(Arrays.toString(files));

        System.out.println(JsonUtils.get(o, "files"));

        o.remove("files");

        System.out.println(o);

        System.out.println(JsonUtils.remove(o, "xyz", "abc", "files", "ooo"));

        System.out.println(unitTaskConfig.getConfigs());

        System.out.println(unitTaskConfig.getTask("11000078","46994b87-a1f1-441e-93d1-c31a5baf6a2e"));

        unitTaskConfig.get("11000078").map(m -> m.get("46994b87-a1f1-441e-93d1-c31a5baf6a2e")).map(m->{
            System.out.println(m.get("cph"));
            System.out.println(m.get("rate"));
           return null;
        });

        String nodestr = "[{\"unitId\":\"865af4cb-e348-4533-9302-63895b0bba27\",\"tasks\":[{\"taskId\":\"dccc7375-90ba-48ce-bf1e-80903dbc6dbc\",\"taskName\":\"用户安全治理\",\"color\":\"171,184,195,1\"}]}]";
        ArrayNode nodesArray = (ArrayNode) JsonUtils.from(nodestr);
        System.out.println(nodesArray);
    }
}
