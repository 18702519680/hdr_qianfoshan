package com.goodwill.cda.hlht.dao;

import java.util.List;

import com.goodwill.cda.entity.CdaIndexEntity;

/**
 * CDA插入Dao
 * @author jibin
 *
 */
public interface CdaDao {

	//删除cda文档数据
	public void deleteCda(String startTime, String endTime);

	/**
	 * 保存或者修改
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 * @param list
	 */
	public void saveUpdate(List<CdaIndexEntity> list);

}
