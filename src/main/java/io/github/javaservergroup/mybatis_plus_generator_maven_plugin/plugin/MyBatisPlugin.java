package io.github.javaservergroup.mybatis_plus_generator_maven_plugin.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

public class MyBatisPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //添加domain的import
        topLevelClass.addImportedType("lombok.experimental.Accessors");
        topLevelClass.addImportedType("lombok.Data");

        //添加domain的注解
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Accessors(chain = true)");

        // 获取表注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(" */");

        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        field.setVisibility(JavaVisibility.PROTECTED);

        genRemark(field, topLevelClass, introspectedColumn);
        genLength(field, topLevelClass, introspectedColumn);
        genNullable(field, topLevelClass, introspectedColumn);
        genFastJsonName(field, topLevelClass, introspectedColumn);

        return true;
    }

    private void genNullable(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        if (!introspectedColumn.isNullable()) {
            topLevelClass.addImportedType("jakarta.validation.constraints.NotNull");
            field.addAnnotation("@NotNull");
        }
        if (introspectedColumn.isAutoIncrement()) {
            topLevelClass.addImportedType("com.baomidou.mybatisplus.annotation.IdType");
            topLevelClass.addImportedType("com.baomidou.mybatisplus.annotation.TableId");
            field.addAnnotation("@TableId(type = IdType.AUTO)");
        }
    }

    private void genLength(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        topLevelClass.addImportedType("org.hibernate.validator.constraints.Length");
        field.addAnnotation("@Length(max = " + introspectedColumn.getLength() + ")");
    }

    private void genRemark(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (remarks != null && !remarks.isEmpty()) {
            topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
            field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");
        }
    }

    private void genFastJsonName(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn) {
        if(!field.getName().equals(introspectedColumn.getActualColumnName())) {
            topLevelClass.addImportedType("com.alibaba.fastjson2.annotation.JSONField");
            field.addAnnotation("@JSONField(name = \"" + introspectedColumn.getActualColumnName() + "\")");
        }
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        FullyQualifiedJavaType superinterface = new FullyQualifiedJavaType("BaseMapper<" + introspectedTable.getBaseRecordType() + ">");
        FullyQualifiedJavaType imp = new FullyQualifiedJavaType("com.baomidou.mybatisplus.core.mapper.BaseMapper");

        interfaze.addSuperInterface(superinterface);
        interfaze.addImportedType(imp);

        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        interfaze.addAnnotation("@Mapper");

        return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成getter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        //不生成setter
        return false;
    }


}