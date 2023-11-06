package com.demo.spb;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class MySpringTest {


    @Resource
    DemoController demoController;

//    @Resource
//    CharService charService;

//    @Resource
//    private ScoreboardMapper scoreboardMapper;

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testScore(){

//        ScoreboardExample ex = new ScoreboardExample();
//        ex.createCriteria().andIduserEqualTo(18);
//
//        Scoreboard ret = scoreboardMapper.selectOneByExample(ex);
//        log.debug(ret.toString());
    }
    @Test
    public void testScore1(){

//
//        List<LinkedHashMap> ret = scoreboardDao.getMyRank(18);
//
//        log.debug(ret.toString());
    }

    @Test
    public void testService(){
//        List ret = charService.getMultiCharData(List.of("一"), CharService.DataType.info);
//
//        log.debug(ret.toString());
    }

    @Test
    public  void testMvc(){
        try {
            String  result = mockMvc.perform(MockMvcRequestBuilders.get("/demo/hello?name=bill")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", notNullValue()))
                    .andReturn().getResponse().getContentAsString(UTF_8);

            log.debug(result);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
