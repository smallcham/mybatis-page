package com.github.smallcham.plugin.page.support;

import com.github.smallcham.plugin.page.enums.DBType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by medusa on 2016/10/31.
 * explain:
 */
public class PageSQL {
    private static Map<String, String> SQL_POOL = new HashMap<>();

    static {
        SQL_POOL.put(DBType.MYSQL.getName(), "select * from (%s) $_paging limit %s, %s");
        SQL_POOL.put(DBType.ORACLE.getName(), "select * from ( select row_.*, rownum rownum_ from (%s) row_ ) where rownum_ between %s and %s");
    }

    public static String get(String type) {
        return SQL_POOL.get(type);
    }

    public static String get(String type, String sql, long limit, long size) {
        if (DBType.getName(type).equals(DBType.ORACLE.getName())) {
            return String.format(get(type), sql, limit + 1, size + limit);
        }
        return String.format(get(type), sql, limit, size);
    }
}
