package org.yxs.plugin.interceptor;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.yxs.plugin.enums.SettingKey;
import org.yxs.plugin.support.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

/**
 * Created by medusa on 2016/10/20.
 * explain:
 */

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class FastPage implements Interceptor {

    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
//        while (metaObject.hasGetter("h")) {
//            metaObject = SystemMetaObject.forObject(metaObject.getValue("h"));
//        }
        BaseStatementHandler handler = (BaseStatementHandler) metaObject.getValue("delegate");
        BoundSql boundSql = handler.getBoundSql();
        MetaObject handleObject = SystemMetaObject.forObject(handler);
        MappedStatement statement = (MappedStatement) handleObject.getValue("mappedStatement");
        //判断是否需要分页
        if (!MybatisUtil.isPaging(statement.getId())) return invocation.proceed();
        //获取总记录数
        long total = MybatisUtil.total(invocation, statement, boundSql);
        //计算分页信息
        Page<Object> page = MybatisUtil.newPage(total, boundSql.getParameterObject());
        //设置最终执行分页SQL
        MybatisUtil.page(handleObject, boundSql, page);
        //返回结果集
        return MybatisUtil.result((PreparedStatement) invocation.proceed(), page);
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {
        MybatisUtil.setDbType(properties.getProperty(SettingKey.DB_TYPE.getKey()));
        MybatisUtil.setMethods(properties.getProperty(SettingKey.METHOD.getKey()));
        MybatisUtil.setKey(properties.getProperty(SettingKey.PAGE_KEY.getKey()));
        MybatisUtil.setPageSize(properties.getProperty(SettingKey.PAGE_SIZE.getKey()));
    }
}
