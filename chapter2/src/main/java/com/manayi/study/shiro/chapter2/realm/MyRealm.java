package com.manayi.study.shiro.chapter2.realm;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * 自定义realm
 * Created by chenjunyi on 2018/3/26.
 */
public class MyRealm implements Realm {

    /**
     * 设置Realm的名称
     * @return Realm名称
     */
    @Override
    public String getName() {

        return "myreaml";
    }

    /**
     * Realm是否支持传入的Token
     * @param authenticationToken 传入的token，为AuthenticationToken实现类
     * @return true-支持；false-不支持
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof UsernamePasswordToken;
    }

    /**
     * Realm的获取认证信息逻辑
     * @param authenticationToken 验证身份信息用的token
     * @return 用户身份信息
     * @throws AuthenticationException 验证不通过抛出的异常
     */
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //在supports方法中，明确了该Realm只支持UsernamePasswordToken
        //在UsernamePasswordToken中，getCredentials获取到的为char[]
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if (!StringUtils.equals("zhang", username)) {
            throw new UnknownAccountException();
        }
        if (!StringUtils.equals("123", password)) {
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username, password, getName());
    }

}
