package com.o98k.o2o.service;

import java.util.List;

import com.o98k.o2o.dto.ProductCategoryExecution;
import com.o98k.o2o.entity.ProductCategory;
import com.o98k.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	/**
	 * 查询指定某个店铺下的所有商品类别信息
	 * 
	 * @Param long shopId
	 * @return List<ProductCategory>
	 * 
	 */
	
	List<ProductCategory> getProductCategoryList(long shopId);

	/**
	 * 批量增加商品种类
	 * @param productCategoryList
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution batchAddProductCategory(List<ProductCategory>productCategoryList) throws ProductCategoryOperationException;
	
	/**
	 * 将此类别下的商品里的类别id置为空，再删除掉该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws ProductCategoryOperationException;
	
}
