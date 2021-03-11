package com.snz1.seatas.storage.api;

import com.snz1.seatas.storage.data.Storage;
import com.snz1.seatas.storage.service.StorageService;
import io.seata.core.context.RootContext;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gateway.api.Result;
import gateway.api.Return;

import java.sql.SQLException;

@RequestMapping("/inventories")
@RestController
public class StorageController {

  @Autowired
  StorageService storageService;

  @PostMapping(value = "/{commodityCode}/deduct")
  public Result deduct(
    @PathVariable("commodityCode") String commodityCode, @RequestParam Integer count
  ) throws SQLException {
    System.out.println("storage XID " + RootContext.getXID());
    storageService.deduct(commodityCode, count);
    return Return.success();
  }

  @GetMapping(value = "/{commodityCode}")
  public Return<Storage> getById(@PathVariable("commodityCode") String id) {
    Storage s = storageService.get(id);
    Validate.notNull(s);
    return Return.wrap(s);
  }

  @PostMapping(value = "/batch_update")
  public Result batchUpdateCond() {
    try {
      storageService.batchUpdate();
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage(), e);
    }
    return Return.success();
  }

  @DeleteMapping(value = "/batch_delete")
  public Result batchDeleteCond() {
    try {
      storageService.batchDelete();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Return.success();
  }

}
