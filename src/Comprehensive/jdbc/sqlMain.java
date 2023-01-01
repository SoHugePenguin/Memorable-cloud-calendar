/**
 * @Project dingLaoZuoYe
 * @File sqlMain
 * @Time 2022/12/28 15:55
 * @ToDo
 * @Author SoHugePenguin
 */
package Comprehensive.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class sqlMain {
    public static Connection connection;
    public static Statement statement;

    public static void druid_connection() throws Exception {
        //1.导入druid.jar
        //2.定义配置文件 -- druid.properties
        //3.加载配置文件
        Properties prop = new Properties();
        InputStream druid_file = sqlMain.class.getResourceAsStream("druid.properties");
        prop.load(druid_file);
        //4.获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        //5.获取数据连接 Connection
        connection = dataSource.getConnection();
        //4.获取执行sql的对象Statement控制台
        statement = connection.createStatement();

        //检测表
        String tableSearch = "show tables like 'calendar_user%';";
        String tableCreate = """
                create table my_first_schema.calendar_user
                (
                    id       int comment '编号',
                    user     varchar(50) comment '账号',
                    password varchar(50) comment '密码'
                ) comment '万年表账号密码数据表';""";
        if (!connection.prepareStatement(tableSearch).executeQuery().next()) {
            connection.prepareStatement(tableCreate).executeUpdate();
        }

        String tableSearch2 = "show tables like 'calendar_data%';";
        String tableCreate2 = """
                create table my_first_schema.calendar_data
                (
                    user varchar(50) comment '账号',
                    date date comment '日期',
                    id int comment '编号',
                    event varchar(15000) comment '事件'
                ) comment '万年表事件集';""";
        if (!connection.prepareStatement(tableSearch2).executeQuery().next()) {
            connection.prepareStatement(tableCreate2).executeUpdate();
        }
    }
}
