package com.goodwill.cda.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.goodwill.cda.dao.BaseConfigDaoImpl;
import com.goodwill.cda.dao.MatchDictDao;
import com.goodwill.cda.entity.OuterDictItem;

@Repository
public class MatchDictDaoImpl extends BaseConfigDaoImpl<OuterDictItem, String> implements MatchDictDao {

	@Override
	public void insertCdaToMySql(String PatientID, String VisitID, String DocumentTitle, String PatientName,
			String InTime, String OutTime, String HealthCardId, String IdentityId, String DocumentUniqueId,
			String RepositoryUniqueId, String AdmissionDepart, String AdmissionDoctor, String ServerOrganization,
			String MimeType, String EpisodeID, String CreateTime, String AdmissionType, String DiagnosisResult,
			String DocUrl, String ROWKEY) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO HDR_CDA VALUES ('" + PatientID + "','" + VisitID + "','" + DocumentTitle + "','"
				+ PatientName + "','" + InTime + "','" + OutTime + "','" + HealthCardId + "','" + IdentityId + "','"
				+ DocumentUniqueId + "','" + RepositoryUniqueId + "','" + AdmissionDepart + "','" + AdmissionDoctor
				+ "','" + ServerOrganization + "','" + MimeType + "','" + EpisodeID + "','" + CreateTime + "','"
				+ AdmissionType + "','" + DiagnosisResult + "','" + DocUrl + "','" + ROWKEY + "')";
		//System.out.println(sql + "插入完成");
		executeBySql(sql);
	}

	@Override
	public void delCda(String PatientID, String VisitID) {
		// TODO Auto-generated method stub
		String sqlDel = "DELETE FROM HDR_CDA WHERE PatientID='" + PatientID + "' AND VisitID='" + VisitID + "'";
		System.out.println(sqlDel);
		executeBySql(sqlDel);
	}

	@Override
	public Map<String, String> getMatchDictDiag(String dictCode) {
		// TODO Auto-generated method stub
		if (StringUtils.isNotBlank(dictCode)) {
			try {
				dictCode = dictCode.substring(0, dictCode.indexOf(".")) + ".000";
			} catch (Exception e) {
				dictCode = "A00.000";
			}
		} else {
			dictCode = "A00.000";
		}
		Map<String, String> map = new HashMap<String, String>();
		String sql = "SELECT item_value,item_meaning from hdr_md_outer_dict_item where item_value='" + dictCode + "'";
		Query query = createSqlQuery(sql);
		List<Object[]> outerDictList = query.list();
		for (int i = 0; i < outerDictList.size(); i++) {
			Object[] objArray = outerDictList.get(i);
			map.put("code", objArray[0].toString());
			map.put("name", objArray[1].toString());
		}
		System.out.println(map);
		return map;
	}

}
