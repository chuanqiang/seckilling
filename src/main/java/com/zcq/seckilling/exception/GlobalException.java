package com.zcq.seckilling.exception;

import com.zcq.seckilling.result.CodeMsg;

/**
 * @author zhangchuanqiang
 */
public class GlobalException extends RuntimeException {
	private CodeMsg codeMsg;
	public GlobalException(CodeMsg codeMsg){
		super(codeMsg.toString());
		this.codeMsg = codeMsg;
	}

	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
}
