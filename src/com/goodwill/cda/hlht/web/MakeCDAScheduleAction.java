package com.goodwill.cda.hlht.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.goodwill.cda.hlht.constant.ScheduleConstant;
import com.goodwill.core.utils.json.JsonUtil;
import com.goodwill.core.web.BaseAction;

/**
 * @Description
 * 类描述：显示定时 生成CDA进度的action
 * @author liuzhi
 * @Date 2017年12月11日
 * @modify
 * 修改记录：
 */
public class MakeCDAScheduleAction extends BaseAction {

	private static final long serialVersionUID = -12234444441L;

	/**
	 * 获取 住院进度
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public void getInSummarySchedule() {
		BigDecimal in_summary = BigDecimal.valueOf(ScheduleConstant.getInSummarySchedule() * 100)
				.divide(BigDecimal.valueOf(ScheduleConstant.getInSummaryTotal()), 2);
		in_summary.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal out_patient = BigDecimal.valueOf(ScheduleConstant.getOutPatientSchedule() * 100)
				.divide(BigDecimal.valueOf(ScheduleConstant.getOutPatientTotal()), 2);
		out_patient.setScale(2, BigDecimal.ROUND_HALF_UP);
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		result.put("inSummary", in_summary);
		result.put("outPatient", out_patient);
		renderJson(JsonUtil.getJSONString(result));
	}

	/**
	 * 获取 生成门诊 CDA进度
	 * @Description
	 * 方法描述:
	 * @return 返回类型： void
	 */
	public void getOutPatientSchedule() {
		BigDecimal divide = BigDecimal.valueOf(ScheduleConstant.getOutPatientSchedule() * 100)
				.divide(BigDecimal.valueOf(ScheduleConstant.getOutPatientTotal()), 2);
		divide.setScale(2, BigDecimal.ROUND_HALF_UP);
		renderJson(JsonUtil.getJSONString(divide.toString()));
	}
}
