package com.goodwill.cda.dao;

import java.util.Map;

import com.goodwill.cda.entity.OuterDictItem;

/**
 * @Description
 * 类描述：外部数据字典项DAO操作
 * @author 计彬
 * @Date 2017年10月10日
 * @modify
 * 修改记录：
 * 
 */
public interface MatchDictDao extends BaseConfigDao<OuterDictItem, String> {
	/**
	 * 插入
	 * @param PatientID
	 * @param VisitID
	 * @param DocumentTitle
	 * @param PatientName
	 * @param InTime
	 * @param OutTime
	 * @param HealthCardId
	 * @param IdentityId
	 * @param DocumentUniqueId
	 * @param RepositoryUniqueId
	 * @param AdmissionDepart
	 * @param AdmissionDoctor
	 * @param ServerOrganization
	 * @param MimeType
	 * @param EpisodeID
	 * @param CreateTime
	 * @param AdmissionType
	 * @param DiagnosisResult
	 * @param DocUrl
	 * @param ROWKEY
	 */
	public void insertCdaToMySql(String PatientID, String VisitID, String DocumentTitle, String PatientName,
			String InTime, String OutTime, String HealthCardId, String IdentityId, String DocumentUniqueId,
			String RepositoryUniqueId, String AdmissionDepart, String AdmissionDoctor, String ServerOrganization,
			String MimeType, String EpisodeID, String CreateTime, String AdmissionType, String DiagnosisResult,
			String DocUrl, String ROWKEY);

	/**
	 * 删除
	 * @param PatientID
	 * @param VisitID
	 */
	public void delCda(String PatientID, String VisitID);

	/**
	 * 
	 * @param dictCode
	 * @return
	 */
	public Map<String, String> getMatchDictDiag(String dictCode);
}
