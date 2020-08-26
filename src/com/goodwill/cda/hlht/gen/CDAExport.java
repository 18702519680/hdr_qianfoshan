package com.goodwill.cda.hlht.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.goodwill.cda.util.HiveUtil;
import com.goodwill.hadoop.hbase.HbaseCURDUtils;

/**
 * @Classname CDAExport.java
 * @Description 批量导出CDA文档
 * 打成可执行jar包   java -jar xx.jar 2020-01-01 2020-01-03 10
 * 参数 1.开始时间  2.结束时间 3.导出数量 默认10条
 * 导出目标到  C:/CDA-Export/
 * @Date 2020-1-17 13:33
 * @Created by hanfei
 */

public class CDAExport {
    protected static Logger logger = LoggerFactory.getLogger(CDAExport.class);
    public static String dirPath = "C:/CDA-Export/";

    public static void main(String[] args) throws Exception {
        String startTime = "";
        String endTime = "";
        String num = "10";
        if (args.length > 0) {
            startTime = args[0];
        }
        if (args.length > 1) {
            endTime = args[1];
        }
        if (args.length > 2) {
            num = args[2];
        }
        if (StringUtils.isBlank(startTime) && StringUtils.isBlank(endTime)) {
            System.out.println("请传开始时间，结束时间");
            return;
        }
        boolean numeric00 = isNumeric00(num);
        if (!numeric00) {
            System.out.println("导出的数量不是数值类型的");
            return;
        }
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        hbaseToXml(startTime, endTime, num);
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return 返回true 则是数字或小数
     * 返回false 则是其他类型
     */
    public static boolean isNumeric00(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("异常：\"" + str + "\"不是数字/整数...");
            return false;
        }
    }

    /**
     * 随机读取每个cda文档 存储5份
     *
     * @throws Exception
     */
    public static void hbaseToXml(String startTime, String endTime, String num) throws Exception {
        logger.info("===========开始导出================");
        Connection conns = HiveUtil.createHiveConnection();
        ResultSet res = null;
        Statement stmt = null;
        List<Sort> listSort = new ArrayList<Sort>();
        String sql = "";
        getDocumentTitle(listSort);
        stmt = conns.createStatement();
        for (Sort sort : listSort) {
            logger.info("==导出文档==" + sort.getName());
            long start = System.currentTimeMillis();
            if (StringUtils.isBlank(sort.getName())) {
                continue;
            }
            sql = "select  patientid ,min(rowkey) rowkey from  hdr_cda_index_ii where documentcode='" + sort.getId()
                    + "' and createtime between '" + startTime + "' and '" + endTime
                    + "' group by patientid limit " + num;
            logger.info("sql语句==" + sql);
            res = null;
            res = stmt.executeQuery(sql);
            while (res.next()) {
                String[] rks = res.getString("rowkey").split("\\|");
                List<Map<String, String>> list3 = HbaseCURDUtils.findByRowkeyPrefix("HDR_CDA", res.getString("rowkey"));
                System.out.println(res.getString("rowkey"));
                for (Map<String, String> map : list3) {
                    String fileName = dirPath + rks[4] + "-" + rks[2] + "-" + rks[3] + "-" + ""
                            + System.currentTimeMillis() + ".xml";
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),
                            "UTF-8"));
                    bw.append(map.get("Document"));
                    bw.close();
                }
            }
            long end = System.currentTimeMillis();
            logger.info("=====统计生成" + sort.getName() + "cda的时间======" + (((end - start) / 1000) / 60) + "分钟");
        }
        if (res != null) {
            res.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conns != null) {
            conns.close();
        }
        logger.info("===========导出结束================");
    }

    public static List<Sort> getDocumentTitle(List<Sort> listSort) {
        listSort.add(new Sort("C0001", "病历概要"));
        listSort.add(new Sort("C0002", "门急诊病历"));
        listSort.add(new Sort("C0003", "急诊留观病历"));
        listSort.add(new Sort("C0004", "西药处方"));
        listSort.add(new Sort("C0005", "中药处方"));
        listSort.add(new Sort("C0006", "检查报告"));
        listSort.add(new Sort("C0007", "检验报告"));
        listSort.add(new Sort("C0008", "治疗记录"));
        listSort.add(new Sort("C0009", "一般手术记录"));
        listSort.add(new Sort("C0010", "麻醉术前访视记录"));
        listSort.add(new Sort("C0011", "麻醉记录"));
        listSort.add(new Sort("C0012", "麻醉术后访视记录"));
        listSort.add(new Sort("C0013", "输血记录"));
        listSort.add(new Sort("C0014", "待产记录"));
        listSort.add(new Sort("C0015", "阴道分娩记录"));
        listSort.add(new Sort("C0016", "剖宫产记录"));
        listSort.add(new Sort("C0017", "一般护理记录"));
        listSort.add(new Sort("C0018", "病危病重护理记录"));
        listSort.add(new Sort("C0019", "手术护理记录"));
        listSort.add(new Sort("C0020", "生命体征测量记录"));
        listSort.add(new Sort("C0021", "出入量记录"));
        listSort.add(new Sort("C0022", "高值耗材使用记录"));
        listSort.add(new Sort("C0023", "入院评估"));
        listSort.add(new Sort("C0024", "护理计划"));
        listSort.add(new Sort("C0025", "出院评估与指导"));
        listSort.add(new Sort("C0026", "手术知情同意书"));
        listSort.add(new Sort("C0027", "麻醉知情同意书"));
        listSort.add(new Sort("C0028", "输血治疗同意书"));
        listSort.add(new Sort("C0029", "特殊检查及特殊治疗同意书"));
        listSort.add(new Sort("C0030", "病危重通知书"));
        listSort.add(new Sort("C0031", "其他知情告知同意书"));
        listSort.add(new Sort("C0032", "住院病案首页"));
        listSort.add(new Sort("C0033", "中医住院病案首页"));
        listSort.add(new Sort("C0034", "入院记录"));
        listSort.add(new Sort("C0035", "24h内入出院记录"));
        listSort.add(new Sort("C0036", "24h内入院死亡记录"));
        listSort.add(new Sort("C0037", "首次病程记录"));
        listSort.add(new Sort("C0038", "日常病程记录"));
        listSort.add(new Sort("C0039", "上级医师查房记录"));
        listSort.add(new Sort("C0040", "疑难病例讨论记录"));
        listSort.add(new Sort("C0041", "交接班记录"));
        listSort.add(new Sort("C0042", "转科记录"));
        listSort.add(new Sort("C0043", "阶段小结"));
        listSort.add(new Sort("C0044", "抢救记录"));
        listSort.add(new Sort("C0045", "会诊记录"));
        listSort.add(new Sort("C0046", "术前小结"));
        listSort.add(new Sort("C0047", "术前讨论"));
        listSort.add(new Sort("C0048", "术后首次病程记录"));
        listSort.add(new Sort("C0049", "出院记录"));
        listSort.add(new Sort("C0050", "死亡记录"));
        listSort.add(new Sort("C0051", "死亡病例讨论记录"));
        listSort.add(new Sort("C0052", "住院医嘱"));
        listSort.add(new Sort("C0053", "出院小结"));
        return listSort;
    }
}

class Sort {
    private String id;
    private String name;

    public Sort() {
        super();
    }

    public Sort(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}