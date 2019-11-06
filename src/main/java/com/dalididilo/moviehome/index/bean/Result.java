package com.dalididilo.moviehome.index.bean;

import javax.servlet.http.HttpServletResponse;

/**
 *     Modifications:   
 *  
 *  @author zhang.xianhui10@zte.com.cn     
 *  @version 1.0 
 *  @since 2015年2月5日 下午3:41:49 
 */
public class Result {
	private boolean success;
	
	private String message;
	
	private String successInfo;
	
	private int statusCode;//状态码 移动端的 后遗症
	
	private Object result;

	public Result(){

	}

	public Result(boolean success){
		this.success=success;
	}
	
	public Result(boolean success, String message, String successInfo){
		this.success=success;
		this.message=message;
		this.successInfo=successInfo;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.statusCode= HttpServletResponse.SC_OK;
		this.success = success;
	}

	public String getSuccessInfo() {
		return successInfo;
	}

	public void setSuccessInfo(String successInfo) {
		this.successInfo = successInfo;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
