package com.o98k.o2o.dao;

import java.util.List;

import com.o98k.o2o.entity.ProductImg;

public interface ProductImgDao {
	
	/**
	 * 批量添加商品的详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	
	/**
	 * 删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
	
	/**
	 * 列出某个商品的详情图列表
	 * 
	 * @param productId
	 * @return
	 */
	List<ProductImg> queryProductImgList(long productId);


}
