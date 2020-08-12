package com.zcq.seckilling.zcqtest.DutyChain;

public class ProcessMain {
	public static void main(String[] args) {
		String msg = "内容==";
		MsgProcessChain chain = new MsgProcessChain().addChain(new DoOneProcess())
				.addChain(new DoTwoProcess());
		chain.process(msg);
	}
}
