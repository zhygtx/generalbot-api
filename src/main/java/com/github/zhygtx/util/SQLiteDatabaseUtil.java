package com.github.zhygtx.util;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.zhygtx.mapper.PluginDataMapper;
import com.github.zhygtx.pojo.PluginData;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SQLiteDatabaseUtil {

    private static volatile SqlSessionFactory sqlSessionFactory;
    private static final String DB_FILE_NAME = "plugin_data.db";
    private static final String DB_PATH = System.getProperty("user.dir") + File.separator + DB_FILE_NAME;
    private static final String JDBC_URL = "jdbc:sqlite:" + DB_PATH;

    private SQLiteDatabaseUtil() {
    }

    public static synchronized SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try {
                initDatabase();
                
                MybatisConfiguration configuration = new MybatisConfiguration();
                configuration.setMapUnderscoreToCamelCase(true);
                configuration.addMapper(PluginDataMapper.class);
                
                MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
                interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
                
                MybatisSqlSessionFactoryBuilder builder = new MybatisSqlSessionFactoryBuilder();
                sqlSessionFactory = builder.build(configuration);
                
                try (SqlSession session = sqlSessionFactory.openSession()) {
                    PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
                }
                
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize SQLite database", e);
            }
        }
        return sqlSessionFactory;
    }

    private static void initDatabase() throws SQLException {
        File dbFile = new File(DB_PATH);
        boolean isNewDb = !dbFile.exists();
        
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            if (isNewDb) {
                createTables(connection);
            }
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS plugin_data (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id VARCHAR(255) NOT NULL DEFAULT 'test_user',
                plugin_id VARCHAR(255) NOT NULL DEFAULT 'test_plugin',
                data_index VARCHAR(255) NOT NULL,
                data TEXT NOT NULL,
                create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                UNIQUE(data_index)
            );
            """;
        
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    public static SqlSession openSession() {
        return getSqlSessionFactory().openSession(true);
    }

    public static void closeSession(SqlSession session) {
        if (session != null) {
            session.close();
        }
    }

    public static String getDbPath() {
        return DB_PATH;
    }
}