package com.forms.prms.web.export.prepay;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.excel.exports.ExcelExportUtility;
import com.forms.platform.excel.exports.JxlsExcelExporter;
import com.forms.platform.excel.exports.SimplifyBatchExcelExporter;
import com.forms.platform.excel.exports.inter.IExportDataDeal;
import com.forms.prms.tool.exceltool.domain.CommonExcelDealBean;
import com.forms.prms.web.contract.query.service.ContractQueryService;
import com.forms.prms.web.pay.payquery.domain.PayQueryBean;
import com.forms.prms.web.pay.payquery.service.PayQueryService;

public class PrePayExport implements IExportDataDeal {
	PayQueryService es = PayQueryService.getInstance();
	ContractQueryService excelDealService = ContractQueryService.getInstance(); // 用于更新导出任务结果状态和导出备注
	@Override
	public void getSimpleExcelData(String taskId, Map params,
			SimplifyBatchExcelExporter excelExporter) throws Exception {
 
	}

	@Override
	public void getJxlExcelData(String taskId, Map params,
			JxlsExcelExporter jxlExporter) throws Exception {
		boolean exportSucFlag = (Boolean) params.get("exportSucFlag");
		String exportMemo = "";
	Map<String, Object> beansMap = new HashMap<String, Object>();
	String cntNum=(String) params.get("cntNum");
	String cntAllAmt=(String) params.get("cntAllAmt");
	String zbAmt=(String) params.get("zbAmt");
	String cntType=(String) params.get("cntType");
	String normarlTotalAmt=(String) params.get("normarlTotalAmt");
	String advanceTotalAmt=(String) params.get("advanceTotalAmt");
	String freezeTotalAmt=(String) params.get("freezeTotalAmt");
	String suspenseTotalAmt=(String) params.get("suspenseTotalAmt");
	String payId=(String) params.get("payId");
	String invoiceId=(String) params.get("invoiceId");
	String 	attachmentNum=(String) params.get("attachmentNum");
	String providerName=(String) params.get("providerName");
	String 	provActNo=(String) params.get("provActNo");
	String bankName=(String) params.get("bankName");
	String provActCurr=(String) params.get("provActCurr");
	String payDate=(String) params.get("payDate");
	String payModeName=(String) params.get("payModeName");
	String advancePayId=(String) params.get("advancePayId");
	String payTotalAmt=(String) params.get("payTotalAmt");
	
	PayQueryBean bean = new PayQueryBean();
	
	bean.setCntNum(cntNum);
//	bean.setCntAllAmt(new BigDecimal(cntAllAmt));
	bean.setZbAmt(zbAmt);
	bean.setCntType(cntType);
	bean.setNormarlTotalAmt(normarlTotalAmt);
	bean.setAdvanceTotalAmt(advanceTotalAmt);
	bean.setFreezeTotalAmt(freezeTotalAmt);
	bean.setSuspenseTotalAmt(suspenseTotalAmt);
	bean.setPayId(payId);
	bean.setInvoiceId(payId);
	///bean.setAttachmentNum(new BigDecimal(attachmentNum));
	bean.setProviderName(providerName);
	bean.setProvActNo(provActNo);
	bean.setBankName(bankName);
	bean.setProvActCurr(provActCurr);
	bean.setPayDate(payDate);
	bean.setPayModeName(payModeName);
	bean.setAdvancePayId(advancePayId);
	//bean.setPayTotalAmt(new BigDecimal(payTotalAmt));
	
	// //插入excel数据内容
			try {
				
				List<PayQueryBean> projects = new ArrayList<PayQueryBean>();
				bean.setFlag("1");
				//projects = es.exportExcute(bean);
				//資產類型
				PayQueryBean prebean=es.getPrePayByPayId((String)params.get("payid"));
				if(prebean.getCntType().equals("0")){
					prebean.setCntType("资产类");
				}else if(prebean.getCntType().equals("1")){
					prebean.setCntType("费用类");
				}
				
				 
				//发票状态 0-录入 【1-校验失败】 2-创建成功 3-回冲
				switch (Integer.parseInt(prebean.getDataFlagInvoice())) {
				case 0:
					prebean.setDataFlagInvoice("录入");
					break;
				case 1:
					prebean.setDataFlagInvoice("校验失败");
					break;
				case 2:
					prebean.setDataFlagInvoice("创建成功");
					break;
				case 3:
					prebean.setDataFlagInvoice("回冲");
					break;

				default:
					break;
				}
				
				//付款状态 0-录入 【1-校验失败】  2-付款中 3-支付成功  4-付款回冲中   5-回冲
				switch (Integer.parseInt(prebean.getDataFlagPay())) {
				case 0:
					prebean.setDataFlagPay("录入");
					break;
				case 1:
					prebean.setDataFlagPay("校验失败");
					break;
				case 2:
					prebean.setDataFlagPay("付款中");
					break;
				case 3:
					prebean.setDataFlagPay("支付成功");
					break;
				case 4:
					prebean.setDataFlagPay("付款回冲中");
					break;
				case 5:
					prebean.setDataFlagPay("回冲");
					break;

				default:
					break;
				}
				
				//質保金比例
				 
				BigDecimal NormarlTotalAmt=new BigDecimal(prebean.getNormarlTotalAmt());
				BigDecimal AdvanceTotalAmt=new BigDecimal(prebean.getAdvanceTotalAmt());
				BigDecimal CntAllAmt= prebean.getCntAllAmt();
				BigDecimal sum=NormarlTotalAmt.add(AdvanceTotalAmt);
				BigDecimal d=sum.divide(CntAllAmt,4,BigDecimal.ROUND_HALF_UP);
				BigDecimal s=d.multiply(new BigDecimal("100"));
				prebean.setProgRess(s.toString());
				projects.add(prebean);
				
				beansMap.put("payInfo", projects);
				exportSucFlag = true;
				exportMemo = "处理成功，可下载";
				//增加一条汇总数据
			} catch (Exception e) {
				exportMemo = "【处理失败】， ：" ;
				exportSucFlag = false;
				CommonLogger.error("数据导出时往excel里插入数据失败" + e.getMessage()
						+ "ExportExcel,getSimpleExcelData");
				e.printStackTrace();
			}
			try {
				// 可按查找数据量进行分段加载
				ExcelExportUtility.loadJxlExcelData(beansMap, jxlExporter);
			} catch (Exception e) {
				exportSucFlag = false;
				exportMemo = "【处理失败】，";
				e.printStackTrace();
			} finally {
				params.put("exportSucFlag", exportSucFlag);
				params.put("exportMemo", exportMemo);
			}
		
	}

	@Override
	public void start(String taskId, Map params) throws Exception {
		boolean exportSucFlag = false; // Excel导出操作是否成功标识
		params.put("exportSucFlag", exportSucFlag);

	}

	@Override
	public void end(String taskId, Map params) throws Exception {
		CommonExcelDealBean bean = new CommonExcelDealBean();
		bean.setTaskId(taskId);
		if ((Boolean) params.get("exportSucFlag")) {
			// 成功
			bean.setDataFlag("03");
		} else {
			// 失败
			bean.setDataFlag("02");
		}
		bean.setProcMemo((String) params.get("exportMemo"));
		excelDealService.updateExcelResult(bean);

	}

}
