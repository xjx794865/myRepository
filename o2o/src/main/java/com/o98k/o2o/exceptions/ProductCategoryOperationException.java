package com.o98k.o2o.exceptions;


//对runtimeException进行一层很薄的封装，便于发生异常时能够知道是与店铺相关
public class ProductCategoryOperationException extends RuntimeException{
    
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7708685919462035489L;

	public ProductCategoryOperationException(String msg) {
		super(msg);
	}
}
