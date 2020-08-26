package com.goodwill.cda.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.goodwill.core.entity.BaseAbstractEntity;
import com.goodwill.core.entity.EnumClass;

/**
 * @Description
 * 类描述：CDRP 配置模块  entity基类
 * @author lijiannan
 * @Date 2014年6月12日
 * @modify
 * 修改记录：
 * 
 */
@MappedSuperclass
public abstract class BaseConfig extends BaseAbstractEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9022680089303454816L;
	private String name_one; //名称首字母
	private String name_abrev; //名称简拼
	private String name_full; //名称全拼

	private String data_status; //数据状态
	private String create_by; //创建人
	private String create_time; //创建时间
	private String modify_by; //修改人
	private String modify_time; //修改时间
	private String modify_note; //修改原因备注

	/**
	 * 名称首字母的getter方法
	 * @return String
	 * @param 
	 */
	public String getName_one() {
		return name_one;
	}

	/**
	 * 名称首字母的setter方法
	 * @return void
	 * @param name_one
	 */
	public void setName_one(String name_one) {
		this.name_one = name_one;
	}

	/**
	 * 名称简拼的getter方法
	 * @return String
	 * @param 
	 */
	public String getName_abrev() {
		return name_abrev;
	}

	/**
	 * 名称简拼的setter方法
	 * @return void
	 * @param name_abrev
	 */
	public void setName_abrev(String name_abrev) {
		this.name_abrev = name_abrev;
	}

	/**
	 * 名称全拼的getter方法
	 * @return String
	 * @param 
	 */
	public String getName_full() {
		return name_full;
	}

	/**
	 * 名称全拼的setter方法
	 * @return void
	 * @param name_full
	 */
	public void setName_full(String name_full) {
		this.name_full = name_full;
	}

	/**
	 * 数据状态的getter方法
	 * @return String
	 * @param 
	 */
	@EnumClass(DataStatusEnum.class)
	public String getData_status() {
		return data_status;
	}

	/**
	 * 数据状态的setter方法
	 * @return void
	 * @param data_status
	 */
	public void setData_status(String data_status) {
		this.data_status = data_status;
	}

	/**
	 * 创建人的getter方法
	 * @return String
	 * @param 
	 */
	public String getCreate_by() {
		return create_by;
	}

	/**
	 * 创建人的setter方法
	 * @return void
	 * @param create_by
	 */
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	/**
	 * 创建时间的getter方法
	 * @return String
	 * @param 
	 */
	public String getCreate_time() {
		return create_time;
	}

	/**
	 * 创建时间的setter方法
	 * @return void
	 * @param create_time
	 */
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	/**
	 * 修改人的getter方法
	 * @return String
	 * @param 
	 */
	public String getModify_by() {
		return modify_by;
	}

	/**
	 * 修改人的setter方法
	 * @return void
	 * @param modify_by
	 */
	public void setModify_by(String modify_by) {
		this.modify_by = modify_by;
	}

	/**
	 * 修改时间的getter方法
	 * @return String
	 * @param 
	 */
	public String getModify_time() {
		return modify_time;
	}

	/**
	 * 修改时间的setter方法
	 * @return void
	 * @param modify_time
	 */
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}

	/**
	 * 修改原因备注的getter方法
	 * @return String
	 * @param 
	 */
	public String getModify_note() {
		return modify_note;
	}

	/**
	 * 修改原因备注的setter方法
	 * @return void
	 * @param modify_note
	 */
	public void setModify_note(String modify_note) {
		this.modify_note = modify_note;
	}

	/**
	 * @Description
	 * 方法描述: 各子类计算拼音的属性不同，通过该方法返回计算拼音的源。
	 * 如：CaseConfig 返回的是“时间断面名称001”
	 * Group返回的是“人群名称001” 直接计算拼音 不考虑属性名问题
	 * @return 返回类型： String
	 * @return
	 */
	@Transient
	public abstract String getPinyinSourceAttr();

}
