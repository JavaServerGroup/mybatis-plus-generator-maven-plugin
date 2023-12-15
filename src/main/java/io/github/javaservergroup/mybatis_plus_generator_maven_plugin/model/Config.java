package io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Config {
    // 数据库url
    private String url;
    // 数据库用户名
    private String username;
    // 数据库密码
    private String passwd;
    // 生成的文件放到那个包名
    private String outputPackageName;
}
