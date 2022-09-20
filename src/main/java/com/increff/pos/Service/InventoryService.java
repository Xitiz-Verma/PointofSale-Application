package com.increff.pos.Service;

import com.increff.pos.Dao.InventoryDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.InventoryReport;
import com.increff.pos.Model.InventoryUpdateForm;
import com.increff.pos.Pojo.InventoryPojo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.Util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao inventoryDao;

    public void add(InventoryPojo inventoryPojo)throws ApiException
    {
        if(!isNull(inventoryDao.selectByBarcode(inventoryPojo.getBarcode())))
        {
            throw new ApiException("Inventory Data Alredy Exists");
        }
        if(inventoryPojo.getQuantity()<=0)
        {
            throw new ApiException("Quantity must be Greater than 0");
        }
        inventoryDao.add(inventoryPojo);
    }

    public void bulkAdd(List<InventoryPojo> inventoryPojoList)throws ApiException
    {
        List<String> errorList = new ArrayList<>();
        Integer row =1;
        for(InventoryPojo inventoryPojo : inventoryPojoList)
        {
            if(!isNull(inventoryDao.selectByBarcode(inventoryPojo.getBarcode())))
            {
                errorList.add("Error : row = " + row + " Inventory already exists for barcode "+inventoryPojo.getBarcode());
            }
            if(inventoryPojo.getQuantity()<=0)
            {
                errorList.add("Error : row = "+ row + " Quantity must be greater than 0, Quantity = "+inventoryPojo.getQuantity());
            }
            row++;
        }
        if(CollectionUtils.isEmpty(errorList))
        {
            throwError(errorList);
        }
        for(InventoryPojo inventoryPojo:inventoryPojoList)
        {
            inventoryDao.add(inventoryPojo);
        }

    }
    public InventoryPojo get(Integer id)throws ApiException
    {
        return getCheck(id);
    }
    public List<InventoryPojo> getAll()throws ApiException
    {
        return inventoryDao.selectAll();
    }

    public void update(InventoryUpdateForm inventoryUpdateForm)throws ApiException
    {
        if(inventoryUpdateForm.getQuantity()<0)
        {
            throw new ApiException("Quantity must be greater than 0");
        }
        InventoryPojo inventoryPojo=getCheck(inventoryUpdateForm.getId());
        inventoryPojo.setQuantity(inventoryUpdateForm.getQuantity());
        inventoryDao.update();
    }
    public InventoryPojo getCheck(Integer id)throws ApiException
    {
        InventoryPojo inventoryPojo=inventoryDao.select(id);
        if(isNull(inventoryPojo))
        {
            throw new ApiException("Inventory with given id does not exist, id :"+id);
        }
        return inventoryPojo;
    }
    public InventoryPojo selectByBarcode(String barcode)
    {
        return inventoryDao.selectByBarcode(barcode);
    }

    public InventoryPojo selectByProductId(Integer productId)
    {
        return inventoryDao.selectByProductId(productId);
    }

    public List<InventoryReport> getInventoryReport()
    {
        return inventoryDao.getInventoryReport();
    }
}
