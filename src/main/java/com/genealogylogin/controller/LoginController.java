package com.genealogylogin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 16:14 2018/11/29
 * @ Description：${description}
 * @ Modified By：
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/api/login")
public class LoginController {

    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public Object userLogin(){
        return "登录成功！";
    }
}
