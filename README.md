## Hướng dẫn đặt lịch trong spring boot
### Chúng ta sẽ xử dụng annotation @Scheduled

Đầu tiên, chúng ta phải thêm annotation `@EnableScheduling` vào file `Application` để đảm bảo rằng trình thực thi tác vụ đã được tạo.
Nếu không, không coponent nào có thể xử dụng scheduled

### ScheduleDemoApplication 
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

### Bốn thuộc tính chính trong @Scheduled
### FixedRate
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

### FixedDelay
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

### InitialDelay
Thời gian delay cho lần chạy đầu tiên một method
```java
@Component
public class ScheduleComponent {

    Logger logger = LoggerFactory.getLogger(ScheduleComponent.class);

    @Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void getCurrentTimeWithFixedRateAndInitialDelay(){
        logger.info("Current time is " + new Date());
    }
}
```
Output:
```java
2022-11-30T10:39:18.177+07:00  INFO 6300 --- [           main] c.tqm.schedule.S...
2022-11-30T10:39:23.185+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
2022-11-30T10:39:25.186+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
2022-11-30T10:39:27.184+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
2022-11-30T10:39:29.180+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
2022-11-30T10:39:31.175+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
```
InitialDelay sẽ delay thời gian gọi method trong lần đầu tiên. <br/>
Ví dụ ở trên, chúng ta sẽ cho InitialDelay 5s. <br/>
Và chúng ta để ý:
```java
2022-11-30T10:39:18.177+07:00  INFO 6300 --- [           main] c.tqm.schedule.S...
2022-11-30T10:39:23.185+07:00  INFO 6300 --- [   scheduling-1] c.t.s.component.S...
```
Trong lần gọi đầu tiền, InitialDelay đã delay 5s trước khi gọi method.

### Cron
Lên lịch trình cụ thể cho một method
```java
@Component
public class ScheduleComponent {

    Logger logger = LoggerFactory.getLogger(ScheduleComponent.class);

    @Scheduled(cron = "*/2 * * * * *")
    public void getCurrentTimeWithCron() {
        logger.info("Current time is " + new Date());
    }
}
```
Output:
```java
Current time is Wed Nov 30 10:51:52 ICT 2022
Current time is Wed Nov 30 10:51:54 ICT 2022
Current time is Wed Nov 30 10:51:56 ICT 2022
Current time is Wed Nov 30 10:51:58 ICT 2022
```
Giả sử chúng ta muốn đặt lịch cụ thể trong một tuẩn, hãy sử dụng cron.<br/>
Tham số cần truyền vào thuộc tính cron là một định dạng chuỗi unix-cron ` (* * * * *)` theo thứ tự là `("min" "hour" "day of the month" "month" "day of the week")` <br/>

##### Các kí tự đặc biệt trong cron:

`*` : tất cả, ví dụ (*) trong "min" có nghĩa là tất cả mọi phút <br/>
`?` : bất cứ lúc nào, ví dụ ta muốn đặt lịch vào ngày ngày "14 hàng tháng", mặc dù là bất kể ngày nào trong tuần. <br/>
      Ta sẽ để (?) trong "day of the week"<br/>
`,` : giá trị, ví dụ (MON, WED, FRI) trong "day of week" có nghĩa là "thứ 2, 4, 6 trong một tuần"<br/>
`-` : phạm vi, ví dụ (1-10) trong "hour" có nghĩa là phạm vi từ "1 giờ đến 10 giờ"<br/>
`/` : gia tăng, ví dụ (5/15) trong "min" có nghĩa là "5, 20, 35, 50 phút trong một giờ"<br/>
`L` : cuối cùng, ví dụng (L) trong "day of the month" có nghĩa là "ngày cuối cùng của tháng đó"<br/>
`W` : ngày thường, ví dụ (10W) trong "day of the month", nó sẽ chọn ngày thường gần ngày 10 hàng tháng, <br/>
      nếu ngày 10 là thứ 7, nó sẽ chọn ngày mùng 9. Nếu ngày 10 là chủ nhật, nó sẽ chọn ngày 11<br/>
`#` : chỉ định lần xuất hiện thứ N, ví dụ "thứ 6 lần 3 của một tháng" có nghĩa là (6#3)<br/>

