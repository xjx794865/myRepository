package com.o98k.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.o98k.o2o.BaseTest;
import com.o98k.o2o.dto.ImageHolder;
import com.o98k.o2o.dto.ShopExecution;
import com.o98k.o2o.entity.Area;
import com.o98k.o2o.entity.PersonInfo;
import com.o98k.o2o.entity.Shop;
import com.o98k.o2o.entity.ShopCategory;
import com.o98k.o2o.enums.ShopStateEnum;
import com.o98k.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	
	@Test
	public void testGetShopList() {
		Shop shopCondition =new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数量："+se.getShopList().size());
		System.out.println("店铺总数："+se.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		 File shopImg = new File("D:\\picture\\002.jpg");
		 InputStream is = new FileInputStream(shopImg);
		 ImageHolder imageHolder = new ImageHolder("002.jpg",is);
		 ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);
		 System.out.println("新的图片地址为："+ shopExecution.getShop().getShopImg());
		
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException{
	  Shop shop=new Shop();
   	  PersonInfo owner = new PersonInfo();
   	  Area area = new Area();
   	  ShopCategory shopCategory = new ShopCategory();
   	  owner.setUserId(1L);
   	  area.setAreaId(2);
   	  shopCategory.setShopCategoryId(1L);
   	  shop.setArea(area);
   	  shop.setOwner(owner);
   	  shop.setShopCategory(shopCategory);
   	  shop.setShopName("测试的店铺4.0");
   	  shop.setShopDesc("test4.0");
   	  shop.setShopAddr("test4.0");
   	  shop.setPhone("8080");
   	  shop.setCreateTime(new Date());
   	  shop.setEnableStatus(ShopStateEnum.CHECK.getState());
   	  shop.setAdvice("审核中");
   	  File shopImg = new File("D:\\picture\\002.jpg");
   	  InputStream is = new FileInputStream(shopImg);
   	 ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
   	  ShopExecution se = shopService.addShop(shop,imageHolder);
   	  System.out.println(shopImg.getName());
   	  assertEquals(ShopStateEnum.CHECK.getState(),se.getState());
	}
        
}
