package org.yxs.plugin.interceptor;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.yxs.plugin.enums.DBType;
import org.yxs.plugin.exception.PageParamTypeException;
import org.yxs.plugin.exception.UnSetNextPageException;
import org.yxs.plugin.exception.UnknownSQLException;
import org.yxs.plugin.support.Page;
import org.yxs.plugin.support.PageSQL;
import org.yxs.plugin.support.TotalSQL;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public class PageUtil {

    private static List<Pattern> PATTERNS = new ArrayList<>(Collections.singletonList(Pattern.compile(".*query*")));
    private static String DB_TYPE = DBType.MYSQL.getName();
    private static String KEY = "NEXT_PAGE";
    public static Long PAGE_SIZE = Page.DEFAULT_PAGE_SIZE;

    public static long total(Invocation invocation, MappedStatement statement, BoundSql boundSql) throws SQLException {
        String sql = boundSql.getSql();
        sql = TotalSQL.get(DBType.getName(DB_TYPE), sql);
        Connection connection = getConnection(invocation);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            BoundSql totalSql = new BoundSql(statement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            ParameterHandler handle = new DefaultParameterHandler(statement, boundSql.getParameterObject(), totalSql);
            handle.setParameters(ps);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt(TotalSQL.DUAL_TABLE);
            }
        } finally {
            ps.close();
        }
        return 0;
    }

    public static void page(MetaObject metaObject, BoundSql boundSql, Page<Object> page) throws InvocationTargetException, IllegalAccessException {
        String sql = boundSql.getSql();
        sql = PageSQL.get(DB_TYPE, sql, page.getPageNum(), page.getPageSize());
        metaObject.setValue("boundSql.sql", sql);
    }

    public static boolean isPaging(String methodId) {
        for (Pattern pattern : PATTERNS) {
            Matcher matcher = pattern.matcher(methodId);
            if (matcher.find()) return true;
        }
        return false;
    }

    protected static void setMethods(String method) {
        if (isEmpty(method)) return;
        String[] methods = method.split(";");
        for (int i = 0; i < methods.length; i++) {
            PATTERNS.add(Pattern.compile(methods[i]));
        }
    }

    protected static void setDbType(String dbType) {
        DB_TYPE = DBType.getName(dbType);
        if (DBType.UNKNOWN.getName().equals(DB_TYPE)) {
            throw new UnknownSQLException("not support the current sql");
        }
    }

    protected static void setKey(String key) {
        if (isEmpty(key)) return;
        KEY = key;
    }

    protected static void setPageSize(String pageSize) {
        if (isEmpty(pageSize)) return;
        PAGE_SIZE = Long.valueOf(pageSize);
    }

    public static String key() {
        return KEY;
    }

    @SuppressWarnings("unchecked")
    public static Page<Object> newPage(long total, Object paramObject) {
        if (paramObject instanceof Map) {
            Map<String, Object> param = (Map<String, Object>) paramObject;
            Object nextPageObj = param.get(KEY);
            if (null == nextPageObj) throw new UnSetNextPageException("the page param \"" + KEY + "\" is unset");
            else return new Page<>(total, Long.valueOf(String.valueOf(nextPageObj)), PAGE_SIZE);
        }
        throw new PageParamTypeException("the page param type must be map");
    }

    public static Connection getConnection(Invocation invocation) {
        return (Connection) invocation.getArgs()[0];
    }

    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str) || "".equals(str.replaceAll(" ", "")));
    }
}
