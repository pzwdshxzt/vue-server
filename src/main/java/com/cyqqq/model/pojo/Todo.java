package com.cyqqq.model.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Description
 * Todo
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/5 10:11
 * @Version :
 */
@Data
public class Todo {
    private Long id;
    private String todo;
    private String title;
    /** 累计打卡记录 */
    private Integer count;
    /** 创建日期 */
    private String date;
    private Long userId;
    /** 连续打卡记录 */
    private Integer clockCount;
    /** 最新得打卡时间 */
    private String clockDate;
    /**
     * 连续打卡标记
     * 0. 不是连续
     * 1. 连续打卡
     * */
    private Integer clockFlag;
    /**
     * todo状态
     * 0.执行中
     * 1.完成
     * 2.删除
     **/
    private Integer status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
