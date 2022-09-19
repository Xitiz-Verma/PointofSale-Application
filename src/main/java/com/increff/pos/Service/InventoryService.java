package com.increff.pos.Service;

import com.increff.pos.Dao.InventoryDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.InventoryPojo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;




    public InventoryPojo selectByBarcode(String barcode)
    {
        return inventoryDao.selectByBarcode(barcode);
    }
}
