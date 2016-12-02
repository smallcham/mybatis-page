package com.github.smallcham.plugin.page.support;

import com.github.smallcham.plugin.page.enums.DBType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void main(String[] args) {
//        String sql = "select     asdjkasladjl, aasdamasd,asdas from(select * from asdasdas) as agent_agent         asda";
//        System.out.println(SQLUtil.hasSubSelect(sql));
//        System.out.println(SQLUtil.getTableName(sql));
    }
}
