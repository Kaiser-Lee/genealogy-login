package com.genealogysso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 18:13 2018/12/6
 * @ Description：认证系统登录
 * @ Modified By：
 * @Version: 1.0.0
 */
@Controller
@RequestMapping("/auth")
public class SysLoginController {

    @RequestMapping(value = "/login")
    String login(){
        return "system/pcLogin";
    }
}
