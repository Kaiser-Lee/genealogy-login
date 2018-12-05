package com.genealogysso.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.genealogy.po.User;
import com.genealogy.service.UserService;
import com.genealogysso.common.config.ApplicationContextRegister;
import com.management.redis.RedisManager;
import com.management.utils.IPUtils;
import com.management.utils.MD5Utils;
import com.management.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 16:14 2018/11/29
 * @ Description：${description}
 * @ Modified By：
 * @Version: 1.0.0
 */
@Controller
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public R userLogin(@ApiParam("用户名") @RequestParam String username, @ApiParam("密码") @RequestParam String password, HttpServletRequest request){
        try {
            Long startTime = System.currentTimeMillis();
            String ip = IPUtils.getIpAddr(request);
            RedisManager redisManager = RedisManager.getRedisSingleton();
            UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            token.setRememberMe(false);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);

            //获取用户　
            Map<String, Object> map = new HashMap<>();
            map.put("number",username);
            //List<SellerUser> list = sellerUserService.list(map);
            List<User> list = userService.list(map);

            // 设置session
            request.getSession().setAttribute("user",list.get(0));
            //token
            Serializable id = subject.getSession().getId();
            //将token放入redis
            RedisManager manager = ApplicationContextRegister.getBean(RedisManager.class);
            //manager.set("sys:isLogin", true);
            manager.set(("sys:login:user_token_" + id).getBytes(), list.get(0).getId().toString().getBytes() , 60*30);
            manager.set(("sys:user:id_" + list.get(0).getId()).getBytes(),id.toString().getBytes(), 60*30);
            //manager.set(("sys:user:user_info_" + list.get(0).getId()).getBytes(), JSONObject.toJSONString(list.get(0)).toString().getBytes(), 60*30);

            Long stopTime = System.currentTimeMillis();
            double time = (double) ((stopTime - startTime) / 1000);
            DecimalFormat df = new DecimalFormat("#.000");
            logger.info("登录接口耗时:" + df.format(time));
            return R.ok("登录成功！").put("responseSeconds", df.format(time));
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return R.error("用户名或密码错误！");
    }

    @RequestMapping("/checkLogin")
    public String checkLogin(String redirectUrl, HttpSession session, Model model){
        // 判断是否存在全局会话
        // 从会话中获取令牌信息，如果取不到说明没有全局会员，取到说明存在
        String token = (String)session.getAttribute("token");
        if(StringUtils.isEmpty(token)){
            // 表示没有全局会话
            model.addAttribute("redirectUrl",redirectUrl);
            // 跳转到统一认证中心的登陆页面
            return "login";
        }
        redirectUrl = redirectUrl + "?token=" +"token";
        model.addAttribute("redirectUrl", redirectUrl);
        return "redirect:" + redirectUrl;
    }

    @RequestMapping("/login")
    public String login(String username,String password,String redirectUrl, HttpSession session, Model model){
        try {
            UsernamePasswordToken utoken = new UsernamePasswordToken(username, DigestUtils.md5Hex(password));
            utoken.setRememberMe(false);
            Subject subject = SecurityUtils.getSubject();
            subject.login(utoken);

            // 创建令牌信息
            String  token = UUID.randomUUID().toString();
            // 创建全局会话，将令牌信息放入会话
            session.setAttribute("token", token);
            //
            model.addAttribute("token", token);
            //将token放入redis,设置过期时间为一分钟
            RedisManager manager = ApplicationContextRegister.getBean(RedisManager.class);
            manager.set(("sys:login:user_token_" + token).getBytes(), token.getBytes(),60 * 30);

            redirectUrl = redirectUrl + "?token=" +token;

            return "redirect:" + redirectUrl;
        } catch (Exception e) {
            model.addAttribute("redirectUrl", redirectUrl);
            logger.info(e.getMessage());
        }
        return "login";
    }

    @RequestMapping("/verify")
    @ResponseBody
    public String verifyToken(String token){
        RedisManager manager = ApplicationContextRegister.getBean(RedisManager.class);
        byte[] isToken = manager.get(("sys:login:user_token_" + token).getBytes());
        if(isToken != null && isToken.length > 0){
            manager.setExpire(60*30);
            return "true";
        }
        return "false";
    }
}
