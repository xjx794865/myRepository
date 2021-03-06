package com.o98k.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.o98k.o2o.BaseTest;
import com.o98k.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	
	@Autowired 
	private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
    	List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
   
		assertEquals(2,shopCategoryList.size());
    }

}
