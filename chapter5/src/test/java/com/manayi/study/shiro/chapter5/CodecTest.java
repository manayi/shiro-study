package com.manayi.study.shiro.chapter5;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 加密测试
 * Created by chenjunyi on 2018/3/28.
 */
@RunWith(JUnit4.class)
public class CodecTest {

    @Test
    public void testHash() {
        String str = "hello";
        String salt = "123";
        String sha1 = new Sha256Hash(str, salt).toString();
        System.out.print(sha1);
    }

}
