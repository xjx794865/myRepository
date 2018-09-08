package com.o98k.o2o.service;

import java.io.InputStream;

import com.o98k.o2o.dto.ImageHolder;
import com.o98k.o2o.dto.ShopExecution;
import com.o98k.o2o.entity.Shop;
import com.o98k.o2o.exceptions.ShopOperationException;

public interface ShopService {
	
	/**
	 * 根据shopContidion分页返回相应列表数据
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	
	//注册店铺信息，包括图片信息
    ShopExecution addShop(Shop shop , ImageHolder thumbnail) throws ShopOperationException;
    
    //通过店铺ID获取店铺信息
    Shop getByShopId(long shopId);
    
    //更新店铺信息，包括对图片的处理
    ShopExecution modifyShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;
}
