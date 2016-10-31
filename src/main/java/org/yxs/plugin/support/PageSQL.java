package org.yxs.plugin.support;

import org.yxs.plugin.enums.DBType;

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
        SQL_POOL.put(DBType.ORACLE.getName(), "select * from (select cur_sql_result.*, rownum rn from (%s) cur_sql_result  where rownum <= %s) where rn > %s");
    }

    public static String get(String type) {
        return SQL_POOL.get(type);
    }

    public static String get(String type, String sql, long limit, long size) {
        return String.format(get(type), sql, limit, size);
    }
}
