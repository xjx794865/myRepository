package com.o98k.o2o.service;

import java.util.List;

import com.o98k.o2o.entity.ShopCategory;



/**
 * 根据查询条件获取ShopCategory列表
 * @author Lenovo
 *
 */

public interface ShopCategoryService {
	
	public  static final String SCLISTKEY ="shopcategorylist";
	
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
