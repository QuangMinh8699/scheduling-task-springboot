package com.tqm.schedule.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleComponent {

    Logger logger = LoggerFactory.getLogger(ScheduleComponent.class);

    @Scheduled(fixedRate = 2000)
    public void getCurrentTimeWithFixedRate() {
        logger.info("Current time is " + new Date());
    }


    @Scheduled(fixedDelay = 2000)
    public void getCurrentTimeWithFixedDelay() throws InterruptedException {
        logger.info("Current time is " + new Date());
        Thread.sleep(1000);
    }


    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void getCurrentTimeWithFixedRateAndInitialDelay(){
        logger.info("Current time is " + new Date());
    }

    @Scheduled(cron = "*/2 * * * * *")
    public void getCurrentTimeWithCron() {
        logger.info("Current time is " + new Date());
    }
}
