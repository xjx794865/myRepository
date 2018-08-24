package com.o98k.o2o.util;


//将pageIndex转化为rowIndex
public class PageCalculator {
      
	public static int calculateRowIndex(int pageIndex,int pageSize) {
		return (pageIndex > 0) ? (pageIndex -1) *pageSize :0;
	}
}
