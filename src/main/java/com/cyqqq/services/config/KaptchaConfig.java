package com.cyqqq.services.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.Random;

/**
 * Description
 * 验证码
 *
 * @Author : huangjinxing
 * @Email : hmm7023@gmail.com
 * @Date : 2018/12/12 11:15
 * @Version :
 */
@Component
public class KaptchaConfig extends Configurable implements TextProducer {

    public KaptchaConfig() {

    }

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 自定义文本
        properties.setProperty("kaptcha.textproducer.impl", "com.cyqqq.services.config.KaptchaConfig");
        // 图片边框
        properties.setProperty("kaptcha.border", "no");
        // 边框颜色
        properties.setProperty("kaptcha.textproducer.char.string", "1234567890");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "150");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 去除干扰线
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "28");
        // session key
        properties.setProperty("kaptcha.session.key", "code");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");

        properties.setProperty("kaptcha.background.clear.from", "white");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        defaultKaptcha.createText();

        return defaultKaptcha;
    }

    @Override
    public String getText() {
        // TODO 变成加减乘除 验证码
        int length = this.getConfig().getTextProducerCharLength();
        char[] chars = this.getConfig().getTextProducerCharString();
        Random rand = new Random();
        StringBuffer text = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            text.append(chars[rand.nextInt(chars.length)]);
        }

        return text.toString();
    }
}
