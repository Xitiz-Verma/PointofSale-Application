package com.increff.pos.Service;

import com.increff.pos.Dao.BrandDao;
import com.increff.pos.Exception.ApiException;
import com.increff.pos.Pojo.BrandPojo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.Util.DataUtil.normalize;
import static com.increff.pos.Util.DataUtil.validate;
import static com.increff.pos.Util.ErrorUtil.throwError;
import static java.util.Objects.isNull;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    public List<BrandPojo> getAll() {
        return brandDao.selectAll();

    }

    public void add(BrandPojo brandPojo) throws ApiException {
        normalize(brandPojo);
        checkUnique(brandPojo);
        brandDao.add(brandPojo);
    }

    public void bulkAdd(List<BrandPojo> brandPojoList) throws ApiException {
        for (BrandPojo brandPojo : brandPojoList) {
            normalize(brandPojo);
        }
        List<String> errorList = new ArrayList<>();
        Integer row = 1;
        for (BrandPojo brandPojo : brandPojoList) {
            if (!isNull(selectByBrandCategory(brandPojo.getBrand(), brandPojo.getCategory()))) {
                errorList.add("Error : Row ->" + (row) + " " + brandPojo.getBrand() + " - " + brandPojo.getCategory() + "pair Already exists");
            }
            row++;
        }
        if (!CollectionUtils.isEmpty(errorList))
            throwError(errorList);

        for (BrandPojo brandPojo : brandPojoList) {
            add(brandPojo);
        }

    }

    public BrandPojo get(Integer id) throws ApiException {
        return getCheck(id);
    }

    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo brandPojo = brandDao.select(id);
        if (isNull(brandPojo)) {
            throw new ApiException("Brand with given id does not exist,id :" + id);
        }
        return brandPojo;
    }

    public void update(BrandPojo brandPojo) throws ApiException {
        validate(brandPojo, "Brand or Category cannot be empty");
        normalize(brandPojo);
        checkUnique(brandPojo);

        BrandPojo exists = getCheck(brandPojo.getId());
        exists.setBrand(brandPojo.getBrand());
        exists.setCategory(brandPojo.getCategory());
        brandDao.update();
    }

    public BrandPojo selectByBrandCategory(String brand, String category) {
        return brandDao.selectByBrandCategory(brand, category);
    }

    public BrandPojo selectByBrand(String brand) {
        return brandDao.selectByBrand(brand);
    }

    public BrandPojo selectByCategory(String category) {
        return brandDao.selectByCategory(category);
    }

    private void checkUnique(BrandPojo brandPojo) throws ApiException {
        if (selectByBrandCategory(brandPojo.getBrand(), brandPojo.getCategory()) != null) {
            throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + "pair already exists");
        }
    }


}
