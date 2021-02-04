package com.bigbincome.bigbin;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
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


    @Test
    public void contextLoads() {
        List<Task> list = new ArrayList<>();
        list.add(Task.builder().taskId("1").taskName("NAME1").build());
        list.add(Task.builder().taskId("1").taskName("NAME2").build());
        list.add(Task.builder().taskId("3").taskName("NAME3").build());
        list.add(Task.builder().taskId("4").taskName("NAME4").build());

        //List转Map
        Map<String, String> map = list.stream().collect(Collectors.toMap(Task::getTaskId,Task::getTaskName,(o1, o2)->o2));

        Map<String, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getTaskId,t->t,(o1, o2)->o2));
        System.out.println(taskMap);
        //List转List
        System.out.println(list.stream().map(l->TaskTo.of(l)).collect(Collectors.toList()));


        String json = "{\n" + "	\"files\": [\"ASW_labor_999.txt\"]\n" + "}";

        ObjectNode o = (ObjectNode) com.bigbincome.bigbin.common.util.JsonUtils.from(json);

        String[] files = com.bigbincome.bigbin.common.util.JsonUtils.to(o.get("files"), String[].class);

        System.out.println(Arrays.toString(files));

        System.out.println(com.bigbincome.bigbin.common.util.JsonUtils.get(o, "files"));

        o.remove("files");

        System.out.println(o);

        System.out.println(com.bigbincome.bigbin.common.util.JsonUtils.remove(o, "xyz", "abc", "files", "ooo"));
    }
}
