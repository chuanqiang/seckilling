//package com.zcq.seckilling.common;
//
//import com.zcq.seckilling.utils.ExcelUtil;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.util.List;
//import java.util.Map;
//
//public class ViewFiles extends AbstractExcelView {
//
//	@Override
//	protected void buildExcelDocument(Map<String, Object> model,
//	                                  HSSFWorkbook workbook, HttpServletRequest request,
//	                                  HttpServletResponse response) throws Exception {
//
//		String excelName =(String) model.get("fileName");
//        response.setContentType("APPLICATION/OCTET-STREAM");
////        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(excelName, "UTF-8"));
//
//		response.setHeader("Content-Disposition",
//				"attachment;fileName=" + new String(excelName.getBytes("utf-8"), "ISO8859-1"));
//		response.setCharacterEncoding("utf-8");
//
//		OutputStream ouputStream = response.getOutputStream();
//		try{
//	        @SuppressWarnings("unchecked")
//	        List<ExcelSheetEntity> list=(List<ExcelSheetEntity>)model.get("list");
//	        ExcelSheetEntity entity=(ExcelSheetEntity)model.get("entity");
//	        HSSFWorkbook wb=(HSSFWorkbook)model.get("workbook");
//	        if(wb!=null){
//	        	workbook=wb;
//	        }else if(list==null){
//	        	workbook = ExcelUtil.newWorkbook(entity);
//	        }else{
//	        	workbook = ExcelUtil.newWorkbook(list);
//	        }
//		}catch(Exception e){
//			throw e;
//		}finally{
//	        workbook.write(ouputStream);
//	        ouputStream.flush();
//	        ouputStream.close();
//		}
//
//
//	}
//
//}
