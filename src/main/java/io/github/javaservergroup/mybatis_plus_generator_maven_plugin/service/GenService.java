package io.github.javaservergroup.mybatis_plus_generator_maven_plugin.service;

import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model.Config;
import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model.Table;
import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.plugin.MyBatisPlugin;
import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.resolver.MyJavaTypeResolver;
import org.mybatis.generator.config.*;
import org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin;

import java.util.List;

public class GenService {

    public static Context genContext(String id, Config config, List<Table> tables) {
        Context c = new Context(ModelType.FLAT);
        c.setId(id);
        c.setTargetRuntime("MyBatis3");
        c.addProperty("autoDelimitKeywords", "true");

        // 连接配置
        JDBCConnectionConfiguration jDBCConnectionConfiguration = new JDBCConnectionConfiguration();
        jDBCConnectionConfiguration.setDriverClass("com.mysql.cj.jdbc.Driver");
        jDBCConnectionConfiguration.addProperty("useInformationSchema", "true");
        jDBCConnectionConfiguration.setConnectionURL(config.getUrl());
        jDBCConnectionConfiguration.setUserId(config.getUsername());
        jDBCConnectionConfiguration.setPassword(config.getPasswd());
        c.setJdbcConnectionConfiguration(jDBCConnectionConfiguration);

        // 生成model放置的地方
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(config.getOutputPackageName() + ".entity");
        javaModelGeneratorConfiguration.setTargetProject("./src/main/java");
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        c.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 生成mapper放置的地方
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetPackage(config.getOutputPackageName() + ".mapper");
        sqlMapGeneratorConfiguration.setTargetProject("./src/main/java");
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
        c.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 生成client放置的地方
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.setTargetPackage(config.getOutputPackageName() + ".mapper");
        javaClientGeneratorConfiguration.setTargetProject("./src/main/java");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
        c.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // 生成的 Java 文件的编码
        c.addProperty("javaFileEncoding", "UTF-8");
        // 格式化 Java 代码
        c.addProperty("javaFormatter", "org.mybatis.generator.api.dom.DefaultJavaFormatter");
        // 格式化 XML 代码
        c.addProperty("xmlFormatter", "org.mybatis.generator.api.dom.DefaultXmlFormatter");

        // 自定义PluginAdapter
        PluginConfiguration myBatisPlugin = new PluginConfiguration();
        myBatisPlugin.setConfigurationType(MyBatisPlugin.class.getCanonicalName());
        c.addPluginConfiguration(myBatisPlugin);

        // 不要合并生成XML文件
        PluginConfiguration unmergeableXmlMappersPlugin = new PluginConfiguration();
        unmergeableXmlMappersPlugin.setConfigurationType(UnmergeableXmlMappersPlugin.class.getCanonicalName());
        c.addPluginConfiguration(unmergeableXmlMappersPlugin);

        // 关闭默认注释
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        c.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // 自定义Java类型解析器
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.setConfigurationType(MyJavaTypeResolver.class.getCanonicalName());
        c.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

        // 设置需要生成的表
        for (Table table : tables) {
            TableConfiguration tableConfiguration = new TableConfiguration(c);
            tableConfiguration.setTableName(table.getTableName());
            tableConfiguration.setDomainObjectName(table.getDomainObjectName());
            if(table.getGeneratedKeyColumn() != null && !table.getGeneratedKeyColumn().trim().isEmpty()) {
                tableConfiguration.setGeneratedKey(new GeneratedKey("id", "JDBC", true, null));
            }
            c.addTableConfiguration(tableConfiguration);
        }

        return c;
    }
}
