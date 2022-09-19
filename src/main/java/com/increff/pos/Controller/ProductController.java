package com.increff.pos.Controller;


import com.increff.pos.Dto.ProductDto;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.ProductForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
public class ProductController {

    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Add a Product Details")
    @RequestMapping(path = "/products", method = RequestMethod.POST)
    public ProductForm insertProduct(@RequestBody ProductForm productForm) throws ApiException {
        return productDto.add(productForm);
    }


}