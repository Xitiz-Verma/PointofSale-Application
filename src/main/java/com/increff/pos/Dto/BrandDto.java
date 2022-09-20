package com.increff.pos.Dto;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.BrandData;
import com.increff.pos.Model.BrandForm;
import com.increff.pos.Pojo.BrandPojo;
import com.increff.pos.Service.BrandService;
import com.increff.pos.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.Dto.DtoHelper.BrandDtoHelper.*;
import static com.increff.pos.Util.DataUtil.validate;

@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    public List<BrandData> getAll() throws ApiException {
        List<BrandPojo> brandPojoList = brandService.getAll();
        List<BrandData> brandDataList = new ArrayList<>();
        for (BrandPojo brandPojo : brandPojoList) {
            brandDataList.add(convertBrandPojoToBrandData(brandPojo));
        }
        return brandDataList;
    }

    public BrandForm add(BrandForm brandForm) throws ApiException {
        validate(brandForm, "Neither Brand nor Category cannot be NULL");
        brandService.add(convertBrandFormtoBrandPojo(brandForm));
        return brandForm;
    }

    public Integer bulkAdd(List<BrandForm> brandFormList) throws ApiException {
        if (brandFormList.size() == 0)
            throw new ApiException("Empty Data");

        validateList(brandFormList);
        checkDuplicates(brandFormList);

        brandService.bulkAdd(convertBrandFormtoBrandPojoList(brandFormList));

        return brandFormList.size();

    }


    public BrandData get(Integer id) throws ApiException {
        return convertBrandPojoToBrandData(brandService.get(id));
    }

    public BrandData update(BrandData brandData) throws ApiException {
        validate(brandData, "brand or category cannot be null");
        if (productService.selectByBrandId(brandData.getId()).isEmpty()) {
            throw new ApiException("Cannot Update" + brandData.getBrand() + " - " + brandData.getCategory() + "as product for this exists");
        }
        brandService.update(convertBrandDatatoBrandPojo(brandData));
        return brandData;
    }

}