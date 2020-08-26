package com.goodwill.cda.hlht.section;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.cda.util.CommonUtils;
import com.goodwill.cda.util.DictUtisTools;
import com.goodwill.cda.util.Xmlutil;

/**
 * 用读模板的方式生成的诊断章节
 * @Description
 * 类描述：
 * @author liuzhi
 * @Date 2018年3月21日
 * @modify
 * 修改记录：
 *
 */
public class DiagXpathSection {
	protected static Logger logger = LoggerFactory.getLogger(DiagXpathSection.class);
	private String rootPath = DiagXpathSection.class.getResource("/").getFile().toString();

	/**
	 * 获取 诊断的xml
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getDiagEntry() {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0032-住院病案首页-门诊诊断.xml"));
		} catch (DocumentException e) {
			logger.info("===========读取住院病案首页-门诊诊断.xml异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 获取 诊断的xml
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getDiagEntry(String path) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(path));
		} catch (DocumentException e) {
			logger.info("===========读取住院病案首页-门诊诊断.xml异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 获取 诊断的xml
	 * @Description
	 * 方法描述:
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getDiagEntry(Map<String, String> diagDict, Map<String, String> diag) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0032-住院病案首页-门诊诊断.xml"));
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/organizer/component[1]/observation/value",
					diag.get("DIAGNOSIS_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value", "code",
					diagDict.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value",
					"displayName", diagDict.get("name"));
		} catch (DocumentException e) {
			logger.info("===========读取住院病案首页-门诊诊断.xml异常=================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("===========住院病案首页-门诊诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}
	
	
	/**
	 * 获取 诊断的xml
	 * @Description
	 * 方法描述:
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @return 返回类型： Document
	 * @return
	 */
	public Document get042DiagEntry(Map<String, String> diagDict, Map<String, String> diag) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0042-转科记录-目前诊断.xml"));
		/*	Xmlutil.setTexts(body, "/ClinicalDocument/entry/organizer/component[1]/observation/value",
					diag.get("DIAGNOSIS_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value", "code",
					diagDict.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value",
					"displayName", diagDict.get("name"));*/
			
			Xmlutil.addAttributes(body,
//					"/ClinicalDocument/component/structuredBody/component[3]/section/entry[2]/observation/value",
					"/ClinicalDocument/entry/observation/value",
					"code", diagDict.get("code"));
			Xmlutil.addAttributes(body,
//					"/ClinicalDocument/component/structuredBody/component[3]/section/entry[2]/observation/value",
					"/ClinicalDocument/entry/observation/value",
					"displayName", diagDict.get("name"));
			System.out.println(body.asXML());
		} catch (DocumentException e) {
			logger.info("===========读取转科记录-目前诊断.xml异常=================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("===========转科记录-目前诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 获取 诊断的xml
	 * @Description
	 * 方法描述:
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @param displayName  名称 例如 ：门（急）诊诊断 、入院诊断、出院诊断  等等 
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getDiagEntry(Map<String, String> diagDict, Map<String, String> diag, String displayName) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0032-住院病案首页-门诊诊断.xml"));
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/organizer/component[1]/observation/value",
					diag.get("DIAGNOSIS_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value", "code",
					diagDict.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value",
					"displayName", diagDict.get("name"));

			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[1]/observation/code",
					"displayName", displayName + "名称");
			Xmlutil.addAttributes(body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/code/qualifier/name", "displayName",
					displayName);

			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/code",
					"displayName", displayName + "疾病编码");
			Xmlutil.addAttributes(body,
					"/ClinicalDocument/entry/organizer/component[2]/observation/code/qualifier/name", "displayName",
					displayName);
		} catch (DocumentException e) {
			logger.info("===========读取住院病案首页-门诊诊断.xml异常=================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.info("===========住院病案首页-门诊诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 将门诊诊断 添加到传递的document文档的xpath 节点上
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param body    document 文档
	 * @param xpath   xpath 路径
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @param displayName  名称 例如 ：门（急）诊诊断 、入院诊断、出院诊断  等等 
	 * @return
	 */
	public Document setDiagEntryToDocument(Document body, String xpath, Map<String, String> diagDict,
			Map<String, String> diag, String displayName) {

		try {
			Element node = (Element) body.selectSingleNode(xpath);
			@SuppressWarnings("unchecked")
			List<Element> elements = node.elements();
			Document diagEntry = this.getDiagEntry(diagDict, diag, displayName);
			Element rootElement = (Element) diagEntry.selectSingleNode("/ClinicalDocument");
			Element entryElement = rootElement.element("entry");
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					if ("text".equals(element.getName())) {
						entryElement.setParent(null);
						elements.add(i + 1, entryElement);
						break;
					}
				}
			} else {
				elements.add(0, rootElement);
			}

		} catch (Exception e) {
			logger.info("===========住院病案首页-门诊诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 将门诊诊断 添加到传递的document文档的xpath 节点上
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param body    document 文档
	 * @param xpath   xpath 路径
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @return
	 */
	public Document setDiagEntryToDocument(Document body, String xpath, Map<String, String> diagDict,
			Map<String, String> diag) {

		try {
			Element node = (Element) body.selectSingleNode(xpath);
			@SuppressWarnings("unchecked")
			List<Element> elements = node.elements();
			Document diagEntry = this.getDiagEntry(diagDict, diag);
			Element rootElement = (Element) diagEntry.selectSingleNode("/ClinicalDocument");
			Element entryElement = rootElement.element("entry");
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					if ("text".equals(element.getName())) {
						entryElement.setParent(null);
						elements.add(i + 1, entryElement);
						break;
					}
				}
			} else {
				elements.add(0, rootElement);
			}

		} catch (Exception e) {
			logger.info("===========住院病案首页-门诊诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}
	
	/**
	 * 将C0042-转科记录-目前诊断 添加到传递的document文档的xpath 节点上
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param body    document 文档
	 * @param xpath   xpath 路径
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @return
	 */
	public Document set042DiagEntryToDocument(Document body, String xpath, Map<String, String> diagDict,
			Map<String, String> diag) {

		try {
			Element node = (Element) body.selectSingleNode(xpath);
			@SuppressWarnings("unchecked")
			List<Element> elements = node.elements();
			Document diagEntry = this.get042DiagEntry(diagDict, diag);
			Element rootElement = (Element) diagEntry.selectSingleNode("/ClinicalDocument");
			Element entryElement = rootElement.element("entry");
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					if ("text".equals(element.getName())) {
						entryElement.setParent(null);
						elements.add(i + 1, entryElement);
						break;
					}
				}
			} else {
				elements.add(0, rootElement);
			}

		} catch (Exception e) {
			logger.info("===========C0042-转科记录-目前诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 将门诊诊断 添加到传递的document文档的xpath 节点上
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param body    document 文档
	 * @param xpath   xpath 路径
	 * @param diagDict 对照（查询）字典后的诊断 -- 诊断名称键为：name 诊断编码键为：code
	 * @param diag   从原始数据中直接查询出的诊断 -- 诊断名称键为：DIAGNOSIS_NAME 诊断编码键为：DIAGNOSIS_CODE
	 * @return
	 */
	public Document setDiagEntryToDocument(Document body, String xpath, Document children) {

		try {
			Element node = (Element) body.selectSingleNode(xpath);
			@SuppressWarnings("unchecked")
			List<Element> elements = node.elements();
			Element rootElement = (Element) children.selectSingleNode("/ClinicalDocument");
			Element entryElement = rootElement.element("entry");
			if (elements.size() > 0) {
				for (int i = 0; i < elements.size(); i++) {
					Element element = elements.get(i);
					if ("text".equals(element.getName())) {
						entryElement.setParent(null);
						elements.add(i + 1, entryElement);
						break;
					}
				}
			} else {
				elements.add(0, entryElement);
			}

		} catch (Exception e) {
			logger.info("===========住院病案首页-门诊诊断.xml赋诊断节点值异常=================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 获取出院其他诊断的Document
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getDischargeDiag(Map<String, String> diag) {
		Map<String, String> icd10Map = DictUtisTools
				.getIcd10Map(diag.get("DIAGNOSIS_CODE"), diag.get("DIAGNOSIS_NAME"));
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0032-住院病案首页-出院其他诊断.xml"));
			// 其他诊断日期
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[1]/observation/effectiveTime",
					"value", CommonUtils.DateFormats(diag.get("DIAGNOSIS_TIME")));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value", "code",
					icd10Map.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value",
					"displayName", icd10Map.get("name"));
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/organizer/component[1]/observation/value",
					diag.get("DIAGNOSIS_NAME"));
			Map<String, String> qtrybq = DictUtisTools.getRYBQ(diag.get("ADM_CONDITION_CODE"),
					diag.get("ADM_CONDITION_NAME"));
			//出院诊断-其他诊断入院病情代码
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[3]/observation/value", "code",
					qtrybq.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[3]/observation/value",
					"displayName", qtrybq.get("name"));
		} catch (DocumentException e) {
			logger.error("===========读取住院病案首页-出院其他诊断.xml异常================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("===========住院病案首页-出院其他诊断.xml赋值异常================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 获取手术操作章节的Document
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @return
	 */
	public Document getOperDiag(Map<String, String> map) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0032-住院病案首页-手术操作章节.xml"));
			//手术及操作编码
			Map<String, String> icd9Map = DictUtisTools
					.getOperMap(map.get("OPERATION_CODE"), map.get("OPERATION_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/code", "code", icd9Map.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/code", "displayName", icd9Map.get("name"));
			// 手术名称
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/procedure/entryRelationship[1]/observation/value",
					icd9Map.get("name"));
			// 手术及操作日期时间
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/effectiveTime", "value",
					CommonUtils.DateFormats(map.get("OPERATION_DATE")));
			// 手术者CODE
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/performer/assignedEntity/id", "extension",
					map.get("SURGEN_CODE"));
			// 手术者
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/procedure/performer/assignedEntity/assignedPerson/name",
					map.get("SURGEN_NAME"));
			// 第一助手CODE
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/participant[1]/participantRole/id",
					"extension", map.get("FIRST_ASSISTANT_CODE"));
			// 第一助手
			Xmlutil.setTexts(body,
					"/ClinicalDocument/entry/procedure/participant[1]/participantRole/playingEntity/name",
					map.get("FIRST_ASSISTANT_NAME"));
			// 第二助手CODE
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/participant[2]/participantRole/id",
					"extension", map.get("SECOND_ASSISTANT_CODE"));
			// 第二助手
			Xmlutil.setTexts(body,
					"/ClinicalDocument/entry/procedure/participant[2]/participantRole/playingEntity/name",
					map.get("SECOND_ASSISTANT_NAME"));
			// 手术级别
			Map<String, String> operLevel = DictUtisTools.getOperLevel(map.get("OPERATION_GRADE_CODE"),
					map.get("OPERATION_GRADE_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[2]/observation/value",
					"displayName", operLevel.get("name"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[2]/observation/value",
					"code", operLevel.get("code"));
			// 手术切口类别
			Map<String, String> ssqk = DictUtisTools
					.getSSQKLB(map.get("WOUND_GRADE_CODE"), map.get("WOUND_GRADE_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[3]/observation/value",
					"displayName", ssqk.get("name"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[3]/observation/value",
					"code", ssqk.get("code"));
			// 手术切口愈合等级
			Map<String, String> ssqkyhdj = DictUtisTools.getSSQKYHDJ(map.get("HEALING_GRADE_CODE"),
					map.get("HEALING_GRADE_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[4]/observation/value",
					"code", ssqkyhdj.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[4]/observation/value",
					"displayName", ssqkyhdj.get("name"));
			// 麻醉方式信息
			Map<String, String> mzfsMap = DictUtisTools.getAnesMethod(map.get("ANESTHESIA_METHOD_CODE"),
					map.get("ANESTHESIA_METHOD_NAME"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[5]/observation/value",
					"code", mzfsMap.get("code"));
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/procedure/entryRelationship[5]/observation/value",
					"displayName", mzfsMap.get("name"));
			// 麻醉医师CODE
			Xmlutil.addAttributes(body,
					"/ClinicalDocument/entry/procedure/entryRelationship[5]/observation/performer/assignedEntity/id",
					"extension", map.get("ANESTHESIA_DOCTOR_CODE"));
			// 麻醉医师
			Xmlutil.setTexts(
					body,
					"/ClinicalDocument/entry/procedure/entryRelationship[5]/observation/performer/assignedEntity/assignedPerson/name",
					map.get("ANESTHESIA_DOCTOR"));
		} catch (DocumentException e) {
			logger.error("===========读取住院病案首页-手术操作章节.xml异常================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("===========住院病案首页-手术操作章节.xml赋值异常================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 检验报告-检查章节
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param reportMap
	 * @return
	 */
	public Document getReportMap(Map<String, String> reportMap) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0007-检验报告-检查章节.xml"));
			// 检验时间
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[1]/observation/effectiveTime",
					"value", CommonUtils.DateFormats8(reportMap.get("PE_INSPECTION_TIME")));
			//检验项目代码
			Xmlutil.setTexts(body, "/ClinicalDocument/entry/organizer/component[1]/observation/value",
					reportMap.get("PE_INSPECTION_ITEM_NAME"));
			// 标本采样日期时间
			Xmlutil.addAttributes(
					body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/entryRelationship[1]/observation/effectiveTime/low",
					"value", CommonUtils.DateFormats(reportMap.get("PE_SAMPLE_GET_TIME")));
			// 接收标本日期时间
			Xmlutil.addAttributes(
					body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/entryRelationship[1]/observation/effectiveTime/high",
					"value", CommonUtils.DateFormats(reportMap.get("PE_SAMPLE_RCV_TIME")));
			//项目名称
			Xmlutil.setTexts(
					body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/entryRelationship[1]/observation/value",
					reportMap.get("PE_DETAIL_ITEM_NAME"));
			//标本状态
			Xmlutil.setTexts(
					body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/entryRelationship[2]/observation/value",
					reportMap.get("PE_SAMPLE_STATE"));
			//检验结果代码
			String resultDictCode = "-";
			String resultDictName = "-";
			if (reportMap.get("PE_RESULT_CODE").contains("2")) {
				resultDictCode = "2";
				resultDictName = "正常";
			} else {
				resultDictCode = "1";
				resultDictName = "异常";
			}
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[2]/observation/value", "code",
					resultDictCode, "displayName", resultDictName);
			//检验定量结果
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[3]/observation/value", "value",
					CommonUtils.disposeNum(reportMap.get("PE_QUANTITATIVE_RESULT")));
			//检查定量结果计量单位
			Xmlutil.setTexts(body,
					"/ClinicalDocument/entry/organizer/component[3]/observation/entryRelationship/observation/value",
					reportMap.get("PE_QUANTITATIVE_RESULT_UNIT"));
		} catch (DocumentException e) {
			logger.error("===========读取检验报告-检查章节.xml异常================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("===========检验报告-检查章节.xml赋值异常================");
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * 检查报告-检查章节
	 * @Description
	 * 方法描述:
	 * @return 返回类型： Document
	 * @param reportMap
	 * @return
	 */
	public Document getReportsMap6(Map<String, String> reportMap) {
		SAXReader reader = new SAXReader();
		Document body = null;
		try {
			body = reader.read(new File(rootPath + "/xml/C0006-检查报告-检查章节.xml"));
			// 检查日期
			Xmlutil.addAttributes(body, "/ClinicalDocument/entry/organizer/component[1]/observation/effectiveTime",
					"value", CommonUtils.DateFormats(reportMap.get("EXAM_TIME")));
			//检查项目
			Xmlutil.setTexts(
					body,
					"/ClinicalDocument/entry/organizer/component[1]/observation/entryRelationship[1]/observation/value",
					reportMap.get("EXAM_ITEM_NAME"));
			// 检查/检验结果代码表
			String resultDictCode = "-";
			String resultDictName = "-";
			if (reportMap.get("PE_RESULT_CODE").contains("2")) {
				resultDictCode = "1";
				resultDictName = "异常";
			} else {
				resultDictCode = "2";
				resultDictName = "正常";
			}
			Xmlutil.addAttributes(
					body,
					"/ClinicalDocument/component/structuredBody/component[5]/section/entry[4]/organizer/component[2]/observation/value",
					"code", resultDictCode, "displayName", resultDictName);
		} catch (DocumentException e) {
			logger.error("===========读取检查报告-检查章节.xml异常================");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("===========检查报告-检查章节.xml赋值异常================");
			e.printStackTrace();
		}
		return body;
	}
}
