package com.bigbincome.bigbin.task;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class TestTask {
    @Scheduled(cron = "0/5 * * * * ?")
    public void Rll(){
        System.out.println("ce shi");
    }
}
