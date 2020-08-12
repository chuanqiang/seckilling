package com.zcq.seckilling.service;

import com.zcq.seckilling.dao.JournalDao;
import com.zcq.seckilling.domain.JournalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangchuanqiang
 */
@Service
public class JournalService {

	@Autowired
	JournalDao journal;

	public void insertJournalInfo(JournalInfo journalInfo){
		journal.insertJournal(journalInfo);
	}
}
