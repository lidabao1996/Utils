package com.sophia.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class CollectionUtil {
	public static <T> Set<T> arrayToSet(T[] arr){
		Set<T> set = new HashSet<T>();
		for(T t:arr){
			set.add(t);
		}
		return set;
	}
	public static <T> Set<T> copySet(Set<T> set){
		Set<T> copy = new HashSet<T>();
		for(T t:set){
			copy.add(t);
		}
		return copy;
	}
	public static <T> Set<T> removeSet(Set<T> a,Set<T> b){
		Set<T> copySet = copySet(a);
		copySet.removeAll(b);
		return copySet;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<Map<String, Object>> group(Collection<T> list,String field){
		List<Map<String, Object>> ls = new ArrayList<Map<String,Object>>();
		Object oldValue = null;
		List<T> groupItems = new ArrayList<T>();
		for(T t:list){
			Object groupValue = "";
			if(t instanceof Map){
				groupValue = ((Map<String, Object>)t).get(field);
			}else{
				groupValue = ReflectUtil.getValueByFieldName(field, t);
			}
			
			if(oldValue == null || !oldValue.equals(groupValue)){
				oldValue = groupValue;
				Map<String, Object> item = new HashMap<String, Object>();
				item.put(field, oldValue);
				groupItems = new ArrayList<T>();
				item.put("items", groupItems);
				
				ls.add(item);
			}
			groupItems.add(t);
		}
		return ls;
	}
}
