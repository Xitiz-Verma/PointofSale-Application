package com.increff.pos.Service;

import com.increff.pos.Dao.ProductDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static java.util.Objects.isNull;

import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.validateBarcode;
import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.validateMRP;
import static com.increff.pos.Util.DataUtil.normalize;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public void add(ProductPojo productPojo)throws ApiException{
        normalize(productPojo);
        validateBarcode(productPojo.getBarcode());
        validateMRP(productPojo.getMrp());
        checkBarcodeExist(productPojo.getBarcode());
        productDao.add(productPojo);

    }

    public List<ProductPojo> selectByBrandId(Integer brandId) {
        return productDao.selectByBrandId(brandId);
    }

    private void checkBarcodeExist(String barcode)throws ApiException
    {
        if(!isNull(productDao.selectByBarcode(barcode)))
        {
            throw new ApiException("barcode" + barcode + "does not exist");
        }

    }

}
