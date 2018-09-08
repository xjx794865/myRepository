package com.o98k.o2o.util;

public class PathUtil {
	
	//获取当前系统路径的分隔符
	private static String separator = System.getProperty("file.separator");
	
	//返回项目图片的根路径 根据系统的不同进行分类
	public static String getImgBasePath() {

		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/picture/image";

		} 
		else {
			basePath = "/home/o98k/image";
		}

		basePath = basePath.replace("/", separator);
		
		return basePath;

	}
	//获取店铺图片的存储路径 分别存储在各自店铺的路径下
	public static String getShopImagePath(long shopId) {
		String imagePath ="/upload/item/shop/" + shopId +"/";
		
		return  imagePath.replace("/", separator);
		
	}
	
}
