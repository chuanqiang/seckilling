package com.zcq.seckilling.dao;

import com.zcq.seckilling.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MiaoshaUserDao {
	
	@Select("select * from miaosha_user where id = #{id}")
	MiaoshaUser getById(@Param("id") long id);

	@Update("update miaosha_user set password = #{password} where id = #{id}")
	void update(MiaoshaUser toBeUpdate);
}
