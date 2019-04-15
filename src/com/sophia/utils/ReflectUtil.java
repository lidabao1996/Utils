package com.sophia.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 反射工具类
 * @author hyz
 *
 */
public class ReflectUtil {
	/**
	 * @param cls
	 * @param stopCls
	 * @return
	 */
	public static List<Class<?>> getSupCls(Class<?> cls,Class<?> stopCls){
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(cls);
		
		if(stopCls == cls){
			return list;
		}
		_getSupCls(cls, stopCls, list);
		return list;
	}
	
	private static void _getSupCls(Class<?> cls,Class<?> stopCls,List<Class<?>> list){
		
		
		if(cls.getSuperclass()!=null && cls.getSuperclass()!=stopCls){
			//System.out.println(cls.getSuperclass().getSimpleName()+"-"+stopCls.getSimpleName());
			list.add(cls.getSuperclass());
			_getSupCls(cls.getSuperclass(),stopCls,list);
		}
	}
	/**
	 * 获取对象的所有字段 包括父类
	 * @param cls
	 * @param stopCls
	 * @return
	 */
	public static List<Field> getFields(Class<?> cls,Class<?> stopCls){
		List<Class<?>> list = getSupCls(cls, stopCls);
		List<Field> allFields = new ArrayList<Field>();
		for(Class<?> clz : list){
			Field[] fields = clz.getDeclaredFields();
			for(Field field :fields){
				if(field.getName().equals("serialVersionUID")){
					continue;
				}
				if(!allFields.contains(field)){
					allFields.add(field);
				}
			}
		}
		return allFields;
	}
	/**
	 * 获取对象所有字段 
	 * @param cls
	 * @param stopCls
	 * @return
	 */
	public static Map<String, Field> getFieldsMap(Class<?> cls,Class<?> stopCls){
		List<Class<?>> list = getSupCls(cls, stopCls);
		Map<String, Field> allFields = new HashMap<String, Field>();
		for(Class<?> clz : list){
			Field[] fields = clz.getDeclaredFields();
			for(Field field :fields){
				if(!allFields.containsKey(field.getName())){
					allFields.put(field.getName(), field);
				}
			}
		}
		return allFields;
	}
	/**
	 * 拿到对象的所有属性值 包括父类中的
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> getAttributeValues(Object obj){
		Map<String, Object> map = new HashMap<String, Object>();
		if(obj == null){
			return map;
		}
		List<Field> fields = getFields(obj.getClass(), null);
		for(Field field :fields){
			map.put(field.getName(), reflectGetField(field, obj));
		}
		return map;
	}
	/**
	 * 执行field的get方法获取值
	 * @param field
	 * @param obj
	 * @return
	 */
	public static Object reflectGetField(Field field,Object obj){
		Object value = null;
		try {
			field.setAccessible(true);
			value = field.get(obj);
		} catch (Exception e) {
			System.err.println(obj.getClass() + "[" +field.getName() + "]:读取错误");
		}
		return value;
	}
	/**
	 * 执行field的set方法设值  返回 执行结果  true/false
	 * @param field
	 * @param obj
	 * @param value
	 * @return
	 */
	public static boolean reflectSetField(Field field,Object obj,Object value){
		boolean result = false;
		try {
			field.setAccessible(true);
			field.set(obj, value);
			result = true;
		} catch (Exception e) {
			result = false;
			System.err.println(obj.getClass() + "[" +field.getName() + "]:读取错误");
		}
		return result;
	}
	/**
	 * 根据字段名称 获取字段值
	 * @param fieldName
	 * @param obj
	 * @return
	 */
	public static Object getValueByFieldName(String fieldName,Object obj){
		Map<String, Field> fieldsMap = getFieldsMap(obj.getClass(), null);
		if(!fieldsMap.containsKey(fieldName)){
			return null;
		}
		return reflectGetField(fieldsMap.get(fieldName),obj);
	}
	/**
	 * 根据字段名称给字段设值 返回 结果 true/false
	 * @param fieldName
	 * @param obj
	 * @param value
	 * @return
	 */
	public static boolean setValueByFieldName(String fieldName,Object obj,Object value){
		Map<String, Field> fieldsMap = getFieldsMap(obj.getClass(), null);
		if(!fieldsMap.containsKey(fieldName)){
			return false;
		}
		return reflectSetField(fieldsMap.get(fieldName), obj, value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getTypeClz(Class<?> clz){
		Type type = clz.getGenericSuperclass();
		if(type instanceof ParameterizedType){
			ParameterizedType pt = (ParameterizedType)type;
			Type[] types = pt.getActualTypeArguments();
			if(types.length >0 && types[0] instanceof Class){
				return (Class<T>) types[0];
			}
		}
		return (Class<T>)Object.class;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T  cloneObj(T obj){
		try {
			Class<T> clz = (Class<T>) obj.getClass();
			T t = clz.newInstance();
			List<Field> fields = ReflectUtil.getFields(clz, Object.class);
			for(Field f:fields){
				f.setAccessible(true);
				f.set(t, f.get(obj));
			}
			return t;
		} catch (Exception e) {
		}
		return null;
	}
}
