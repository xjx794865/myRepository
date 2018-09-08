package com.o98k.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.o98k.o2o.entity.Shop;

public interface ShopDao {
	
	/*
	 * 分页查询店铺，可输入的条件有:店铺名 (模糊)，店铺状态，店铺类别，区域Id，owner
	 * @Param rowIndex 从第几行开始取数据
	 * @Param pageSize 返回的条数
	 * @Param shopCondition 查询条件 
	 */        
	
	//@param的作用：要传入三个参数，通过param来确定其唯一标识
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize") int pageSize);
	
	//返回queryShopList总数
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	//用过shop id 查询店铺
	Shop queryByShopId(long shopId);
	
	//新增店铺
	int insertShop(Shop shop);
	
	//更新店铺信息
	int updateShop(Shop shop);

}
