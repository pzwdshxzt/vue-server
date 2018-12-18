package com.cyqqq.controller;

import com.cyqqq.annotation.RequestDecode;
import com.cyqqq.annotation.ResponseEncode;
import com.cyqqq.model.R;
import com.cyqqq.services.biz.UserBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * 用户控制
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/11/30 10:25
 * @Version : 0.0.1
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserBiz userBiz;

    @RequestDecode
    @ResponseEncode
    @RequestMapping("/login")
    public R checkUser(@RequestBody String o){
        return userBiz.loginc(o);
    }

    @ResponseEncode
    @RequestDecode
    @RequestMapping("/getTodoList")
    public R getUserTodoList(@RequestBody String o){
        return userBiz.getUserTodoList(o);
    }

    @ResponseEncode
    @RequestDecode
    @RequestMapping("/addTodo")
    public R addTodo(@RequestBody String o){
        return userBiz.addTodo(o);
    }
    @ResponseEncode
    @RequestDecode
    @RequestMapping("/clockTodo")
    public R clockTodo(@RequestBody String o){
        return userBiz.clockTodo(o);
    }
    @ResponseEncode
    @RequestDecode
    @RequestMapping("/doneTodo")
    public R doneTodo(@RequestBody String o){
        return userBiz.doneTodo(o);
    }
    @ResponseEncode
    @RequestDecode
    @RequestMapping("/delTodo")
    public R delTodo(@RequestBody String o){
        return userBiz.delTodo(o);
    }
}
