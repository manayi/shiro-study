package com.manayi.study.shiro.chapter3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

/**
 * 基于角色的权限控制测试类
 * Created by chenjunyi on 2018/3/27.
 */
@RunWith(JUnit4.class)
public class RoleTest {

    /**
     * hasRole相关方法，返回boolean值，而不是抛异常
     */
    @Test
    public void testHasRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        //判断用户是否具有角色：role1
        Assert.assertTrue(subject().hasRole("role1"));
        //批量判断用户是否具有角色：role1 role2 role3
        boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true, result[0]);
        Assert.assertEquals(true, result[1]);
        Assert.assertEquals(false, result[2]);
        //判断用户是否具有全部角色：role1 && role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));
    }

    /**
     * checkRole相关方法，直接抛异常，而不是返回boolean值
     */
    @Test
    public void testCheckRole() {
        login("classpath:shiro-role.ini", "zhang", "123");
        //判断用户是否具有角色：role1
        subject().checkRole("role1");
        //判断用户是否具有全部角色：role1 && role3
        subject().checkRoles("role1", "role3");
    }

    /**
     * 用户登录
     * @param resource ini配置文件地址
     * @param username 用户名
     * @param password 密码
     */
    public void login(String resource, String username, String password) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(resource);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        subject.login(token);
    }

    /**
     * 获取subject主体对象
     * @return Subject主体对象
     */
    public Subject subject() {
       return SecurityUtils.getSubject();
    }

}
