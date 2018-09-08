package com.o98k.o2o.exceptions;


//对runtimeException进行一层很薄的封装，便于发生异常时能够知道是与店铺相关
public class ShopOperationException extends RuntimeException{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -2104020205928164405L;

	public ShopOperationException(String msg) {
		super(msg);
	}
}
