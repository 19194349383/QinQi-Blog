package com.qinqi.service.impl;

import com.qinqi.domain.VerifyCode;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private final ScheduledExecutorService scheduledExecutorService;

    public AuthService(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public VerifyCode generateVerifyCode() throws IOException {
        // 创建验证码对象
        Captcha captcha = new ArithmeticCaptcha();

        // 生成验证码编号
        String verifyCodeKey = UUID.randomUUID().toString();
        String verifyCode = captcha.text();

        // 获取验证码图片，构造响应结果
        VerifyCode verifyCodeEntity = new VerifyCode(verifyCodeKey, captcha.toBase64(), verifyCode);

        // 存入session，设置120s过期
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        session.setAttribute(verifyCodeKey, verifyCode);
        // 超时后删除验证码缓存
        // 以下是使用ScheduledExecutorService实现
        scheduledExecutorService.schedule(() -> {
            session.removeAttribute(verifyCode);
        }, 120, TimeUnit.SECONDS);

        return verifyCodeEntity;
    }


}


