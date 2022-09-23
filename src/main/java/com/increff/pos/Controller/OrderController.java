package com.increff.pos.Controller;

import com.increff.pos.Dto.OrderDto;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.OrderData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Api
@RequestMapping(path="/orders")
@RestController
public class OrderController {

    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value="Get a Order Details")
    @RequestMapping(path="/{id}",method=RequestMethod.GET)
    public OrderData getOrderData(@PathVariable int id) throws ApiException
    {
        return orderDto.get(id);
    }

    @ApiOperation(value="Gives all Order Details")
    @RequestMapping(path="",method= RequestMethod.GET)
    public List<OrderData> getAllOrderDetails()throws ApiException
    {
        return orderDto.getAll();
    }

    @ApiOperation(value="Adds an Order Details")
    @RequestMapping(path="", method = RequestMethod.POST)
    public ZonedDateTime insertOrder()throws ApiException
    {
        return orderDto.add();
    }

    @ApiOperation(value="Set Order Status Placed")
    @RequestMapping(path="/{id}/place-order",method=RequestMethod.PUT)
    public Integer setOrderStatusPlaced(@PathVariable int id)throws ApiException
    {
        return orderDto.updateOrderStatusPlaced(id);
    }

    @ApiOperation(value="Get Order Invoice for Order Id")
    @RequestMapping(path="/{orderId}/invoices", method = RequestMethod.GET)
    public byte[] getOrderInvoice(@PathVariable int orderId)throws ApiException, IOException, TransformerException
    {
        return orderDto.getOrderInvoice(orderId);
    }









}
