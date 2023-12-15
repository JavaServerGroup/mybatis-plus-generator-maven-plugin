package io.github.javaservergroup.mybatis_plus_generator_maven_plugin.util;

import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model.Config;
import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model.Table;
import org.mybatis.generator.config.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static io.github.javaservergroup.mybatis_plus_generator_maven_plugin.service.GenService.genContext;

public class Utils {

    private static List<String> getTables(Config config) throws ClassNotFoundException, SQLException {
        List<String> result = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPasswd());
             PreparedStatement preparedStatement = connection.prepareStatement("show tables;")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        }
        return result;
    }

    public static void addContext(String name, Configuration configuration, Config config) throws SQLException, ClassNotFoundException {
        List<Table> tables = new ArrayList<>();
        for (String tableName : getTables(config)) {
            tables.add(new Table(tableName));
        }
        configuration.addContext(genContext(name, config, tables));
    }
}
