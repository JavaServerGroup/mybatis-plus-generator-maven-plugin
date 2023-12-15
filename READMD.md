# 引入项目
```xml
<build>
        <plugins>
            <plugin>
                <groupId>io.github.javaservergroup</groupId>
                <artifactId>mybatis-plus-generator-maven-plugin</artifactId>
                <version>0.0.1</version>
                <!-- 配置需要生成的数据库，这里演示配置两个数据库 -->
                <configuration>
                    <dbConfigs>
                        <!-- 第一个数据库 -->
                        <dbConfig>
                            <!-- 数据连接url -->
                            <url>jdbc:mysql://127.0.0.1:3306/cms?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;allowMultiQueries=true&amp;allowPublicKeyRetrieval=true</url>
                            <!-- 数据库用户民 -->
                            <username>root</username>
                            <!-- 数据库密码 -->
                            <passwd>your_password</passwd>
                            <!-- 生成到项目的那个包 -->
                            <outputPackageName>tv.onreels.dao.mysql.cms</outputPackageName>
                        </dbConfig>
                        <!-- 第二个数据库，参考第一个库 -->
                        <dbConfig>
                            <url>jdbc:mysql://127.0.0.1:3306/main?useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false&amp;allowMultiQueries=true&amp;allowPublicKeyRetrieval=true</url>
                            <username>root</username>
                            <passwd>your_password</passwd>
                            <outputPackageName>tv.onreels.dao.mysql.main</outputPackageName>
                        </dbConfig>
                    </dbConfigs>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
# 执行生成
```shell
mvn io.github.javaservergroup:mybatis-plus-generator-maven-plugin:gen
```
# 注意
生成的`entity`和`mapper`会覆盖原来的文件，所以建议自己写的`entity`和`mapper`可以放到别的地方，比如`extentity`和`extmapper`