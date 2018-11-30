package com.cyqqq.model;

import com.cyqqq.util.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author huangjinxing
 * @email 83265414@qq.com
 * @date 2018年10月21日 19:29:34
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
        put("msg", "交易成功");
        put("date", DateUtils.getNowDate());
        put("time", DateUtils.getNowTime());
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        r.put("date", DateUtils.getNowDate());
        r.put("time", DateUtils.getNowTime());
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("code", 200);
        r.put("msg", msg);
        r.put("date", DateUtils.getNowDate());
        r.put("time", DateUtils.getNowTime());
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.put("code", 200);
        r.put("msg", "交易成功");
        r.put("date", DateUtils.getNowDate());
        r.put("time", DateUtils.getNowTime());
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
