package com.zcq.seckilling.zcqtest.DutyChain;

import java.util.ArrayList;
import java.util.List;

public class MsgProcessChain {
	private List<Process> chains = new ArrayList<>();

	/**
	 * @Description: 添加责任链
	 */
	public MsgProcessChain addChain(Process process){
		chains.add(process);
		return this;
	}

	/**
	 * @Description: 执行处理
	 */
	public void process(String msg){
		for (Process process : chains) {
			process.doProcess(msg);
		}
	}
}
