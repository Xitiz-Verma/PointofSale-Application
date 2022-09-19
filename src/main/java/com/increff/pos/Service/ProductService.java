package com.increff.pos.Service;

import com.increff.pos.Dao.ProductDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.ProductPojo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import static java.util.Objects.isNull;

import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.validateBarcode;
import static com.increff.pos.Dto.DtoHelper.ProductDtoHelper.validateMRP;
import static com.increff.pos.Util.DataUtil.normalize;

@Service
@Transactional(rollbackOn = ApiException.class)
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

    public void bulkAdd(List<ProductPojo> productPojoList)throws ApiException
    {
        for(ProductPojo productPojo : productPojoList)
        {
            add(productPojo);
        }
    }

    public ProductPojo get(int id)throws ApiException{
        return getCheck(id);
    }
    public List<ProductPojo> getAll()throws ApiException
    {
        return productDao.selectAll();
    }

    public void update(ProductPojo productPojo)throws ApiException
    {
        normalize(productPojo);
        validateBarcode(productPojo.getBarcode());
        validateMRP(productPojo.getMrp());

        if(productDao.selectByBarcodeNotEqualId(productPojo.getBarcode(),productPojo.getId())!=null)
        {
            throw new ApiException("Bacode " + productPojo.getBarcode() + "already exists");
        }

    }

    public List<ProductPojo> selectByBrandId(Integer brandId) {
        return productDao.selectByBrandId(brandId);
    }

    public ProductPojo selectByBarcode(String barcode)
    {
        return productDao.selectByBarcode(barcode);
    }

    public ProductPojo getCheck(Integer id)throws ApiException{
        ProductPojo productPojo=productDao.select(id);
        if(productPojo == null)
        {
            throw new ApiException("Product with given id does not exist, id : "+id);
        }
        return productPojo;
    }
    private void checkBarcodeExist(String barcode)throws ApiException
    {
        if(!isNull(productDao.selectByBarcode(barcode)))
        {
            throw new ApiException("barcode" + barcode + "does not exist");
        }

    }

}
