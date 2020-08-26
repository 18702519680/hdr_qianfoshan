package com.goodwill.cda.hlht.dao.Impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.goodwill.cda.entity.CdaIndexEntity;
import com.goodwill.cda.hlht.dao.CdaDao;
import com.goodwill.core.orm.hibernate.BaseHibernateDaoImpl;

@Repository
@Transactional
public class CdaDaoImpl extends BaseHibernateDaoImpl<CdaIndexEntity, String> implements CdaDao {
	//删除cda计算数据数据
	@Override
	public void deleteCda(String startTime, String endTime) {
		StringBuilder sqlDelete = new StringBuilder();
		sqlDelete.append("DELETE FROM  hdr_cda_count_new1 ");
		Boolean whereFlag = false;
		if (StringUtils.isNotBlank(startTime)) {
			sqlDelete.append(" where document_time >= '" + startTime + "'  ");
			whereFlag = true;
		}
		if (StringUtils.isNotBlank(endTime)) {
			sqlDelete.append((whereFlag ? " and " : " where "));
			sqlDelete.append(" document_time <= '" + endTime + "'  ");
		}
		executeBySql(sqlDelete.toString());
	}

	@Override
	public void saveUpdate(List<CdaIndexEntity> list) {
		logger.info("saveOrUpdate统计cda数据。。。");
		this.batchSaveOrUpdate(list);
	}
}
