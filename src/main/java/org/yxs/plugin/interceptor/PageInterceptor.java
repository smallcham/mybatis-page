package org.yxs.plugin.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by medusa on 2016/10/20.
 * explain:
 */

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class PageInterceptor implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        while (metaObject.hasGetter("h")) {
            metaObject = SystemMetaObject.forObject(metaObject.getValue("h"));
        }
        String sql = String.valueOf(metaObject.getValue("delegate.boundSql.sql"));

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {}
}
