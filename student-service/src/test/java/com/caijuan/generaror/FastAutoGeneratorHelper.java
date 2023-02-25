package com.caijuan.generaror;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;


/**
 * @author cai juan
 * @see <a href="https://juejin.cn/post/7033399493684903949">MyBatis-Plus——代码生成器</a>
 * @see <a href="https://juejin.cn/post/7049272958790926343">Mybatis-plus3.5.0全面攻略</a>
 */
public class FastAutoGeneratorHelper {

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {


        DataSourceConfig.Builder configBuilder = new DataSourceConfig.Builder("jdbc:postgresql://localhost:5432/study", "postgres", "root")
                .dbQuery(new PostgreSqlQuery())
                .typeConvert(new PostgreSqlTypeConvert())
                .keyWordsHandler(new PostgreSqlKeyWordsHandler())
                .schema("public");

        FastAutoGenerator.create(configBuilder)
                .globalConfig(builder -> {
                    builder.author("cai juan") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://develop/code/student-info-system/student-service/student-service/src/test/java/")// 指定输出目录
                            .disableOpenDir()
                            .dateType(DateType.ONLY_DATE);
                })
                .packageConfig(builder -> {
                    builder.parent("com.caijuan") // 设置父包名
                            .moduleName("studentservice") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://develop/code/student-info-system/student-service/student-service/src/main/resources/mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.enableSchema()    // 启用 schema
                            .addInclude("stu_student") // 设置需要生成的表名
                            .addInclude("stu_course")
                            .addInclude("stu_student_course_mapping")
                            .addTablePrefix("stu_") // 设置过滤表前缀

                            .mapperBuilder()
                            .superClass(BaseMapper.class)   // 设置父类
                            .formatMapperFileName("%sMapper")   // 格式化 mapper 文件名称
                            .enableMapperAnnotation()       // 开启 @Mapper 注解
                            .formatXmlFileName("%sXml") // 格式化 Xml 文件名称

                            // 4.2、service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") // 格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            // 4.3、实体类策略配置
                            .entityBuilder()
                            .enableLombok() // 开启 Lombok
                            // .disableSerialVersionUID(flase)  // 不实现 Serializable 接口，不生产 SerialVersionUID
                            .logicDeleteColumnName("deleted")   // 逻辑删除字段名
                            .naming(NamingStrategy.underline_to_camel)  // 数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    // 数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )   // 添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                            .enableTableFieldAnnotation()       // 开启生成实体时生成字段注解

                            // 4.4、Controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController") // 格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                            .enableRestStyle();  // 开启生成 @RestController 控制器


                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}