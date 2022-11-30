## Hướng dẫn đặt lịch trong spring boot
#### Chúng ta sẽ xử dụng annotation @Scheduled
Đầu tiên, chúng ta phải thêm annotation `@EnableScheduling` để đảm bảo rằng trình thực thi tác vụ đã được tạo.
Nếu không, không coponent nào có thể xử dụng scheduled
