package com.genealogysso.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 18:13 2018/12/6
 * @ Description：认证系统登录---认证系统后台管理
 * @ Modified By：
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/authSystem")
public class SysLoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(){
        return "system/sysLogin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String userLogin(String username,String password){
        UsernamePasswordToken utoken = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
        utoken.setRememberMe(false);
        Subject subject = SecurityUtils.getSubject();
        subject.login(utoken);
        return "system/index";
    }
}
