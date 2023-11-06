package com.demo.spb;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class SimpleTest {

    @BeforeAll
    static void allInit(){
        log.info("allInit()：在所有方法前执行，只执行一次");
    }

    @BeforeEach
    void eachInit(){
        log.info("eachInit()：在测试方法前执行，每个测试方法前都执行");
    }

    @AfterEach
    void eachDown(){
        log.info("eachDown()：在测试方法后执行，每个测试方法后都执行");
    }

    @AfterAll
    static void allDown(){
        log.info("allDown()：在测试方法后执行，每个测试方法后都执行");
    }



    @Disabled
    @Test
    @Order(1)
    @ParameterizedTest
    @ValueSource(ints = {500,800,1200, 1500})
    void lowOrder(long sleepTime){
        log.info("lowOrder method");
        long finalSleepTime = sleepTime;
        Assertions.assertTimeout(Duration.ofMillis(1000), ()->{
            ThreadUtil.sleep(finalSleepTime);
            log.info("timeoutTest():休眠{}毫秒", finalSleepTime);
        });
    }

    @Test
    @Order(10)
    @DisplayName("order为10的方法")
    @Disabled
    void highOrder(){
        log.info("highOrder method");
        Assertions.fail("Error test");
    }
}
