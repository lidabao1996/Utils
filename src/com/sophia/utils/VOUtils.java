package com.sophia.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class VOUtils {
	@Note("转换的时候处理格式化字段")
	public static Map<String, Object> toFormatMap(Object obj,Set<String> formatFields){
		Map<String, Object> map = ReflectUtilSup.getAttributeValues(obj);
		for(Entry<String, Object> en : map.entrySet()){
			if(formatFields.contains(en.getKey())){
				en.setValue("***");
				continue;
			}
			Object value = en.getValue();
			if(value == null){
				en.setValue("");
				continue;
			}
			en.setValue(reverseToValue(value));
		}
		return map;
	}
	
	@Note("转换成Map")
	public static Map<String, Object> toMap(Object obj){
		Map<String, Object> map = ReflectUtilSup.getAttributeValues(obj);
		for(Entry<String, Object> en : map.entrySet()){
			Object value = en.getValue();
			if(value == null){
				en.setValue("");
				continue;
			}
			en.setValue(reverseToValue(value));
		}
		return map;
	}
	@Note("转换成List<Map>")
	public static List<Map<String, Object>> toArrayMap(List<?> objs){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for(Object obj : objs){
			list.add(toMap(obj));
		}
		return list;
	}
	@Note("反转为值")
	public static Object reverseToValue(Object value){
		if(value == null){
			return null;
		}
		if(value instanceof Date){
			return DateUtil.toDate((Date)value);
		}else if(value instanceof String){
			return value;
		}else if(value.getClass().isEnum()){
			return ((Enum<?>)value).ordinal();
		}
		return value;
	}
	
	private static Object getValue(Field field,Object obj){
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Note("得到Collections的sort方法")
	private static Method getCollectionsSortMethod(){
		try {
			Method method = Collections.class.getMethod("sort", List.class);
			return method;
		} catch (Exception e) {
		}
		return null;
	}
	@Note("强制执行Collections的sort方法")
	private static void sort(List<?> list,Method method){
		try {
			method.invoke(null, list);
		} catch (Exception e) {
		}
	}
	/**
	 * 结果全是描述性 枚举得到描述字段  数据字典得到中文值
	 * @param List
	 * @return
	 */
	public static List<Map<String, String>> toArrayDescMap(List<?> list){
		List<Map<String, String>> descList = new ArrayList<Map<String,String>>();
		for(Object obj : list){
			descList.add(toDescMap(obj));
		}
		return descList;
	}
	
	/**
	 * 结果全是描述性 枚举得到描述字段  数据字典得到中文值
	 * @param obj
	 * @return
	 */
	@Note("将对象转换为Map")
	public static Map<String, String> toDescMap(Object obj){
		Map<String, Object> map = ReflectUtilSup.getAttributeValues(obj);
		Map<String, String> descMap = new HashMap<String, String>();
		for(Entry<String, Object> en : map.entrySet()){
			Object value = en.getValue();
			
			if(value == null){
				//en.setValue("");
				descMap.put(en.getKey(), "");
				continue;
			}
			descMap.put(en.getKey(), reverseToDescValue(value,en.getKey()));
		}
		return descMap;
	}
	
	@SuppressWarnings("rawtypes")
	@Note("反转为值")
	public static String reverseToDescValue(Object value,String fieldName){
		if(value == null){
			return null;
		}
		
		//数据字典类型的
//		if(DicCache.isDic(fieldName)){
//			return DicCache.findDicValue(fieldName, String.valueOf(value));
//		}
		
		if(value instanceof Date){
			return DateUtil.toDate((Date)value);
		}else if(value instanceof String){
			return (String)value;
		}else if(value.getClass().isEnum()){
			//return EnumHelper.getDesc((Enum)value);
		}
		return String.valueOf(value);
	}
}
