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

#### Chúng ta cần quan tâm đến 4 method chính trong @Scheduled
##### FixedRate
##### FixedDelay
##### InitialDelay
##### Cron
