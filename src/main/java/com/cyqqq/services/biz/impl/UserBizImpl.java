package com.cyqqq.services.biz.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyqqq.constant.Constants;
import com.cyqqq.model.R;
import com.cyqqq.model.pojo.Todo;
import com.cyqqq.model.pojo.User;
import com.cyqqq.services.biz.UserBiz;
import com.cyqqq.services.mapper.TodoMapper;
import com.cyqqq.services.mapper.UserMapper;
import com.cyqqq.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Description
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/5 15:40
 * @Version :
 */
@Service("userBiz")
public class UserBizImpl implements UserBiz {

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");

    public JSONObject init(String o) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(o);
        return jsonObject.getJSONObject("data");
    }


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserMapper userMapper;
    @Resource
    private TodoMapper todoMapper;

    @Override
    public R loginc(String o) {
        JSONObject jsonObject = init(o);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        User inputUser = JSONObject.parseObject(jsonObject.toJSONString(), User.class);
        queryWrapper.lambda().eq(User::getUsername, inputUser.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            /** 不存在用户，注册一个 */
            int i = userMapper.insert(inputUser);
            log.info("注册用户 {}", i);
            user = userMapper.selectOne(queryWrapper);
            return R.ok().put(Constants.DATA, user.toString()).put("msg", "注册成功");
        }

        if (user != null) {
            if (!inputUser.getPassword().equals(user.getPassword())) {
                /** 密码错误 */
                return R.error(Constants.ERROR_CODE_0001, Constants.ERROR_CODE_0001_MSG);
            }
        }

        return R.ok().put(Constants.DATA, user.toString()).put("msg", "登陆成功");
    }

    @Override
    public R getUserTodoList(String o) {
        JSONObject jsonObject = init(o);
        User inputUser = JSONObject.parseObject(jsonObject.toJSONString(), User.class);
        QueryWrapper<Todo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Todo::getUserId, inputUser.getId());
        List<Todo> todos = todoMapper.selectList(queryWrapper);

        for (Todo todo : todos) {
            boolean isClockContinuity = !(isDifferentClockDays(todo.getClockDate(), 1, 99) || isDifferentClockDays(todo.getClockDate(), 0, 99));
            if (new Integer(1).equals(todo.getClockFlag())) {
                if (new Integer(0).equals(todo.getStatus()) && isClockContinuity) {
                    todo.setClockCount(0);
                    todo.setClockFlag(0);
                    todoMapper.updateById(todo);
                }
            }
        }
        if (todos.size() > 0) {
            return R.ok().put(Constants.DATA, todos.toString());
        }
        return R.ok();
    }

    @Override
    public R addTodo(String o) {
        JSONObject jsonObject = init(o);
        Todo todo = JSONObject.parseObject(jsonObject.toJSONString(), Todo.class);
        LocalDateTime now = LocalDateTime.now();
        todo.setDate(now.format(formatter));
        todo.setCount(0);
        todo.setClockCount(0);
        todo.setStatus(0);
        todo.setClockFlag(0);
        todoMapper.insert(todo);
        return R.ok().put("msg", "添加成功");
    }

    @Override
    public R clockTodo(String o) {
        JSONObject jsonObject = init(o);
        String id = jsonObject.getString("id");
        Todo todo = todoMapper.selectById(id);
        if (todo != null) {
            if (!isDifferentClockDays(todo.getClockDate(), 0, 99)) {
                if (!isDifferentClockDays(todo.getClockDate(), 1, 99) || new Integer(0).equals(todo.getClockFlag())) {
                    todo.setClockCount(1);
                    todo.setClockFlag(1);
                } else {
                    todo.setClockCount(todo.getClockCount() + 1);
                }
                LocalDateTime now = LocalDateTime.now();
                todo.setClockDate(now.format(formatter));
                todo.setCount(todo.getCount() + 1);
                todoMapper.updateById(todo);
            } else {
                return R.error().put("msg", "今日已经打卡了");
            }
        } else {
            return R.error().put("msg", "系统异常");
        }

        return R.ok().put("msg", "打卡成功");
    }

    @Override
    public R doneTodo(String o) {
        JSONObject jsonObject = init(o);
        Long id = jsonObject.getLong("id");
        Todo todo = todoMapper.selectById(id);
        todo.setStatus(1);
        todoMapper.updateById(todo);
        return R.ok().put("msg", "TODO完成");
    }

    @Override
    public R delTodo(String o) {
        JSONObject jsonObject = init(o);
        Long id = jsonObject.getLong("id");
        Todo todo = todoMapper.selectById(id);
        todo.setStatus(2);
        todoMapper.updateById(todo);
        return R.ok().put("msg", "删除成功");
    }

    /**
     * 判断是否是连续打卡 / 或者今天是否打卡
     *
     * @param date 时间
     * @param i    相差时间
     * @param flag 判断
     * @return
     */
    private boolean isDifferentClockDays(String date, Integer i, Integer flag) {
        if (date == null || "".equals(date)) {
            return false;
        }
        if (flag.equals(1)) {
            return DateUtils.differentDays(LocalDateTime.parse(date, formatter), new Date()) > i;
        }
        if (flag.equals(0)) {
            return DateUtils.differentDays(LocalDateTime.parse(date, formatter), new Date()) < i;
        }

        return DateUtils.differentDays(LocalDateTime.parse(date, formatter), new Date()) == i;
    }


}
