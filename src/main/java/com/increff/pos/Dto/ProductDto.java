package com.increff.pos.Dto;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.ProductForm;
import com.increff.pos.Pojo.BrandPojo;
import com.increff.pos.Pojo.ProductPojo;
import com.increff.pos.Service.BrandService;
import com.increff.pos.Service.InventoryService;
import com.increff.pos.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.*;
import static com.increff.pos.Util.DataUtil.validate;
import static java.util.Objects.isNull;

@Service
public class ProductDto {

    @Autowired
    private ProductService productService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private InventoryService inventoryService;

    public ProductForm add(ProductForm productForm) throws ApiException {
        validate(productForm, "Parameters in the Insert Form cannot be Null");

        Integer brandId = getBrandIdByBrandCategory(productForm.getBrand(), productForm.getCategory());
        ProductPojo productPojo=convertProductFormtoProductPojo(productForm);
        productPojo.setBrandId(brandId);

        productService.add(productPojo);
        return productForm;

    }

    public Integer getBrandIdByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandService.selectByBrandCategory(brand, category);
        if (isNull(brandPojo)) {
            throw new ApiException(brand + " - " + category + "Brand-Category Combination does not exist");
        }
        return brandPojo.getId();
    }



}
