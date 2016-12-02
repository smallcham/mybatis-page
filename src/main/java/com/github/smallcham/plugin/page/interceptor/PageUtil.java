package com.github.smallcham.plugin.page.interceptor;

import com.github.smallcham.plugin.page.exception.PageParamTypeException;
import com.github.smallcham.plugin.page.support.PageSQL;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import com.github.smallcham.plugin.page.enums.DBType;
import com.github.smallcham.plugin.page.exception.UnknownSQLException;
import com.github.smallcham.plugin.page.support.Page;
import com.github.smallcham.plugin.page.support.RowBounds;
import com.github.smallcham.plugin.page.support.TotalSQL;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public class PageUtil {

    private static List<Pattern> PATTERNS = new ArrayList<>();
    private static String DB_TYPE = DBType.MYSQL.getName();
    public static Long PAGE_SIZE = Page.DEFAULT_PAGE_SIZE;

    static long total(Invocation invocation, MappedStatement statement, BoundSql boundSql) throws SQLException {
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

    static void page(MetaObject metaObject, BoundSql boundSql, Page<Object> page) throws InvocationTargetException, IllegalAccessException {
        String sql = boundSql.getSql();
        sql = PageSQL.get(DB_TYPE, sql, page.getPageNum(), page.getPageSize());
        metaObject.setValue("boundSql.sql", sql);
    }

    static boolean isPaging(String methodId) {
        for (Pattern pattern : PATTERNS) {
            Matcher matcher = pattern.matcher(methodId);
            if (matcher.find()) return true;
        }
        return false;
    }

    static void setMethods(String method) {
        if (isEmpty(method)) return;
        String[] methods = method.split(";");
        for (String m: methods) {
            PATTERNS.add(Pattern.compile(m));
        }
    }

    static void setDbType(String dbType) {
        if (!isEmpty(dbType)) dbType = dbType.toUpperCase();
        DB_TYPE = DBType.getName(dbType);
        if (DBType.UNKNOWN.getName().equals(DB_TYPE)) {
            throw new UnknownSQLException("not support the current sql");
        }
    }

    static void setPageSize(String pageSize) {
        if (isEmpty(pageSize)) return;
        PAGE_SIZE = Long.valueOf(pageSize);
    }

    @SuppressWarnings("unchecked")
    static Page<Object> newPage(long total, Object paramObject) {
        if (paramObject instanceof RowBounds) {
            RowBounds rowBounds = (RowBounds) paramObject;
            rowBounds.setRowCount(total);
            return new Page<>(rowBounds);
        }
        throw new PageParamTypeException("the page param type must be RowBounds");
    }

    private static Connection getConnection(Invocation invocation) {
        return (Connection) invocation.getArgs()[0];
    }

    public static boolean isEmpty(String str) {
        return (null == str || "".equals(str) || "".equals(str.replaceAll(" ", "")));
    }
}
