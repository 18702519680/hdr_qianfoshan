package com.goodwill.cda.entity;

import javax.persistence.Id;
import javax.persistence.Transient;


/**
 * 类描述：cda实体类
 * 2017-11-02
 * @author jibin
 *
 */
//@Entity
//@Table(name = "HDR_CDA")
public class CdaIndex extends BaseConfig {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3204009639268409997L;
	private String id;
	private String PatientID;
	private String VisitID;
	private String DocumentTitle;
	private String PatientName;
	private String InTime;
	private String OutTime;
	private String HealthCardId;
	private String IdentityId;
	private String DocumentUniqueId;
	private String RepositoryUniqueId;
	private String AdmissionDepart;
	private String AdmissionDoctor;
	private String ServerOrganization;
	private String MimeType;
	private String EpisodeID;
	private String CreateTime;
	private String AdmissionType;
	private String DiagnosisResult;
	private String DocUrl;
	private String ROWKEY;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPatientID() {
		return PatientID;
	}

	public void setPatientID(String patientID) {
		PatientID = patientID;
	}

	public String getVisitID() {
		return VisitID;
	}

	public void setVisitID(String visitID) {
		VisitID = visitID;
	}

	public String getDocumentTitle() {
		return DocumentTitle;
	}

	public void setDocumentTitle(String documentTitle) {
		DocumentTitle = documentTitle;
	}

	public String getPatientName() {
		return PatientName;
	}

	public void setPatientName(String patientName) {
		PatientName = patientName;
	}

	public String getInTime() {
		return InTime;
	}

	public void setInTime(String inTime) {
		InTime = inTime;
	}

	public String getOutTime() {
		return OutTime;
	}

	public void setOutTime(String outTime) {
		OutTime = outTime;
	}

	public String getHealthCardId() {
		return HealthCardId;
	}

	public void setHealthCardId(String healthCardId) {
		HealthCardId = healthCardId;
	}

	public String getIdentityId() {
		return IdentityId;
	}

	public void setIdentityId(String identityId) {
		IdentityId = identityId;
	}

	public String getDocumentUniqueId() {
		return DocumentUniqueId;
	}

	public void setDocumentUniqueId(String documentUniqueId) {
		DocumentUniqueId = documentUniqueId;
	}

	public String getRepositoryUniqueId() {
		return RepositoryUniqueId;
	}

	public void setRepositoryUniqueId(String repositoryUniqueId) {
		RepositoryUniqueId = repositoryUniqueId;
	}

	public String getAdmissionDepart() {
		return AdmissionDepart;
	}

	public void setAdmissionDepart(String admissionDepart) {
		AdmissionDepart = admissionDepart;
	}

	public String getAdmissionDoctor() {
		return AdmissionDoctor;
	}

	public void setAdmissionDoctor(String admissionDoctor) {
		AdmissionDoctor = admissionDoctor;
	}

	public String getServerOrganization() {
		return ServerOrganization;
	}

	public void setServerOrganization(String serverOrganization) {
		ServerOrganization = serverOrganization;
	}

	public String getMimeType() {
		return MimeType;
	}

	public void setMimeType(String mimeType) {
		MimeType = mimeType;
	}

	public String getEpisodeID() {
		return EpisodeID;
	}

	public void setEpisodeID(String episodeID) {
		EpisodeID = episodeID;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getAdmissionType() {
		return AdmissionType;
	}

	public void setAdmissionType(String admissionType) {
		AdmissionType = admissionType;
	}

	public String getDiagnosisResult() {
		return DiagnosisResult;
	}

	public void setDiagnosisResult(String diagnosisResult) {
		DiagnosisResult = diagnosisResult;
	}

	public String getDocUrl() {
		return DocUrl;
	}

	public void setDocUrl(String docUrl) {
		DocUrl = docUrl;
	}

	public String getROWKEY() {
		return ROWKEY;
	}

	public void setROWKEY(String rOWKEY) {
		ROWKEY = rOWKEY;
	}

	@Override
	@Transient
	public String getPinyinSourceAttr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return id;
	}

}
