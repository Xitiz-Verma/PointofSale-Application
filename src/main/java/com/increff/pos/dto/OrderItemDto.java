package com.increff.pos.dto;

import com.increff.pos.exception.ApiException;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.OrderItemUpdateForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.dto.dtoHelper.OrderItemDtoHelper.convertOrderItemFormToOrderItemPojo;
import static com.increff.pos.dto.dtoHelper.OrderItemDtoHelper.convertOrderItemPojoToOrderItemData;
import static com.increff.pos.util.DataUtil.validate;
import static java.util.Objects.isNull;

@Service
public class OrderItemDto {

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    public OrderItemForm add(OrderItemForm orderItemForm)throws ApiException
    {
        validateOrderItemForm(orderItemForm);
        Integer availableQty = getAvailQty(orderItemForm.getBarcode());

        InventoryForm inventoryForm = new InventoryForm();
        if(orderItemForm.getQuantity()<=0)
        {
            throw new ApiException("Quantity shoud be greater than 0");
        }
        String barcode = inventoryService.selectByBarcode(orderItemForm.getBarcode()).getBarcode();
        inventoryForm.setBarcode(barcode);
        inventoryForm.setQuantity(availableQty-orderItemForm.getQuantity());
        inventoryService.update(inventoryForm);

        orderItemService.add(convertOrderItemFormToOrderItemPojo(orderItemForm));
        return orderItemForm;
    }

    public List<OrderItemData> getAll()throws ApiException
    {
        List<OrderItemPojo> orderItemPojoList=orderItemService.getAll();
        List<OrderItemData> orderItemDataList=new ArrayList<>();
        for(OrderItemPojo orderItemPojo : orderItemPojoList)
        {
            orderItemDataList.add(convertOrderItemPojoToOrderItemData(orderItemPojo));
        }
        return orderItemDataList;
    }

    public List<OrderItemData> getOrderItemForOrder(Integer orderId)throws ApiException
    {
        OrderPojo orderPojo= orderService.get(orderId);
        if(isNull(orderPojo))
        {
            throw new ApiException("Order with this id does not exist, OrderId : "+orderId);
        }

        List<OrderItemPojo> orderItemPojoList=orderItemService.selectFromOrderId(orderId);
        List<OrderItemData> orderItemDataList=new ArrayList<>();

        for(OrderItemPojo orderItemPojo:orderItemPojoList)
        {
            orderItemDataList.add(convertOrderItemPojoToOrderItemData(orderItemPojo));
        }
        return orderItemDataList;
    }

    @Transactional(rollbackFor = ApiException.class)
    public OrderItemUpdateForm update(OrderItemUpdateForm orderItemUpdateForm)throws ApiException
    {
        validate(orderItemUpdateForm,"Body values cant be Null");
        int orderId=orderItemService.get(orderItemUpdateForm.getId()).getOrderId();
        if(checkOrderStatus(orderId))
        {
            throw new ApiException("Cannot update as Order is already Placed for Order id : "+orderId);
        }
        updateInventoryQty(orderItemUpdateForm);
        orderItemService.update(convertOrderItemFormToOrderItemPojo(orderItemUpdateForm));
        return orderItemUpdateForm;

    }

    public Integer delete(Integer id)throws ApiException
    {
        int orderId=orderItemService.get(id).getOrderId();
        if(checkOrderStatus(orderId))
        {
            throw new ApiException("Cannot Delete as Order is alreasy placed");
        }

        OrderItemPojo orderItemPojo=orderItemService.get(id);
        InventoryPojo inventoryPojo= inventoryService.selectByBarcode(orderItemPojo.getBarcode());

        if(isNull(inventoryPojo))
        {
            throw new ApiException("Product with given id does nopt exist in the inventory");
        }

        int availqty=inventoryService.get(inventoryPojo.getBarcode()).getQuantity();
        InventoryForm inventoryForm=new InventoryForm();
        inventoryForm.setBarcode(inventoryPojo.getBarcode());
        inventoryForm.setQuantity(availqty+orderItemPojo.getQuantiy());

        inventoryService.update(inventoryForm);
        orderItemService.delete(id);
        return id;

    }


    @Transactional(rollbackFor = ApiException.class)
    private void validateOrderItemForm(OrderItemForm orderItemForm)throws ApiException
    {
        validate(orderItemForm,"Body values cant be Null");

        if(orderItemService.selectFromOrderIdBarcode(orderItemForm.getOrderId(),orderItemForm.getBarcode()) != null)
        {
            throw new ApiException("OrderItem already exists ..Kindly update that instead");
        }

        OrderPojo orderPojo= orderService.get(orderItemForm.getOrderId());
        if(isNull(orderPojo))
        {
            throw new ApiException("Order with this id does not exist, Order id : "+orderItemForm.getOrderId());
        }

        InventoryPojo inventoryPojo = inventoryService.get(orderItemForm.getBarcode());
        if(isNull(inventoryPojo))
        {
            throw new ApiException("Product with this Barcode does not exist , Barcode : "+orderItemForm.getBarcode());
        }

        int availqty=getAvailQty(orderItemForm.getBarcode());
        if(orderItemForm.getQuantity()>availqty)
        {
            throw new ApiException("Selected Quantity more than available Quantity \n,Of now the available quantity is only "+availqty);
        }

        int orderId=orderItemForm.getOrderId();
        if(checkOrderStatus(orderId))
        {
            throw new ApiException("Cannot add as order akready placed for order id, OrderID : "+orderItemForm.getOrderId());
        }

    }

    private boolean checkOrderStatus(Integer orderId)throws ApiException
    {
        OrderPojo orderPojo= orderService.get(orderId);
        return orderPojo.getOrderPlaced();
    }

    private Integer getAvailQty(String barcode)throws ApiException
    {
        InventoryPojo inventoryPojo=inventoryService.selectByBarcode(barcode);
        if(isNull(inventoryPojo))
        {
            throw new ApiException("Product with this Barcode does not exist int he Inventory, id : "+barcode);
        }
        //return inventoryService.get(inventoryPojo.getBarcode()).getQuantity();
        return inventoryPojo.getQuantity();
    }

    @Transactional(rollbackFor = ApiException.class)
    private void updateInventoryQty(OrderItemUpdateForm orderItemUpdateForm)throws ApiException
    {
        if(orderItemUpdateForm.getQuantity()<=0)
        {
            throw new ApiException("Quantity must be greater than 0");
        }

        OrderItemPojo orderItemExist = orderItemService.get(orderItemUpdateForm.getId());

        InventoryPojo inventoryPojo= inventoryService.selectByBarcode(orderItemExist.getBarcode());
        if(isNull(inventoryPojo))
        {
            throw new ApiException("Product with given barcode does not exist ,barocde : "+orderItemExist.getBarcode());
        }

        int availQty=inventoryService.get(inventoryPojo.getBarcode()).getQuantity();
        if(!(availQty + orderItemExist.getQuantiy() >= orderItemUpdateForm.getQuantity()))
        {
            throw new ApiException("Selected Quantity is more than available quantity , Availale Quantity is : "+availQty);
        }

        InventoryForm invUpdate = new InventoryForm();
        invUpdate.setBarcode(inventoryPojo.getBarcode());
        invUpdate.setQuantity(availQty+orderItemExist.getQuantiy()-orderItemUpdateForm.getQuantity());
        inventoryService.update(invUpdate);
    }

}
