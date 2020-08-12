package com.zcq.seckilling.common;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @ClassName: Sheet
 * @Description: Excel的工作薄实体
 * @author Chopin
 * @date 2014年12月13日
 * @version 1.0
 */
public class ExcelSheetEntity {
	
	private String sheetName;
	private String[] colNames;
	private String[] captions;
	private List data;
	
	/**
	 * 
	 * @ClassName: Sheet
	 * @Description: Excel的工作薄实体
	 * @param cols json格式的字符串，"表头名称:entity字段名称,表头名称:entity字段名称"
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 */
	public static ExcelSheetEntity newInstance(String cols, List data){
			return new ExcelSheetEntity("sheet",cols,data);
	}
	
	/**
	 * 
	 * @ClassName: Sheet
	 * @Description: Excel的工作薄实体
	 * @param sheetName 字符串，工作薄名称
	 * @param cols json格式的字符串，"表头名称:entity字段名称,表头名称:entity字段名称"
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 */
	public static ExcelSheetEntity newInstance(String sheetName, String cols, List data){
		
			return new ExcelSheetEntity(sheetName,cols,data);
	}
	
	private ExcelSheetEntity(){};

	private ExcelSheetEntity(String sheetName, String cols, List data){
		if(isCol(cols)){
			String[] cs=cols.split(",");
			String[] cm=new String[cs.length];
			String[] cp=new String[cs.length];
			for(int i=0;i<cs.length;i++){
				cp[i]=cs[i].split(":")[0];
				cm[i]=cs[i].split(":")[1];
			}
			this.colNames=cm;
			this.captions=cp;
			this.sheetName=sheetName;
			this.data=data;
		}
	}
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String[] getColNames() {
		return colNames;
	}
	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
	
	public String[] getCaptions() {
		return captions;
	}

	public void setCaptions(String[] captions) {
		this.captions = captions;
	}

	/**
	 * 
	 * Class Name: Sheet.java
	 * @Description: 验证是否为json字符串
	 * @author Chopin
	 * @date 2014年12月13日
	 * @version 1.0
	 * @param str
	 * @return
	 */
	private static Boolean isCol(String str){
		Pattern pattern= Pattern.compile("(.*?:.*?[^,])*");
		Matcher matcher =pattern.matcher(str);
		return matcher.matches();
	}
	
}
