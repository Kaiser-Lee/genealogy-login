package com.genealogysso.service.impl;

import com.genealogysso.service.LoginAuthenticationService;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：lufangpu
 * @ Date       ：Created in 17:24 2018/12/3
 * @ Description：登录授权
 * @ Modified By：
 * @Version: 1.0.0
 */
@Service
public class LoginAuthenticationServiceImpl implements LoginAuthenticationService {

    @Override
    public boolean loginAuthentication() {
        return false;
    }

    public boolean loginVerification(String url,String token){
        return false;
    }
}
