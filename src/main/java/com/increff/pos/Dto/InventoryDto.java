package com.increff.pos.Dto;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.InventoryData;
import com.increff.pos.Model.InventoryForm;
import com.increff.pos.Model.InventoryReport;
import com.increff.pos.Model.InventoryUpdateForm;
import com.increff.pos.Pojo.InventoryPojo;
import com.increff.pos.Pojo.ProductPojo;
import com.increff.pos.Service.InventoryService;
import com.increff.pos.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.pos.Dto.DtoHelper.InventoryDtoHelper.convertInventoryFormtoInventoryPojo;
import static com.increff.pos.Dto.DtoHelper.InventoryDtoHelper.convertInventoryPojotoInventoryData;
import static com.increff.pos.Util.DataUtil.checkNotNullBulkUtil;
import static com.increff.pos.Util.DataUtil.validate;
import static com.increff.pos.Util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
public class InventoryDto {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductService productService;

    public InventoryData get(Integer id)throws ApiException
    {
        return convertInventoryPojotoInventoryData(inventoryService.get(id));
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

    public InventoryForm add(InventoryForm inventoryForm)throws ApiException
    {
        validate(inventoryForm,"barcode and quantity cant be null");
        InventoryPojo inventoryPojo = convertInventoryFormtoInventoryPojo(inventoryForm);
        inventoryService.add(addProductId(inventoryPojo));
        return inventoryForm;
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

    public InventoryUpdateForm update(InventoryUpdateForm inventoryUpdateForm)throws ApiException
    {
        validate(inventoryUpdateForm,"Quantity cant be Null");
        inventoryService.update(inventoryUpdateForm);
        return inventoryUpdateForm;
    }

    public List<InventoryReport> getInventoryReport()
    {
        return inventoryService.getInventoryReport();
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
}
