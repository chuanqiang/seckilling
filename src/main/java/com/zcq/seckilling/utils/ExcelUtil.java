package com.zcq.seckilling.utils;

import com.zcq.seckilling.common.ExcelSheetEntity;
import org.apache.poi.hssf.usermodel.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: ExcelUtil
 * @Description: Excel工具
 * TODO 待扩展
 * @author Chopin
 * @date 2014年12月13日
 * @version 1.0
 */
public class ExcelUtil {
	/**
	 * 
	 * Class Name: ExcelUtil.java
	 * @Description: 创建Excel对象
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 * @param sheets
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook newWorkbook(List<ExcelSheetEntity> sheets)throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		for(ExcelSheetEntity entity: sheets){
			HSSFSheet sheet = workbook.createSheet(entity.getSheetName()); 
			// 产生Excel表头  
	        HSSFRow header = sheet.createRow(0); // 第0行 
	        
	        for(int i=0;i<entity.getCaptions().length;i++){
	        	 // 产生标题列  
		        header.createCell(i).setCellValue(entity.getCaptions()[i]);  
	        }
	        HSSFCellStyle cellStyle = workbook.createCellStyle();  
	        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));  
	        // 填充数据  
	        for (int n=0;n<entity.getData().size();n++) {
	        	HSSFRow row = sheet.createRow(n+1);
	        	Object data=entity.getData().get(n);
	        	Class clazz=data.getClass();
	        	Field[] fields=clazz.getDeclaredFields();
	        	for(int i=0;i<entity.getColNames().length;i++){
	        		HSSFCell cell=row.createCell(0);
	        		for(Field field:fields){
	        			String colName=entity.getColNames()[i];
		        		if(field.getName().equals(colName)){
		        			try{
		        				Method method=clazz.getMethod("get"+colName.substring(0,1).toUpperCase()+colName.substring(1), null);
		        				cell.setCellValue(method.invoke(data, null).toString());
		        			}catch(NoSuchMethodException e){
		        				throw new Exception("HSSFWorkbook ERROR : 【列名或get方法不存在】"+e);
		        			}catch(NullPointerException e){
		        				throw new Exception("HSSFWorkbook ERROR : 【不存在】"+e);
		        			}catch(Exception e){
		        				throw new Exception("HSSFWorkbook ERROR : "+e);
		        			}
		        		}
		        	}
	        	}
	        }  
		}
		return workbook;
	}
	
	/**
	 * 
	 * Class Name: ExcelUtil.java
	 * @Description: 创建Excel对象
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 * @param sheets
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook newWorkbook(List<Map<String,Object>> datalist, String sheetName, String cols)throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName); 
		// 产生Excel表头  
        HSSFRow header = sheet.createRow(0); // 第0行 
        
        //解析列头设置
        String[] cs=cols.split(",");
        String[] captions=new String[cs.length];
        String[] colsNames=new String[cs.length];
		for(int i=0;i<cs.length;i++){
			captions[i]=cs[i].split(":")[0];
			colsNames[i]=cs[i].split(":")[1];
		}
        for(int i=0;i<captions.length;i++){
        	 // 产生标题列  
	        header.createCell(i).setCellValue(captions[i]);  
        }

        HSSFCellStyle cellStyle = workbook.createCellStyle();  
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));  
        // 填充数据  
        for (int n=0;n<datalist.size();n++) {
        	HSSFRow row = sheet.createRow(n+1);
        	Map<String,Object> data=datalist.get(n);
        	for(int i=0;i<colsNames.length;i++){
        		HSSFCell cell=row.createCell(i);
        		String colName=colsNames[i];
    			try{
    				Object cellValue=data.get(colName);
    				if(cellValue instanceof Date){
    					Date date=(Date)cellValue;
    					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    					cell.setCellValue(sdf.format(date));
    				}else{
    					cell.setCellValue(cellValue!=null?cellValue.toString():"");
    				}
    				
    			}catch(NullPointerException e){
    				throw new Exception("HSSFWorkbook ERROR : 【不存在】"+e);
    			}catch(Exception e){
    				throw new Exception("HSSFWorkbook ERROR : "+e);
    			}
        	}
        }  
		return workbook;
	}
	/**
	 * 
	 * Class Name: ExcelUtil.java
	 * @Description: 创建Excel对象
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 * @param sheets
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook newWorkbook(List<Map<String,Object>> datalist, String sheetName, String cols, String first)throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(sheetName); 
		// 产生Excel表头  
        HSSFRow title = sheet.createRow(0); // 第0行 
        String[] strArr = first.split(",");
        for(int i=0;i<strArr.length;i++){
        	title.createCell(i).setCellValue(strArr[i]);
        }
        
        HSSFRow header = sheet.createRow(1); // 第1行
        //解析列头设置
        String[] cs=cols.split(",");
        String[] captions=new String[cs.length];
        String[] colsNames=new String[cs.length];
		for(int i=0;i<cs.length;i++){
			captions[i]=cs[i].split(":")[0];
			colsNames[i]=cs[i].split(":")[1];
		}
        for(int i=0;i<captions.length;i++){
        	 // 产生标题列  
	        header.createCell(i).setCellValue(captions[i]);  
        }

        HSSFCellStyle cellStyle = workbook.createCellStyle();  
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));  
        // 填充数据  
        for (int n=0;n<datalist.size();n++) {
        	HSSFRow row = sheet.createRow(n+2);
        	Map<String,Object> data=datalist.get(n);
        	for(int i=0;i<colsNames.length;i++){
        		HSSFCell cell=row.createCell(i);
        		String colName=colsNames[i];
    			try{
    				Object cellValue=data.get(colName);
    				if(cellValue instanceof Date){
    					Date date=(Date)cellValue;
    					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    					cell.setCellValue(sdf.format(date));
    				}else{
    					cell.setCellValue(cellValue!=null?cellValue.toString():"");
    				}
    				
    			}catch(NullPointerException e){
    				throw new Exception("HSSFWorkbook ERROR : 【不存在】"+e);
    			}catch(Exception e){
    				throw new Exception("HSSFWorkbook ERROR : "+e);
    			}
        	}
        }  
		return workbook;
	}
	/**
	 * 
	 * Class Name: ExcelUtil.java
	 * @Description: 创建Excel对象
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 * @param sheets
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook newWorkbook(ExcelSheetEntity entity)throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(entity.getSheetName()); 
		// 产生Excel表头  
        HSSFRow header = sheet.createRow(0); // 第0行 
        
        for(int i=0;i<entity.getCaptions().length;i++){
        	 // 产生标题列  
	        header.createCell(i).setCellValue(entity.getCaptions()[i]);  
        }
        HSSFCellStyle cellStyle = workbook.createCellStyle();  
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
        // 填充数据  
        for (int n=0;n<entity.getData().size();n++) {
        	HSSFRow row = sheet.createRow(n+1);
        	Object data=entity.getData().get(n);
        	Class clazz=data.getClass();
        	Field[] fields=clazz.getDeclaredFields();
        	for(int i=0;i<entity.getColNames().length;i++){
        		HSSFCell cell=row.createCell(i);
        		String colName=entity.getColNames()[i];
        		for(Field field:fields){
	        		if(field.getName().equals(colName)){
	        			try{
	        				Method method=clazz.getMethod("get"+colName.substring(0,1).toUpperCase()+colName.substring(1), null);
	        				Object cellValue=method.invoke(data, null)==null?"":method.invoke(data, null);
	        				System.out.println(cellValue.toString());
	        				if(cellValue instanceof Date){
	        					Date date=(Date)cellValue;
	        					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        					cell.setCellValue(sdf.format(date));
	        				}else{
	        					cell.setCellValue(cellValue.toString());
	        				}
	        				
	        			}catch(NoSuchMethodException e){
	        				throw new Exception("HSSFWorkbook ERROR : 【列名或get方法不存在】"+e);
	        			}catch(NullPointerException e){
	        				throw new Exception("HSSFWorkbook ERROR : 【不存在】"+e);
	        			}catch(Exception e){
	        				throw new Exception("HSSFWorkbook ERROR : "+e);
	        			}
	        		}
	        	}
        	}
        }  
     // 输出文件     
//        FileOutputStream fileOut = new FileOutputStream("e:/123.xls");     
//        workbook.write(fileOut);     
//        fileOut.close(); 
        
		return workbook;
	}

}
