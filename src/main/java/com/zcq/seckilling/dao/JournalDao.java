package com.zcq.seckilling.dao;

import com.zcq.seckilling.domain.JournalInfo;
import com.zcq.seckilling.domain.MiaoshaOrder;
import com.zcq.seckilling.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author zhangchuanqiang
 */
@Mapper
public interface JournalDao {

	@Insert("INSERT INTO journal (uid,modularType,operationType,operationTime) VALUES (10086,#{modularType},#{operationType},NOW())")
	int insertJournal(JournalInfo journalInfo);
}
