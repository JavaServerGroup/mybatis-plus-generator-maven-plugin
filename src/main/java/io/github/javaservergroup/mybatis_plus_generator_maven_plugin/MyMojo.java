package io.github.javaservergroup.mybatis_plus_generator_maven_plugin;

import io.github.javaservergroup.mybatis_plus_generator_maven_plugin.model.Config;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static io.github.javaservergroup.mybatis_plus_generator_maven_plugin.util.Utils.addContext;

@Mojo(name = "gen")
public class MyMojo extends AbstractMojo {
    @Parameter
    private List<Config> dbConfigs;

    public void execute() {
        Configuration configuration = new Configuration();

        try {
            for (Config config : dbConfigs) {
                addContext(UUID.randomUUID() + ":id", configuration, config);
            }
            new MyBatisGenerator(configuration, new DefaultShellCallback(true), null).generate(null);
        } catch (SQLException | InterruptedException | IOException | InvalidConfigurationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
