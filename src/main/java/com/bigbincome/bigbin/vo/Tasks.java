package com.bigbincome.bigbin.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tasks {
    private String id;
    private String name;

    public static Tasks of(ScheduleTasks s){
        return Tasks.builder().id(s.getTaskId()).name(s.getTaskName()).build();
    }
}
