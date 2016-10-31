package org.yxs.plugin.support;

import org.yxs.plugin.enums.DBType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public class TotalSQL {
    public final static String DUAL_TABLE = "total";
    private static Map<String, String> SQL_POOL = new HashMap<>();

    static {
        SQL_POOL.put(DBType.MYSQL.getName(), "select count(*) as " + DUAL_TABLE + " from (%s) $_paging");
        SQL_POOL.put(DBType.ORACLE.getName(), "select count(*) as " + DUAL_TABLE + " from (%s)");
    }

    public static String get(String type) {
        return SQL_POOL.get(type);
    }

    public static String get(String type, String sql) {
        return String.format(get(type), sql);
    }
}
