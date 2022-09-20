package com.increff.pos.Dto;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.ProductData;
import com.increff.pos.Model.ProductForm;
import com.increff.pos.Model.ProductUpdateForm;
import com.increff.pos.Pojo.BrandPojo;
import com.increff.pos.Pojo.ProductPojo;
import com.increff.pos.Service.BrandService;
import com.increff.pos.Service.InventoryService;
import com.increff.pos.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.*;
import static com.increff.pos.Util.DataUtil.*;
import static com.increff.pos.Util.ErrorUtil.throwError;
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

    public Integer bulkAdd(List<ProductForm> productFormList)throws ApiException
    {
        if(CollectionUtils.isEmpty(productFormList))
        {
            throw new ApiException("Empty Data");
        }
        checkDuplicates(productFormList);
        validateProductList(productFormList);
        List<ProductPojo> productPojoList=new ArrayList<>();
        for(ProductForm productForm : productFormList)
        {
            Integer brandId=getBrandIdByBrandCategory(productForm.getBrand(), productForm.getCategory());
            ProductPojo productPojo=convertProductFormtoProductPojo(productForm);
            productPojo.setBrandId(brandId);
            productPojoList.add(productPojo);
        }

        productService.bulkAdd(productPojoList);
        return productFormList.size();
    }

    public ProductData get(Integer id)throws ApiException{
        return convertProductPojotoProductData(productService.get(id));
    }
    public List<ProductData> getAll()throws ApiException{
        List<ProductPojo> productPojoList= productService.getAll();
        List<ProductData> productDataList=new ArrayList<>();
        for(ProductPojo productPojo:productPojoList)
        {
            productDataList.add(convertProductPojotoProductData(productPojo));
        }
        return productDataList;
    }

    public ProductUpdateForm update(ProductUpdateForm productUpdateForm)throws ApiException
    {
        validate(productUpdateForm,"Parameters in thr Update form Cannot be Null");
        ProductPojo productPojo=productService.get(productUpdateForm.getId());
        if(!isNull(inventoryService.selectByBarcode(productUpdateForm.getBarcode()))&productUpdateForm.getBarcode()!=productPojo.getBarcode())
        {
            throw new ApiException("Cannot change Barcode ad Inventory exists for this");
        }

        Integer brandId=getBrandIdByBrandCategory(productUpdateForm.getBrand(),productUpdateForm.getCategory());
        ProductPojo productPojoConverted = convertProductFormtoProductPojo(productUpdateForm);
        productService.update(productPojoConverted);
        return productUpdateForm;
    }

    public Integer getBrandIdByBrandCategory(String brand, String category) throws ApiException {
        BrandPojo brandPojo = brandService.selectByBrandCategory(brand, category);
        if (isNull(brandPojo)) {
            throw new ApiException(brand + " - " + category + "Brand-Category Combination does not exist");
        }
        return brandPojo.getId();
    }

    public void validateProductList(List<ProductForm>productFormList)throws ApiException{
        List<String> errorList=new ArrayList<>();
        Integer row=1;
        for(ProductForm productForm:productFormList)
        {
            normalize(productForm);
            if(!checkNotNullBulkUtil(productForm))
            {
                errorList.add("Error : row = " + row + "Parameters in the insert form cannot be null");
                continue;
            }
            if(!validateMRPBulk(productForm.getMrp()))
            {
                errorList.add("Error : row = "+row+" mrp "+productForm.getMrp()+" aint valid, Positive Number it should be");
            }
            if(!validateBarcodeBulk(productForm.getBarcode()))
            {
                errorList.add("Error : row = "+row+ " barcode "+productForm.getBarcode()+" aint valid, Barcode can have only Alphanumeric Values valid by regex");
            }
            if(!isNull(productService.selectByBarcode(productForm.getBarcode())))
            {
                errorList.add("Error : row : =" + row + " barcode " + productForm.getBarcode()+"already Exists");
            }
            BrandPojo brandPojo=brandService.selectByBrandCategory(productForm.getBrand(),productForm.getCategory());
            if(isNull(brandPojo))
            {
                errorList.add("Error : row = " + row + " "+productForm.getBrand()+" - "+productForm.getCategory()+"Brand Categoty doesnt exist");
            }
            row++;
        }
        if(CollectionUtils.isEmpty(errorList))
        {
            throwError(errorList);
        }
    }



}
