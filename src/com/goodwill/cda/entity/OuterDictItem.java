package com.goodwill.cda.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 外部字典项（包含字典项和数据集）
 * @author xiehongwei
 * @Date 2015年8月17日
 * @modify:
 * 
 */
@Entity
@Table(name = "hdr_md_outer_dict_item")
public class OuterDictItem extends BaseConfig {
	private static final long serialVersionUID = 1L;

	private String code;//编码
	private String dict_code;//所属平台字典
	private String item_value;//平台字典项值或者数据集编码
	private String item_meaning;//字典项值含义
	private String description;//描述
	private String match_method;//匹配方法（等于、包含、存在与等）
	private String match_codes;//选择对照院内字典项,一条或多条（以逗号分割）

	@Id
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDict_code() {
		return dict_code;
	}

	public void setDict_code(String dict_code) {
		this.dict_code = dict_code;
	}

	public String getItem_value() {
		return item_value;
	}

	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}

	public String getItem_meaning() {
		return item_meaning;
	}

	public void setItem_meaning(String item_meaning) {
		this.item_meaning = item_meaning;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMatch_method() {
		return match_method;
	}

	public void setMatch_method(String match_method) {
		this.match_method = match_method;
	}

	public String getMatch_codes() {
		return match_codes;
	}

	public void setMatch_codes(String match_codes) {
		this.match_codes = match_codes;
	}

	@Override
	@Transient
	public String getPinyinSourceAttr() {
		return item_meaning;
	}

	@Override
	@Transient
	public String getPrimaryKey() {
		return code;
	}
}
