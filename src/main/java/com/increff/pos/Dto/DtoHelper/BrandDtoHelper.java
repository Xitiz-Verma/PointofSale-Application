package com.increff.pos.Dto.DtoHelper;

import com.increff.pos.Exception.ApiException;
import com.increff.pos.Model.BrandData;
import com.increff.pos.Model.BrandForm;
import com.increff.pos.Pojo.BrandPojo;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.pos.Util.DataUtil.checkNotNullBulkUtil;
import static com.increff.pos.Util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

public class BrandDtoHelper {

    //Static Conversions
    public static BrandData convertBrandPojoToBrandData(BrandPojo brandPojo) {
        BrandData brandData = new BrandData();
        brandData.setId(brandPojo.getId());
        brandData.setBrand(brandPojo.getBrand());
        brandData.setCategory(brandData.getCategory());
        return brandData;

    }

    public static BrandPojo convertBrandFormtoBrandPojo(BrandForm brandForm) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brandForm.getBrand());
        brandPojo.setCategory(brandForm.getCategory());
        return brandPojo;
    }

    public static BrandPojo convertBrandDatatoBrandPojo(BrandData brandData) {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setId(brandData.getId());
        brandPojo.setBrand(brandData.getBrand());
        brandPojo.setCategory(brandData.getCategory());
        return brandPojo;
    }

    public static void validateList(List<BrandForm> brandFormList) throws ApiException {
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        for (BrandForm brandForm : brandFormList) {
            if (!checkNotNullBulkUtil(brandForm)) {
                errorList.add("Error : row ->" + (row) + "brand or category cannot be empty");
            }
            row++;
        }
        if (errorList.size() > 0) {
            String errorStr = "";
            for (String e : errorList) {
                errorStr += e + "\n";
            }
            throw new ApiException(errorStr);
        }
    }


    public static void checkDuplicates(List<BrandForm> brandFormList) throws ApiException {
        Set<String> brandSet = new HashSet<>();
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        for (BrandForm brandForm : brandFormList) {
            if (brandSet.contains(brandForm.getBrand() + brandForm.getCategory())) {
                errorList.add("Error : row ->" + (row) + "Brand-Category should not be repeated, BrandCategory : " + brandForm.getBrand() + "-" + brandForm.getCategory());
            } else {
                brandSet.add(brandForm.getBrand() + brandForm.getCategory());
            }
            row++;
        }
        if (!CollectionUtils.isEmpty(errorList)) {
            throwError(errorList);
        }
    }
}