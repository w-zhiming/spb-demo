package com.demo.spb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

/**
 * @author Bill Wang
 * @date 2023/10/31 10:56
 */

@EnableScheduling
@Component
@Slf4j
public class TaskDemo {
    private static DateTimeFormatter pattern= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 每秒钟执行一次，以空格分隔
     * cron表达式：Seconds Minutes Hours DayOfMonth Month DayOfWeek [Year]
     */
    @Scheduled(cron="0 */2 * * * ?")
    public void cron(){
        LocalDateTime now= LocalDateTime.now();
        System.out.println("spring task 这是定时任务，时间是："+pattern.format(now));
    }

    //@Scheduled(cron="0 */2 * * * ?")
    public void readUrl(){
        LocalDateTime now= LocalDateTime.now();
        System.out.println("spring task 这是定时任务，时间是："+pattern.format(now));

        // 同步
        // 1.创建HttpClient对象。
        var client = HttpClient.newHttpClient();
        // 2.创建请求对象：request,封装请求地址和请求方式get.
        var request = HttpRequest.newBuilder().uri(URI.create("http://api.k780.com:88?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json"))
                        .GET().build();
        // 3.使用HttpClient对象发起request请求。得到请求响应对象response
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 4.得到响应的状态码信息
        System.out.println(response.statusCode());
        // 5.得到响应的数据信息输出
        System.out.println(response.body());

// 异步
        // 1.创建HttpClient对象。
        var client1 = HttpClient.newHttpClient();
        // 2.创建请求对象：request,封装请求地址和请求方式get.
        var request1 = HttpRequest.newBuilder().uri(URI.create("http://api.k780.com:88?app=life.time&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json"))
                        .GET().build();
        // 3.使用HttpClient对象发起request异步请求。得到请求响应对象future
        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(request1, HttpResponse.BodyHandlers.ofString());
        // 4.监听future对象的响应结果，并通过join方法进行异步阻塞式等待。
        future.whenComplete((resp ,ex) -> {
            if(ex != null ){
                ex.printStackTrace();
            } else{
                System.out.println(resp.statusCode());
                System.out.println(resp.body());
            }
        }).join();
// future.thenApply(t -> t.body()).thenAccept(System.out::println)
    }
}
