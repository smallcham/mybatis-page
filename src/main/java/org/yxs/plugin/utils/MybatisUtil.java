package org.yxs.plugin.utils;

import org.yxs.plugin.enums.DBType;
import org.yxs.plugin.exception.PageSettingException;
import org.yxs.plugin.exception.UnknownSQLException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public class MybatisUtil {

    private static List<String> METHOD_LIST = new ArrayList<>();

    public static String dbType(String dbType) throws Exception {
        dbType = DBType.getName(dbType);
        if (DBType.UNKNOWN.getName().equals(dbType)) {
            throw new UnknownSQLException("not support the current sql");
        }
        return dbType;
    }

    public static long total(String sql, String dbType) {
        sql = TotalSQL.get(DBType.getName(dbType), sql);
        return 0;
    }

    public static boolean isPaging(String methods, Method method) {
        if (METHOD_LIST.isEmpty()) Collections.addAll(METHOD_LIST, methods(methods));
        return METHOD_LIST.contains(method.getName());
    }

    public static String[] methods(String method) {
        if (null == method || "".equals(method) || "".equals(method.replaceAll(" ", ""))) throw new PageSettingException("the property method is not set");
        return method.split(",");
    }
}
