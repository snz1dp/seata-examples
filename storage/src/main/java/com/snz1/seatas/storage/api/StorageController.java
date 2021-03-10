package com.snz1.seatas.storage.api;

import com.snz1.seatas.storage.data.Storage;
import com.snz1.seatas.storage.service.StorageService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/api/storage")
@RestController
public class StorageController {

  @Autowired
  StorageService storageService;

  @GetMapping(value = "/deduct")
  public void deduct(@RequestParam String commodityCode, @RequestParam Integer count) throws SQLException {
    System.out.println("storage XID " + RootContext.getXID());
    storageService.deduct(commodityCode, count);
  }

  @GetMapping(value = "/get/{id}")
  public Storage getById(@PathVariable("id") Integer id) {
    return storageService.get(id);
  }

  @GetMapping(value = "/batch/update")
  public void batchUpdateCond() {
    try {
    storageService.batchUpdate();
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
  }

  @GetMapping(value = "/batch/delete")
  public void batchDeleteCond() {
    try {
      storageService.batchDelete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
