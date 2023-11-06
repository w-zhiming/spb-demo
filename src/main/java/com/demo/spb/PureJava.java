package com.demo.spb;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class PureJava {

    public static void main(String[] args) throws IOException {

        var upList1 = List.of( "刘能", "赵四", "谢广坤" );
        var upList2 = List.of( "永强", "玉田", "刘英" );
        var upList3 = List.of( "谢飞机", "兰妮", "兰娜" );
        var upListAll = List.of( upList1, upList2, upList3 );

        System.out.println("test var and ListOf:");
        for( var i : upListAll ) { // 用var接受局部变量的确非常简洁！
            for( var j : i  ) {
                System.out.print(j);
            }
        }

        //新版字符串String类型增加了诸如：isBlank()、strip()、repeat()等方便的字符串处理方法
        System.out.println("test new string methods.");
        System.out.println( "  ".isBlank() ); // 打印：true
        System.out.println( "  ".isEmpty() ); // 打印：false

        String myName = " codesheep ";
        System.out.println( myName.strip() );         // 打印codesheep，前后空格均移除
        System.out.println( myName.stripLeading() );  // 打印codesheep ，仅头部空格移除
        System.out.println( myName.stripTrailing() ); // 打印 codesheep，仅尾部空格移除
        System.out.println( myName.repeat(2) );

        // 从集合中依次删除满足条件的元素，直到不满足条件为止
        System.out.println("test collection stream function.");
        var upListSub1 = upList1.stream()
                .dropWhile( item -> item.equals("刘能") )
                .collect( Collectors.toList() );
        System.out.println(upListSub1);

        var upListSub2 = upList1.stream()
                .takeWhile( item -> item.equals("刘能") )
                .collect( Collectors.toList() );
        System.out.println( upListSub2 ); // 打印 [刘能]

        System.out.println("test HttpClient.");
        //当然你也可以自定义请求头，比如携带JWT Token权限信息去请求等：
        var requestWithAuth = HttpRequest.newBuilder()
                .uri( URI.create("https://www.baidu.com") )
             //   .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2Vybm8iOiI1YzhlZGUzYzY1NzAwMjE0ZjgwZjBiOGYiLCJpYXQiOjE1ODgwNjczNDUsImV4cCI6MTcwNjAwMzM0NX0.zGHMOcFFMsqVTRWnzgbhdk_Mn5nn1eDeqis4rD3hsF0")
                .GET()
                .build();
        CompletableFuture<String> future = HttpClient.newHttpClient().
                sendAsync( requestWithAuth, HttpResponse.BodyHandlers.ofString() )
                .thenApply( HttpResponse::body );
        System.out.println("我先继续干点别的事情...");
        try {
            System.out.println( future.get()); // 打印获取到的网页内容
        }catch (Exception e) {
            System.out.println( e.getMessage()); // 打印获取到的网页内容
        }

        System.out.println("test file method");
        Path path = Paths.get("temp/test.txt");
        if(!Files.exists(path)){
            Files.createFile(path);
        }

        Files.writeString( path, "王老七", StandardCharsets.UTF_8 );
        String content = Files.readString(path, StandardCharsets.UTF_8);
        System.out.println(content);

        try{
            InputStream inputStream = new FileInputStream( "temp/test.txt" );
            OutputStream outputStream = new FileOutputStream( "temp/test2.txt" );
            inputStream.transferTo( outputStream );
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
