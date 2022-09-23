package com.increff.pos.Controller;

import com.increff.pos.Dto.InventoryDto;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.InventoryData;
import com.increff.pos.Model.InventoryForm;
import com.increff.pos.Model.InventoryReport;
import com.increff.pos.Pojo.InventoryPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RequestMapping(path="/inventory")
@RestController
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value="Gets a Inventory Details by Id")
    @RequestMapping(path="/{id}", method =RequestMethod.GET)
    public InventoryData getInventory(@PathVariable String barcode)throws ApiException{
        return inventoryDto.get(barcode);
    }

    @ApiOperation(value="Gets all Inventory Details")
    @RequestMapping(path="",method= RequestMethod.GET)
    public List<InventoryData> getAllInventoryDetails()throws ApiException
    {
        return inventoryDto.getAll();
    }

    @ApiOperation(value="Adds Inventory Details")
    @RequestMapping(path="",method = RequestMethod.POST)
    public InventoryForm insertInventory(@RequestBody InventoryForm inventoryForm)throws ApiException
    {
        return inventoryDto.add(inventoryForm);
    }

    @ApiOperation(value="Adds Incentory Details in Bulk")
    @RequestMapping(path="/upload",method = RequestMethod.POST)
    public Integer bulkInsertInventory(@RequestBody List<InventoryForm> inventoryFormList)throws ApiException
    {
        return inventoryDto.bulkAdd(inventoryFormList);
    }

    @ApiOperation(value="Updates an Inventory")
    @RequestMapping(path="/{id}", method = RequestMethod.PUT)
    public InventoryForm updateInventory(@RequestBody InventoryForm  inventoryForm,@PathVariable Integer id)throws ApiException
    {
        return inventoryDto.update(inventoryForm);
    }

    @ApiOperation(value="Gets Inventory Report")
    @RequestMapping(path="/inventory-reports", method=RequestMethod.GET)
    public List<InventoryReport> getInventoryReport()
    {
        return inventoryDto.getInventoryReport();
    }

}
