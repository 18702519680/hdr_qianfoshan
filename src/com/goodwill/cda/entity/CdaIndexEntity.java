package com.goodwill.cda.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * HDR_CDA_COUNT 实体类
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2017年12月18日
 * @modify
 * 修改记录：
 *
 */
@Entity
@Table(name = "hdr_cda_count_new1")
public class CdaIndexEntity implements Serializable {

	private static final long serialVersionUID = -3204009639268409997L;

	private String documentCode;

	private String documentTitle;

	private String documentCount;

	private String documentTime;

	private String id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	@Column(name = "document_code")
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocumentTitle() {
		return documentTitle;
	}

	@Column(name = "document_title")
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}

	public String getDocumentCount() {
		return documentCount;
	}

	@Column(name = "document_count")
	public void setDocumentCount(String documentCount) {
		this.documentCount = documentCount;
	}

	public String getDocumentTime() {
		return documentTime;
	}

	@Column(name = "document_time")
	public void setDocumentTime(String documentTime) {
		this.documentTime = documentTime;
	}

}
