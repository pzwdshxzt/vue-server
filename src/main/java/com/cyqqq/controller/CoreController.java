package com.cyqqq.controller;

import com.cyqqq.annotation.RequestDecode;
import com.cyqqq.annotation.ResponseEncode;
import com.cyqqq.model.R;
import com.cyqqq.util.DateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/30 14:15
 * @Version :
 */
@RestController
@RequestMapping("/")
public class CoreController {

    @RequestDecode
    @RequestMapping(value = "timestamp", method = RequestMethod.GET)
    public R index(){
        return R.ok().put("timestamp",DateUtils.getTimeString());
    }

    @RequestDecode
    @RequestMapping(value = "start", method = RequestMethod.POST)
    public R start(@RequestBody String o){
        System.out.println(o);
        return R.ok().put("timestamp",DateUtils.getTimeString());
    }
}
