package com.zcq.seckilling.zcqtest.DutyChain;

public class DoTwoProcess implements Process{
	@Override
	public void doProcess(String msg) {
		System.out.println(msg + "处理2");
	}
}
