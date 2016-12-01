package com.github.smallcham.plugin.page.enums;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public enum DBType {
    MYSQL("MYSQL", "MYSQL"),
    ORACLE("ORACLE", "ORACLE"),
    UNKNOWN("UNKNOWN", "UNKNOWN")
    ;
    private String type;
    private String name;

    DBType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getName(String type) {
        for (DBType dbType : DBType.values()) {
            if (dbType.getType().equals(type)) return dbType.getName();
        }
        return DBType.UNKNOWN.getName();
    }

    public static void main(String[] args) {
        System.out.println(DBType.getName("MYSQL"));
    }
}
