package com.manayi.study.shiro.chapter3;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 基于资源的权限控制测试类
 * Created by chenjunyi on 2018/3/27.
 */
@RunWith(JUnit4.class)
public class PermissionTest {

    /**
     * isPermitted相关方法，返回boolean值，而不是抛异常
     */
    @Test
    public void testIsPermitted() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //判断是否拥有权限：user:create
        Assert.assertTrue(subject().isPermitted("user:create"));
        //批量判断是否拥有权限：user:create user:update
        boolean[] result = subject().isPermitted("user.create", "user:delete");
        org.junit.Assert.assertEquals(true, result[0]);
        org.junit.Assert.assertEquals(false, result[1]);
        //判断是否拥有全部权限：user:update && user:delete
        Assert.assertTrue(subject().isPermittedAll("user:update", "user:delete"));
    }

    /**
     * checkPermission相关方法，直接抛异常，而不是返回boolean值
     */
    @Test
    public void testCheckPermission() {
        login("classpath:shiro-permission.ini", "zhang", "123");
        //判断是否拥有权限：user:create
        subject().checkPermission("user:create");
        //判断是否拥有全部权限：user:update && user:delete
        subject().checkPermissions("user:update", "user:delete");
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
