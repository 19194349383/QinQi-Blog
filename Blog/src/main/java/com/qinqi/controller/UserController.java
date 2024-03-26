package com.qinqi.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qinqi.domain.User;
import com.qinqi.domain.VerifyCode;
import com.qinqi.service.UserService;
import com.qinqi.service.impl.AuthService;
import com.qinqi.utils.R;
import com.qinqi.utils.UUid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/verCode")
    public R<VerifyCode> generateVerifyCode() throws IOException {
        return R.success(authService.generateVerifyCode());
    }


    @PostMapping("/login")
    public R<User> login(@RequestBody User user, HttpServletRequest request) {
        //获取验证码
        String code = user.getVerCode();
        // 从Session读取验证码并删除缓存
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        String expect =(String) session.getAttribute(user.getVarCodeKey());
        session.removeAttribute(user.getVarCodeKey());

        // 比较用户输入的验证码和缓存中的验证码是否一致
        if (!StringUtils.hasText(expect) || !StringUtils.hasText(code) || !code.equalsIgnoreCase(expect)) {
            return R.error("验证码错误");
        }

        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User U = userService.getOne(wrapper);

        if (U == null) {
            return R.error("登录失败，该用户不存在");
        }

        if (!U.getPassword().equals(password)) {
            return R.error("密码错误，请重试");
        }

        request.getSession().setAttribute("UserId", U.getId());
        return R.success(U);
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("User");
        return R.success("退出成功");
    }


    @PostMapping
    public R<String> save(@RequestBody User user) {
        user.setId(UUid.getUUid());
        //对密码进行md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User U = userService.getOne(wrapper);
        if (U != null) {
            return R.error("该用户已存在");
        }
        userService.save(user);
        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody User user) {
        if (userService.getById(user.getId()) == null) {
            return R.error("该用户不存在，无法修改");
        }
        if (user.getPassword() != null) {
            //修改密码对密码进行加密处理
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        }
        userService.updateById(user);
        return R.success("修改成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {

        if (userService.getById(id) == null) {
            return R.error("该用户不存在");
        }
        userService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        Page<User> pageInfo = new Page<>(page, pageSize);
        userService.page(pageInfo);
        return R.success(pageInfo);
    }

    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return R.error("该用户不存在");
        }
        return R.success(user);
    }

}
