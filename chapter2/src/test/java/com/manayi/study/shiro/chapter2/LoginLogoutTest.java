package com.manayi.study.shiro.chapter2;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * shiro-身份验证测试类
 * Created by CJY on 2018/3/26.
 */
@RunWith(JUnit4.class)
public class LoginLogoutTest {

    @Test
    public void testHelloWorld() {
        //1、创建使用Ini配置文件的SecurityManager工厂，
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //2、获取SecurityManager，并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        //3、执行登录逻辑
        Subject subject = SecurityUtils.getSubject(); //获取主体，其绑定到当前线程
        UsernamePasswordToken token = new UsernamePasswordToken("ads", "123");
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(true, subject.isAuthenticated()); //断言用户身份验证成功
        //4、执行登出
        subject.logout();

    }

}
