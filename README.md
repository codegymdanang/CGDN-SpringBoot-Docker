# CGDN-SpringBoot-Docker
## Bước 1 : Cài đặt Docker trên máy của mình
+ Vào trang web sau để tải docker về máy https://docs.docker.com/install/ <br>
    + Kiểm tra docker đã cài thành công chưa bằng lệnh <br>
    + Gõ docker --version <br>
    + Ta sẽ thấy phiên bản docker đang có trên máy : Docker version 18.03.1-ce, build 9ee9f40 <br>
+ Chạy test thử docker Helloworld có chạy được ko <br>
    + Gõ docker run hello-world //Vì chưa có docker helloworld trên máy của mình nên nó sẽ lên Docker Hub tải về <br>
    + Ta sẽ thấy chữ Hello from Docker! . Như vậy ta đã cài thành công <br>
## Bước 2 : Tạo file docker trên dựa án của mình với nội dụng như sau
// Alpine Linux with OpenJDK JRE <br>
FROM openjdk:8-jre-alpine // Cài đặt JDK cho container . Mình cài từ openjdk <br>
//copy WAR into image <br>
COPY spring-boot-app-0.0.1-SNAPSHOT.war /app.war //Mình copy file war từ folder dự án và đổi tên thành app.war <br>
//run application with this command line  <br>
CMD ["/usr/bin/java", "-jar", "-Dspring.profiles.active=default", "/app.war"] // Run command để chạy file jar như bình thường <br>

## Bước 3 : Cấu hình maven để sinh file war vào trong folder docker <br>

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
