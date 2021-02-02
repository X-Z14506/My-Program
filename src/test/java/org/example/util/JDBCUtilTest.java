package org.example.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Intellij IDEA
 * Description:
 * Users:123
 * Date:2020-12-06
 * Time:23:19
 */

public class JDBCUtilTest {

    @Test
    public void testConnection() {
        Assert.assertNotNull(JDBCUtil.getConnection());
    }

    @Test
    public void test() throws ClassNotFoundException {
        Class.forName("com.sss.ok");
    }
}

