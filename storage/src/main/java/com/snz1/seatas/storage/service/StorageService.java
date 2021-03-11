package com.snz1.seatas.storage.service;

import com.snz1.seatas.storage.data.Storage;

public interface StorageService {
    
  void deduct(String commodityCode, int count);

  Storage get(String id);

  void batchUpdate() throws Exception;

  void batchDelete() throws Exception;

}
