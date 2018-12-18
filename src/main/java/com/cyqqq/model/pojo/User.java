package com.cyqqq.model.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Description
 * 用户类
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/4 19:11
 * @Version :
 */
@Data
public class User {

    private Long id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
