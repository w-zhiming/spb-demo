package com.demo.spb;

import com.demo.spb.common.CommonResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class DemoController {


    @GetMapping(value = "/GetType")
    @ResponseBody
    public CommonResult<String> GetTypeList(@RequestParam String type) {

        return CommonResult.success("OK", type);
    }


}
