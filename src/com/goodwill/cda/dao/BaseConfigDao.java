/**
 * 
 */
package com.goodwill.cda.dao;

import java.io.Serializable;

import com.goodwill.core.orm.BaseDao;
import com.goodwill.core.orm.Page;

/**
 * @author liujl
 *
 */
public interface BaseConfigDao<T, PK extends Serializable> extends BaseDao<T, PK> {

	/**
	 * @Description
	 * 方法描述:根据关键字查询，like匹配 name code name_abrev（简拼，首字母） name_full（全拼）
	 * @return 返回类型： List<T>
	 * @param entity
	 * @param keyWord
	 * @return
	 */
	public Page<T> queryByKeyWord(Page<T> page, T entity, String keyWord);

	/**
	 * 方法描述:查询属性的值在表中是否唯一。pkValue=空时，是新增实体。pkValue !=空时，是编辑实体
	 * @return 	>0	已存在 <br>	=0不存在<br>   <0	参数异常
	 */
	public int isPropertyUnique(String entityName, String pkName, String pkValue, String colName, Object newValue);

	/**
	 * 方法描述:查询属性的值在表中是否唯一。pkValue=空时，是新增实体。pkValue !=空时，是编辑实体,filterName是可以额外增加的过滤条件，如数据状态必须为启用
	 * @return 	>0	已存在 <br>	=0不存在<br>   <0	参数异常
	 */
	public int isPropertyUnique(String entityName, String pkName, String pkValue, String colName, Object newValue,
			String[] filterName, String[] filterValue);
}
