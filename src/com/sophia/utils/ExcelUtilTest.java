package com.sophia.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


public class ExcelUtilTest {
	public static void main(String[] args) throws Exception {
		export(); //导出
		
		//读取 excel
		//导出 模板
//		exportTemplate();
		
		//读取Excel
//		readExcel();
		
//		exportObjs();
		
	}
	
	public static void readExcel() throws Exception{
		List<Map<String,String>> list = ExcelUtil.readExcel(new File("c://poiExcelTemplate.xls"), 1, 0, new String[]{
			"seq",
			"name",
			"gender",
			"age",
			"money"
		});
		
		System.out.println(list);
		
	}
	
	/*导出 模板*/
	public static void exportTemplate()throws Exception{
		File file = new File("c://poiExcelTemplate.xls");
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		
		List<Map<String, String>> values = new ArrayList<Map<String,String>>();
		
		ExcelUtil.ExcelConfig cf = new ExcelUtil.ExcelConfig();
		cf.values = values;
		cf.out = out;
		cf.columnWidths = Arrays.asList(new Integer[]{1500,2500,1200,1200,2000});
		cf.fieldDescs = Arrays.asList(new String[]{"序号","姓名","性别","年龄","资产"});
		cf.fieldNames = Arrays.asList(new String[]{"seq","name","gender","age","money"});
		cf.fileName = "POIExcel";
		cf.description = "一个说明文件:xjakldsj\n          fkljsadgjsdg\nhjhjkasdfhjksahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明ahdfhahuqwh	说明\n";
		//cf.title = "员工信息模板";
		
		ExcelUtil.createExcel(cf);
		out.close();
	}
	
	/**
	 * 常规用法
	 */
	public static void exportObjs() throws Exception{
		File file = new File("c://positions.xls");
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		
		List<Position> positions = new ArrayList<Position>();
		Position p = new Position();
		p.setPositionCode("A01");
		p.setPositionName("A岗");
		
		positions.add(p);
		
		p = new Position();
		p.setPositionCode("B01");
		p.setPositionName("B岗");
		positions.add(p);
		
		p = new Position();
		p.setPositionCode("C01");
		p.setPositionName("C岗");
		positions.add(p);
		
		List<Map<String, String>> values = VOUtils.toArrayDescMap(positions);
		
		ExcelUtil.ExcelConfig cf = new ExcelUtil.ExcelConfig();
		cf.values = values;
		cf.out = out;
		cf.columnWidths = Arrays.asList(new Integer[]{1500,2500});
		cf.fieldDescs = Arrays.asList(new String[]{"岗位编号","岗位名称"});
		cf.fieldNames = Arrays.asList(new String[]{"positionCode","positionName"});
		cf.fileName = "POIExcel";
		
		//cf.title = "员工信息模板";
		
		ExcelUtil.createExcel(cf);
		out.close();
	}
	
	//导出  
	public static void export() throws IOException{
		File file = new File("c://poiExcel.xls");
		file.createNewFile();
		FileOutputStream out = new FileOutputStream(file);
		
		List<Map<String, String>> values = new ArrayList<Map<String,String>>();
		Map<String, String> valMap;
		valMap = new HashMap<String, String>();
		valMap.put("seq", "1");
		valMap.put("name", "张三");
		valMap.put("gender", "男");
		valMap.put("age", "10");
		valMap.put("money", "100");
		values.add(valMap);
		
		valMap = new HashMap<String, String>();
		valMap.put("seq", "2");
		valMap.put("name", "李四");
		valMap.put("gender", "女");
		valMap.put("age", "15");
		valMap.put("money", "150");
		values.add(valMap);
		
		valMap = new HashMap<String, String>();
		valMap.put("seq", "3");
		valMap.put("name", "王五");
		valMap.put("gender", "男");
		valMap.put("age", "12");
		valMap.put("money", "120");
		values.add(valMap);
		
		ExcelUtil.ExcelConfig cf = new ExcelUtil.ExcelConfig();
		cf.values = values;
		cf.out = out;
		cf.columnWidths = Arrays.asList(new Integer[]{1500,2500,1200,1200,2000});
		cf.fieldDescs = Arrays.asList(new String[]{"序号","姓名","性别","年龄","资产"});
		cf.fieldNames = Arrays.asList(new String[]{"seq","name","gender","age","money"});
		cf.fileName = "POIExcel";
		cf.title = "员工信息";
		cf.group = true;
		cf.groupField = "gender";
		cf.summary = true;
		cf.summaryFields = Arrays.asList(new String[]{"money"});
		cf.groupSummary = true;
		cf.groupSummaryFields = Arrays.asList(new String[]{"money"});
		
		cf.columnRenders = new ArrayList<ExcelUtil.ColumnRender>();
		cf.columnRenders.add(null);
		cf.columnRenders.add(new ExcelUtil.ColumnRender() {
			@Override
			public String render(String value) {
				return "渲染::::" + value;
			}
		});
		cf.columnRenders.add(null);
		cf.columnRenders.add(null);
		cf.columnRenders.add(null);
		
		ExcelUtil.createExcel(cf);
		out.close();
	}
}
