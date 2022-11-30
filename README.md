## Hướng dẫn đặt lịch trong spring boot
#### Chúng ta sẽ xử dụng annotation @Scheduled

Đầu tiên, chúng ta phải thêm annotation `@EnableScheduling` vào file `Application` để đảm bảo rằng trình thực thi tác vụ đã được tạo.
Nếu không, không coponent nào có thể xử dụng scheduled

#### ScheduleDemoApplication 
```java
@SpringBootApplication
@EnableScheduling
public class ScheduleDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScheduleDemoApplication.class, args);
    }
}
```

Hai `rules` cho một method `@Scheduled`
```
- Là một hàm void, không trả về bất cứ giá trị nào
- Là một hàm không cho phép bất kì một parameters nào
```

#### Chúng ta cần quan tâm đến 4 thuộc tính chính trong @Scheduled
#### FixedRate
Khoảng cách thời gian giữa các lần chạy method
```java
@Component
public class ScheduleComponent {

    Logger logger = LoggerFactory.getLogger(ScheduleComponent.class);
    
    @Scheduled(fixedRate = 2000)
    public void getCurrentTimeWithFixedRate() {
        logger.info("Current time is " + new Date());
    }
}
```
Output: 
```java
Current time is Wed Nov 30 10:21:08 ICT 2022
Current time is Wed Nov 30 10:21:10 ICT 2022
Current time is Wed Nov 30 10:21:12 ICT 2022
Current time is Wed Nov 30 10:21:14 ICT 2022
```
Như vậy, ta có thể thấy cứ mỗi 2s, method sẽ được gọi đến một lần. <br/>
FixedRate sẽ không quan tâm đến method chạy hết bao lâu và đã hoàn thành chưa, nó chỉ quan tâm đến nó sẽ gọi lại method đấy trong bao lâu.

#### FixedDelay
Khoảng cách thời gian giữa các lần chạy khi một method đã hoàn thành
```java
@Component
public class ScheduleComponent {

    Logger logger = LoggerFactory.getLogger(ScheduleComponent.class);

    @Scheduled(fixedDelay = 2000)
    public void getCurrentTimeWithFixedDelay() throws InterruptedException {
        logger.info("Current time is " + new Date());
        Thread.sleep(1000);
    }
}
```
Output:
```java
Current time is Wed Nov 30 10:28:46 ICT 2022
Current time is Wed Nov 30 10:28:49 ICT 2022
Current time is Wed Nov 30 10:28:53 ICT 2022
Current time is Wed Nov 30 10:28:56 ICT 2022
```
Ví dụ ở trên, method cần mất 1s để hoàn thành. <br/>
Ngược lại với FixedRate, FixedDelay sẽ đợi method hoàn thành rồi mới quan tâm đến việc sẽ gọi lại method trong bao lâu. <br/>
Vì vậy, ta thấy được cứ mỗi 3s, method sẽ được gọi đến một lần. <br/>

##### InitialDelay
Thời gian delay cho lần chạy đầu tiên một method
##### Cron
Lên lịch trình cụ thể cho một method
