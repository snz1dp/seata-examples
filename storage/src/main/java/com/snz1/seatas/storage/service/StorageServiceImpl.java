package com.snz1.seatas.storage.service;

import com.snz1.seatas.storage.data.Storage;
import com.snz1.seatas.storage.dao.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class StorageServiceImpl implements StorageService {

  @Autowired
  private StorageMapper storageMapper;

  @Autowired
  private DataSource dataSource;

  public void deduct(String commodityCode, int count) {
    Storage storage = get(commodityCode);
    storage.setCount(storage.getCount() - count);
    storageMapper.updateById(storage);
  }

  public Storage get(String id) {
    return storageMapper.selectById(id);
  }

  /**
   * 0.8.0 release
   *
   * @throws SQLException
   */
  public void batchUpdate() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = dataSource.getConnection();
      connection.setAutoCommit(false);
      String sql = "update Storage_tbll set count = ?" + "    where id = ? and commodity_code = ?";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, 100);
      preparedStatement.setLong(2, 1);
      preparedStatement.setString(3, "2001");
      preparedStatement.addBatch();
      preparedStatement.setInt(1, 200);
      preparedStatement.setLong(2, 2);
      preparedStatement.setString(3, "2002");
      preparedStatement.addBatch();
      preparedStatement.setInt(1, 300);
      preparedStatement.setLong(2, 3);
      preparedStatement.setString(3, "2003");
      preparedStatement.addBatch();
      preparedStatement.executeBatch();
      connection.commit();
      System.out.println(1 / 0);
    } catch (Exception e) {
      throw e;
    } finally {
      connection.close();
      preparedStatement.close();
    }
  }

  /**
   * 0.8.0 release
   *
   * @throws SQLException
   */
  public void batchDelete() throws SQLException {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = dataSource.getConnection();
      connection.setAutoCommit(false);
      String sql = "delete from Storage_tbll where  count = ? and commodity_code = ?";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, 11);
      preparedStatement.setString(2, "2001");
      preparedStatement.addBatch();
      preparedStatement.setInt(1, 22);
      preparedStatement.setString(2, "2002");
      preparedStatement.addBatch();
      preparedStatement.setInt(1, 33);
      preparedStatement.setString(2, "2003");
      preparedStatement.addBatch();
      preparedStatement.executeBatch();
      connection.commit();
      System.out.println(1 / 0);
    } catch (Exception e) {
      throw e;
    } finally {
      connection.close();
      preparedStatement.close();
    }
  }

}

/*
 * reference solution:
 * 
 * @Transactional public void deduct(String commodityCode, int count) { //select
 * + for update Storage storage =
 * storageMapper.findByCommodityCode(commodityCode);
 * storage.setCount(storage.getCount() - count);
 * storageMapper.updateById(storage); } 1.select for update,refer
 * https://seata.io/zh-cn/docs/overview/faq.html#4
 * 2.(optional)use @Transactional,keep X locks held until connection submission
 */
