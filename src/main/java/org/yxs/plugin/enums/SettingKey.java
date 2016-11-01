package org.yxs.plugin.enums;

/**
 * Created by medusa on 2016/10/21.
 * explain:
 */
public enum SettingKey {
    DB_TYPE("type", "数据库类型"),
    METHOD("method", "需要分页处理的方法名"),
    PAGE_SIZE("size", "每页显示多少条")
    ;

    private String key;
    private String name;

    SettingKey(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}
