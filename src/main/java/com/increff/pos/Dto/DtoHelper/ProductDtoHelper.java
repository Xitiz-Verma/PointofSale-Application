package com.increff.pos.Dto.DtoHelper;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.ProductForm;
import com.increff.pos.Pojo.ProductPojo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductDtoHelper {


    public static ProductPojo convertProductFormtoProductPojo(ProductForm productForm)
    {
        ProductPojo productPojo=new ProductPojo();
        productPojo.setName(productPojo.getName());
        productPojo.setBrand(productPojo.getBrand());
        productPojo.setCategory(productPojo.getCategory());
        productPojo.setBarcode(productPojo.getBarcode());
        productPojo.setMrp(productPojo.getMrp());
        return productPojo;

    }

    public static void validateBarcode(String barcode)throws ApiException
    {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher matcher = pattern.matcher(barcode);
        if(!matcher.find())
        {
            throw new ApiException("barcode " + barcode + " not valid,barcode can only have alphanumerc values");
        }
    }

    public static void validateMRP(Double mrp)throws ApiException
    {
        Pattern pattern = Pattern.compile("^[0-9]+$|^[0-9]+\\.[0-9]*$");
        Matcher matcher=pattern.matcher(mrp.toString());
        if(!matcher.find())
        {
            throw new ApiException("Mrp " + mrp + "not vlaid, mrp should be a Positive Nume=ber");
        }
    }

}
