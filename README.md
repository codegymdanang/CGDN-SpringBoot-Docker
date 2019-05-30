# CGDN-SpringBoot-Docker
### Bước 1 : Cài đặt Docker trên máy của mình
+ Vào trang web sau để tải docker về máy https://docs.docker.com/install/ <br>
    + Kiểm tra docker đã cài thành công chưa bằng lệnh <br>
    + Gõ docker --version <br>
    + Ta sẽ thấy phiên bản docker đang có trên máy : Docker version 18.03.1-ce, build 9ee9f40 <br>
+ Chạy test thử docker Helloworld có chạy được ko <br>
    + Gõ docker run hello-world //Vì chưa có docker helloworld trên máy của mình nên nó sẽ lên Docker Hub tải về <br>
    + Ta sẽ thấy chữ Hello from Docker! . Như vậy ta đã cài thành công <br>
+ Tham khảo thêm các lệnh chạy Docker tại đây <br>
### Docker for beginner
https://github.com/docker/labs/tree/master/beginner

### Bước 2 : Tạo file docker trên dựa án của mình với nội dụng như sau
// Alpine Linux with OpenJDK JRE <br>
FROM openjdk:8-jre-alpine // Cài đặt JDK cho container . Mình cài từ openjdk <br>
//copy WAR into image <br>
COPY spring-boot-app-0.0.1-SNAPSHOT.war /app.war //Mình copy file war từ folder dự án và đổi tên thành app.war <br>
//run application with this command line  <br>
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"] // Run command để chạy file jar như bình thường <br>

### Bước 3 : Cấu hình maven để sinh file war vào trong folder docker <br>

    <plugin>
         <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
            <configuration>
                <mainClass>com.stackify.Application</mainClass>
                <outputDirectory>${project.basedir}/docker</outputDirectory>
            </configuration>
        </execution>
     </executions>
    </plugin>
#### Bước 4:  Build docker image bằng cách gõ lệnh sau <br>
docker build -t spring-boot-app:latest . 
+ Kiểm tra xem đã build được docker chưa bằng lệnh <b>
  + docker images <br>
  + Ta sẽ thấy <br>
    REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE <br>
    spring-boot-app     latest              bde358fcd08f        5 seconds ago       106MB <br>
    openjdk             8-jre-alpine        f7a292bbb70c        2 weeks ago         84.9MB <br>
+ Run docker  gõ lệnh <br>
docker run -d  -p 8080:8080 spring-boot-app:latest  <br>
+ Kiểm tra xem container đã lên chưa  <br>
docker ps <br>
# Phần 2 kết nối ứng dụng với mysql docker. 2 containers riêng biệt

## Để kết nối với mysql ta sẽ start mysql container riêng cho mysql để ứng dụng mình có thể kết nối db <br>
+ Run database từ docker bằng lệnh
    docker run -p 2012:3306 --name mysqldocker -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7.26
        
    + -d : ta chạy mysql ở 1 container khác không cùng với app ở trên. Nó chạy riêng biệt
    + -p : là port để ta có thể truy cập vào mysql. Ở máy local ta có thể truy cập bằng port 2012. <br>
    Nhưng nếu ở máy khác hoặc một ứng dụng khác thì dùng port 3306
    + 5.7.26 : version của mysql
+ Login vào mysql và tạo schema library,có table book có 1 trường là book_name, sau đó thêm dữ liệu  <br>

+ Chỉnh sửa cấu hình database trong file application nếu ta build ra docker và link với  <br>

spring.datasource.driver-class-name=com.mysql.jdbc.Driver <br>
spring.datasource.url=jdbc:mysql://mysqldocker:3306/library <br>
spring.datasource.username=root <br>
spring.datasource.password=123456 <br>

+ Sau khi chạy ứng dụng dưới local đã connect ổn với database . Giờ chúng ta build Spring boot thành docker <br>
    + Chúng ta chạy lại maven install để copy war mới có connect với mysql
    
    docker build -t spring-boot-app:latest . 
    
+ Sau khi chạy xong ta có image của ứng dụng spring boot. Bây giờ chúng ta sẽ start docker container cho ứng dụng <br>
Lần này chúng ta sẽ link ứng dựng của mình tới mysql container bằng lệnh sau : <br>
docker run -t --name springboot-mysql-container --link mysqldocker:mysql -p 8087:8080 spring-boot-app <br>
<br>
–name springboot-mysql-container : tên của docker container sẽ 
<br>
-p 8087:8080 : port 8087 được access từ local container , còn port 8080 sẽ được access từ ngoài 
<br>
–link

Now, because our application Docker container requires a MySQL container, we will link both containers to each other. To do that we use the --link flag. The syntax of this command is to add the container that should be linked and an alias, 
<br>
for example --link mysql-docker-container:mysql, in this case mysql-docker-container is the linked container and mysql is the alias.
<br>
spring-boot-app : tên image mà chúng ta build docker cho spring boot app 

### Chúng ta có thể đưa image của mình lên online (dockerhub) để cho các developer có thể lấy về. Chi tiết cách làm tại đây
https://ropenscilabs.github.io/r-docker-tutorial/04-Dockerhub.html
