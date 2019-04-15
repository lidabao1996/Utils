package com.sophia.utils;


/**
 * 工具类,类型转换
 * @author hyz.persist
 *
 */
public class ClassTypeParse {
	
	public static Long toLong(String str){
		if(RegexUtil.isLong(str)){
			return Long.parseLong(str);
		}
		return null;
	}
	/**
	 * 将String转换成
	 * @param str
	 * @return
	 */
	public static Double toDouble(String str){
		if(RegexUtil.isFloat(str)){
			return Double.parseDouble(str);
		}
		return null;
	}
	
	/**
	 * 转换为int数组
	 * @param strs
	 * @return
	 */
	public static Double[] toDoubles(String[] strs){
		if(strs == null || strs.length == 0){
			return new Double[0];
		}
		Double[] doubles = new Double[strs.length];
		for(int i = 0;i<strs.length;i++){
			if(RegexUtil.isFloat(strs[i])){
				doubles[i] = Double.parseDouble(strs[i]);
			}else{
				return new Double[0];
			}
		}
		return doubles;
	}
	
	public static Integer toInt(String str){
		if(RegexUtil.isInt(str)){
			return Integer.parseInt(str);
		}
		return null;
	}
	/**
	 * 转换为int数组
	 * @param strs
	 * @return
	 */
	public static int[] toInts(String[] strs){
		if(strs == null || strs.length == 0){
			return new int[0];
		}
		int[] ints = new int[strs.length];
		for(int i = 0;i<strs.length;i++){
			if(RegexUtil.isInt(strs[i])){
				ints[i] = Integer.parseInt(strs[i]);
			}else{
				return new int[0];
			}
		}
		return ints;
	}
	/**
	 * 转换为Long数组
	 * @param strs
	 * @return
	 */
	public static Long[] toLongs(String[] strs){
		if(strs == null || strs.length == 0){
			return new Long[0];
		}
		Long[] longs = new Long[strs.length];
		for(int i = 0;i<strs.length;i++){
			if(RegexUtil.isLong(strs[i])){
				longs[i] = Long.parseLong(strs[i]);
			}else{
				return new Long[0];
			}
		}
		return longs;
	}
}
