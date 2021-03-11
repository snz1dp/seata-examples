package com.snz1.seatas.storage.conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Initializer {

  @Autowired
  private Environment environment;

  @PostConstruct
  public void createDatabase() {
    String databaseUsername = environment.getProperty("spring.datasource.username", "postgres");
    String databasePassword = environment.getProperty("spring.datasource.password", "123456");
    String postgresHost = environment.getProperty("PG_HOST", "localhost");
    Integer postgresPort = Integer.parseInt(environment.getProperty("PG_PORT", "5432"));
    String databaseName = environment.getProperty("PG_DATABASE", "seatas_example_storage");

    Connection conn;
    String jdbcURL = String.format("jdbc:postgresql://%s:%d/postgres", postgresHost, postgresPort);
    try {
      conn = DriverManager.getConnection(jdbcURL, databaseUsername, databasePassword);
    } catch (SQLException e) {
      throw new IllegalStateException("创建数据库连接失败: " + e.getMessage(), e);
    }
    ResultSet result = null;
    try {
      Statement stmt = conn.createStatement();
      result = stmt.executeQuery(String.format("SELECT u.datname FROM pg_catalog.pg_database u where u.datname='%s';", databaseName));
      if (!result.next()) {
        String createSQL = String.format("CREATE DATABASE %s WITH OWNER %s;", databaseName, databaseUsername);
        log.info("执行SQL语句: " + createSQL);
        stmt.execute(createSQL);
      }
    } catch(SQLException e) {
      if (!StringUtils.contains(e.getMessage(), "already exists")) {
        throw new IllegalStateException("创建数据库失败: " + e.getMessage(), e);
      }
    } finally {
      try {
        result.close();
        conn.close();
      } catch(Throwable e) {
      }
    }
  }

}
