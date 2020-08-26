/**
 * 
 */
package com.goodwill.cda.dao;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.goodwill.cda.entity.DataStatusEnum;
import com.goodwill.core.orm.Page;
import com.goodwill.core.orm.hibernate.BaseHibernateDaoImpl;
import com.goodwill.core.utils.ApplicationException;

/**
 * @Description
 * 类描述：CDRP DAO的基类
 * @author lijiannan
 * @Date 2014年8月26日
 * @param <T> 
 * @param <PK>
 * @modify
 * 修改记录：
 * 
 */
public abstract class BaseConfigDaoImpl<T, PK extends Serializable> extends BaseHibernateDaoImpl<T, PK> implements
		BaseConfigDao<T, PK> {

	/**
	 * @Description
	 * 方法描述:根据关键字查询，like匹配 name code name_abrev（简拼，首字母） name_full（全拼）
	 * @return 返回类型： List<T>
	 * @param entity
	 * @param keyWord
	 * @return
	 */
	public Page<T> queryByKeyWord(Page<T> page, T entity, String keyWord) {

		String hql = "from " + entity.getClass().getSimpleName() + " ";
		if (!StringUtils.isBlank(keyWord)) {
			hql += "where code like concat('%' ,? ,'%') or name like concat('%' ,? ,'%')  or ";
			hql += "name_abrev like concat('%' ,? ,'%') or name_full like concat('%' ,? ,'%') ";
			return findPage(page, hql, keyWord, keyWord, keyWord, keyWord);
		}
		return findPage(page, hql);
	}

	/**
	 * 方法描述:查询属性的值在表中是否唯一。pkValue=空时，是新增实体。pkValue !=空时，是编辑实体
	 * @return 	>0	已存在 <br>	=0不存在<br>   <0	参数异常
	 */
	@Override
	public int isPropertyUnique(String entityName, String pkName, String pkValue, String colName, Object newValue) {
		int resultSize = -1;
		String hql = " from " + entityName + " ";
		if (StringUtils.isBlank(pkValue)) {//新增
			hql += " where " + colName + "='" + newValue + "' ";
		} else {
			hql += "where " + pkName + "<>'" + pkValue + "' and " + colName + "='" + newValue + "'";
		}
		hql += " and data_status='" + DataStatusEnum.ENABLE.getCode() + "'";
		resultSize = (int) countHqlResult(hql);
		return resultSize;
	}

	/**
	 * 方法描述:查询属性的值在表中是否唯一。pkValue=空时，是新增实体。pkValue !=空时，是编辑实体,filterName是可以额外增加的过滤条件，如数据状态必须为启用
	 * @return 	>0	已存在 <br>	=0不存在<br>   <0	参数异常
	 */
	@Override
	public int isPropertyUnique(String entityName, String pkName, String pkValue, String colName, Object newValue,
			String[] filterName, String[] filterValue) {
		int resultSize = -1;
		String hql = " from " + entityName + " ";
		if (StringUtils.isBlank(pkValue)) {//新增
			hql += " where " + colName + "='" + newValue + "' ";
		} else {
			hql += "where " + pkName + "<>'" + pkValue + "' and " + colName + "='" + newValue + "'";
		}
		if (filterName != null && filterValue != null && filterName.length == filterValue.length) {
			for (int i = 0; i < filterName.length; i++) {
				hql += " and " + filterName[i] + "='" + filterValue[i] + "'";
			}
		} else {
			if (filterName != null || filterValue != null) {
				throw new ApplicationException("过滤参数与参数值数量不相等！");
			}
		}
		resultSize = (int) countHqlResult(hql);
		return resultSize;
	}
}
