package com.zcq.seckilling.utils;

import com.zcq.seckilling.domain.JournalInfo;
import com.zcq.seckilling.service.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class JournalUtil {
	@Autowired
	private JournalService journalService;

	/**
	 * @Description: 添加日志
	 * @Author: zcq
	 * @Date: 2018/9/4 上午11:57
	 */
	public void addJournalInfo(int modeularType, int operationType, int uid) {
		JournalInfo journalInfo = new JournalInfo();
		journalInfo.setModularType(modeularType);
		journalInfo.setOperationType(operationType);
		journalInfo.setUid(uid);
		journalService.insertJournalInfo(journalInfo);
	}
}
