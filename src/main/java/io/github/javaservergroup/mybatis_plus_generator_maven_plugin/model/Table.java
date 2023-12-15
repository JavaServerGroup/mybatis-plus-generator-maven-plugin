package io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model;

import com.google.common.base.CaseFormat;
import lombok.Data;

@Data
public class Table {
    private String tableName;
    private String domainObjectName;
    private String generatedKeyColumn;

    public Table(String tableName, String domainObjectName) {
        this.tableName = tableName;
        this.domainObjectName = domainObjectName;
    }

    public Table(String tableName) {
        this.tableName = tableName;
        this.domainObjectName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
    }

    // 用于设置自动生成主键的名称
    public Table setGeneratedKeyColumn(String generatedKeyColumn) {
        this.generatedKeyColumn = generatedKeyColumn;
        return this;
    }

}
