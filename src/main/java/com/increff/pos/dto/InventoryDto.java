package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.DataUI.InventoryDataUI;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReport;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.pos.dto.dtoHelper.InventoryDtoHelper.*;
import static com.increff.pos.util.DataUtil.checkNotNullBulkUtil;
import static com.increff.pos.util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    public InventoryData get(String barcode)throws ApiException
    {
        return convertInventoryPojotoInventoryData(inventoryService.selectByBarcode(barcode));
    }
    public List<InventoryData> getAll()throws ApiException
    {
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList)
        {
            inventoryDataList.add(convertInventoryPojotoInventoryData(inventoryPojo));
        }
        return inventoryDataList;
    }

    public InventoryDataUI add(InventoryForm inventoryForm)throws ApiException
    {
        validateInventoryForm(inventoryForm);
        inventoryForm=normalize(inventoryForm);
        InventoryPojo inventoryPojo = convertInventoryFormtoInventoryPojo(inventoryForm);
        inventoryService.add(addProductId(inventoryPojo));
        return convertInventoryFormtoInventoryDataUI(inventoryForm);
    }

    public Integer bulkAdd(List<InventoryForm> inventoryFormList)throws ApiException
    {
        if(CollectionUtils.isEmpty(inventoryFormList))
        {
            throw new ApiException("Empty Inventory Form List");
        }
        validateInventoryList(inventoryFormList);
        List<InventoryPojo> inventoryPojoList=new ArrayList<>();
        for(InventoryForm inventoryForm : inventoryFormList)
        {
            InventoryPojo inventoryPojo=convertInventoryFormtoInventoryPojo(inventoryForm);
            inventoryPojoList.add(addProductId(inventoryPojo));
        }
        inventoryService.bulkAdd(inventoryPojoList);
        return inventoryPojoList.size();
    }

    public InventoryDataUI update(InventoryForm inventoryForm)throws ApiException
    {
        validateInventoryForm(inventoryForm);
        inventoryForm=normalize(inventoryForm);
        inventoryService.update(inventoryForm);
        return convertInventoryFormtoInventoryDataUI(inventoryForm);
    }

    private void validateInventoryList(List<InventoryForm> inventoryFormList)throws ApiException
    {
        List<String> errorList = new ArrayList<>();
        Set<String> barcodeSet = new HashSet<>();
        Integer row = 1;
        for(InventoryForm inventoryForm : inventoryFormList)
        {
            if(!checkNotNullBulkUtil(inventoryForm))
            {
                errorList.add("Error : row = "+ row +  "barcode or quantity cant be Null");
                continue;
            }
            ProductPojo productPojo = productService.selectByBarcode(inventoryForm.getBarcode());

            if(isNull(productPojo))
            {
                errorList.add("Error : row = "+ row + " product with barcode "+ inventoryForm.getBarcode() + "does not exist");
            }
            if(barcodeSet.contains(inventoryForm.getBarcode()))
            {
                errorList.add("Error : row = "+ row + " barcode should not be repeated , barcode : "+inventoryForm.getBarcode());
            }
            else
            {
                barcodeSet.add(inventoryForm.getBarcode());
            }
        }
        if(!CollectionUtils.isEmpty(errorList))
            throwError(errorList);
    }

    private InventoryPojo addProductId(InventoryPojo inventoryPojo)throws ApiException
    {
        ProductPojo productPojo = new ProductPojo();
        if(isNull(productPojo))
        {
            throw new ApiException("Product with this Barcode does not exist");
        }
        int productId = productPojo.getId();
        inventoryPojo.setProductId(productId);
        return inventoryPojo;
    }

    public void validateInventoryForm(InventoryForm inventoryForm)throws ApiException
    {
        if(isNull(inventoryForm.getBarcode()) || inventoryForm.getBarcode().isEmpty())
        {
            throw new ApiException("Brand cannot be Empty!");
        }
        if(isNull(inventoryForm.getQuantity()) || inventoryForm.getQuantity()<0)
        {
            throw new ApiException("Category cannot be Empty or Negative!");
        }
    }

    public InventoryForm normalize(InventoryForm inventoryForm){
        inventoryForm.setBarcode(inventoryForm.getBarcode().trim());
        inventoryForm.setQuantity(inventoryForm.getQuantity());
        return inventoryForm;
    }

    public List<InventoryReport> getInventoryReport()
    {
        return inventoryService.getInventoryReport();
    }

}
