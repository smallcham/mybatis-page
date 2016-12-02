package com.github.smallcham.plugin.page.support;

import com.github.smallcham.plugin.page.exception.UnknownSQLException;
import com.github.smallcham.plugin.page.interceptor.PageUtil;

/**
 * Created by medusa on 2016/12/2.
 * explain:
 */
public class SQLUtil {
    public static String getTableName(String sql) {
        String[] part = partSQL(sql);
        for (int i = 0; i < part.length; i++) {
            String from = part[i].replace("(", "");
            if ("FROM".equalsIgnoreCase(from) && !PageUtil.isEmpty(part[i + 1])) return part[i + 1];
        }
        throw new UnknownSQLException(String.format("can not resolve this sql [%s] ", sql));
    }

    public static boolean hasSubSelect(String sql) {
        return getTableName(sql).contains("select");
    }

    public static String[] partSQL(String sql) {
        if (PageUtil.isEmpty(sql)) throw new UnknownSQLException("sql can not be empty");
        String[] format = sql.split(" ");
        String[] part = new String[format.length];
        for (int i = 0, j = 0; i < format.length; i++) {
            if (PageUtil.isEmpty(format[i])) continue;
            part[j] = format[i];
            j++;
        }
        return part;
    }
}
