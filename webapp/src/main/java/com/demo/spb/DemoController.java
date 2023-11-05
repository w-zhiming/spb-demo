package com.demo.spb;

import com.demo.spb.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {


    @GetMapping(value = "/hello")
    public CommonResult<String> hello(@RequestParam(defaultValue = "unknown") String name) {
        return CommonResult.success("OK", "hello," + name);
    }

    @PostMapping(value = "/send")
    public CommonResult<String> send(@RequestBody HashMap<String, Object> map) {
        return CommonResult.success("OK", "send:" + map.get("name").toString());
    }

    @GetMapping(value = "/getDataSync")
    public CommonResult<String> getDataSync() {

        String result = "";
        var url = "https://118.89.204.198/resolv?host=www.zhihu.com&os_type=web";

        // 同步
        // 1.创建HttpClient对象。
        var client = HttpClient.newHttpClient();
        // 2.创建请求对象：request,封装请求地址和请求方式get.
        var request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        // 3.使用HttpClient对象发起request请求。得到请求响应对象response

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 4.得到响应的状态码信息
            System.out.println(response.statusCode());
            // 5.得到响应的数据信息输出

            result = response.body();
            System.out.println(response.body());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return CommonResult.success("OK", result);

    }

    @GetMapping(value = "/getDataAsync")
    public CommonResult<String> getDataAsync() {

        AtomicReference<String> result = new AtomicReference<>("");
        var url = "https://118.89.204.198/resolv?host=www.zhihu.com&os_type=web";

        // 异步
        // 1.创建HttpClient对象。
        var client = HttpClient.newHttpClient();
        // 2.创建请求对象：request,封装请求地址和请求方式get.
        var request = HttpRequest.newBuilder().uri(URI.create(url))
                .GET().build();
        // 3.使用HttpClient对象发起request异步请求。得到请求响应对象future
        CompletableFuture<HttpResponse<String>> future =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        // 4.监听future对象的响应结果，并通过join方法进行异步阻塞式等待。
        future.whenComplete((resp, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
            } else {
                System.out.println(resp.statusCode());
                result.set(resp.body());
                System.out.println(resp.body());
            }
        }).join();

        return CommonResult.success("OK", result.get());

    }

}
