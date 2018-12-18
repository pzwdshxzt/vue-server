package com.cyqqq.services.biz;

import com.cyqqq.model.R;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/5 15:39
 * @Version :
 */
public interface UserBiz {
    /**
     * 登陆或者注册校验
     * @param o
     * @return
     */
    R loginc(String o);

    /**
     * 获取用户的TodoList
     * @param o
     * @return
     */
    R getUserTodoList(String o);

    /**
     * ADD TODO事件
     * @param o
     * @return
     */
    R addTodo(String o);

    /**
     * TODO事件打卡
     * @param o
     * @return
     */
    R clockTodo(String o);

    /**
     * TODO事件完成
     * @param o
     * @return
     */
    R doneTodo(String o);

    /**
     * TODO事件删除
     * @param o
     * @return
     */
    R delTodo(String o);
}
