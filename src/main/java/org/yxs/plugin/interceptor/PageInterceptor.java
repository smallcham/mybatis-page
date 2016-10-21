package org.yxs.plugin.interceptor;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.yxs.plugin.enums.SettingKey;
import org.yxs.plugin.utils.MybatisUtil;

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

    private String dbType;
    private String method;

    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        while (metaObject.hasGetter("h")) {
            metaObject = SystemMetaObject.forObject(metaObject.getValue("h"));
        }
        String sql = String.valueOf(metaObject.getValue("delegate.boundSql.sql"));
        //TODO 获取数据库类型
        dbType = MybatisUtil.dbType(dbType);
        //TODO 判断是否需要分页
        if (!MybatisUtil.isPaging(method, invocation.getMethod())) return invocation.proceed();
        //TODO 获取总记录数
        long total = MybatisUtil.total(sql, dbType);
        //TODO 计算分页信息
        //TODO 执行最终SQL

        return invocation.proceed();
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        dbType = properties.getProperty(SettingKey.DB_TYPE.getKey());
        method = properties.getProperty(SettingKey.METHOD.getKey());
    }
}
