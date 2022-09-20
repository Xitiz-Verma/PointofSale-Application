package com.increff.pos.Controller;


import com.increff.pos.Dto.ProductDto;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.ProductData;
import com.increff.pos.Model.ProductForm;
import com.increff.pos.Model.ProductUpdateForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RequestMapping(path="/products")
@RestController
public class ProductController {

    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Add a Product Details")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ProductForm insertProduct(@RequestBody ProductForm productForm) throws ApiException {
        return productDto.add(productForm);
    }

    @ApiOperation(value="Add Bulk Product Data")
    @RequestMapping(path="/upload", method=RequestMethod.POST)
    public Integer bulkInsertProduct(@RequestBody List<@Valid ProductForm> productFormList)throws ApiException
    {
        return productDto.bulkAdd(productFormList);
    }

    @ApiOperation(value = "Gives all Product Details")
    @RequestMapping(path = "", method=RequestMethod.GET)
    public List<ProductData> getAllProductDetails()throws ApiException{
        return productDto.getAll();
    }

    @ApiOperation(value=" Get a product by Id")
    @RequestMapping(path="/{id}",method= RequestMethod.GET)
    public ProductData getProduct(@PathVariable int id)throws ApiException
    {
        return productDto.get(id);
    }

    @ApiOperation(value= " Update a Product ")
    @RequestMapping(path="" , method =RequestMethod.PUT)
    public ProductUpdateForm updateProduct(@RequestBody ProductUpdateForm productUpdateForm)throws ApiException
    {
        return productDto.update(productUpdateForm);
    }

}