package com.zcq.seckilling.result;

/**
 * @author zhangchuanqiang
 */
public class Result<T> {
	private int code;
	private String msg;
	private T data;

	public Result() {
	}

	private Result(T data) {
		this.code = 0;
		this.msg = "success";
		this.data = data;
	}

	public Result(CodeMsg msg) {
		if (msg == null) {
			return;
		}
		this.code = msg.getCode();
		this.msg = msg.getMsg();
	}

	public int getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}

	/**
	 * @Description: 成功时候的调用
	 */
	public static <T> Result<T> success(T data) {
		return new Result<T>(data);
	}

	/**
	 * @Description: 失败时候的调用
	 */
	public static <T> Result<T> error(CodeMsg msg) {
		return new Result<T>(msg);
	}
}
