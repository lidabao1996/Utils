package com.sophia.utils;

import java.math.BigDecimal;

/***
 * 浮点型 计算
 * @author hyz79
 */
public class CountUtil {
	/***
	 * 空返回0  ,非空返回double
	 * @param d1
	 * @return
	 */
	public static double getDouble(Double d1){
		return d1 == null ? 0:d1.doubleValue();
	}
	
	/***
	 * 加法   最后保留两位小数
	 * @param ds
	 * @return
	 */
	public static double addRound(Double ...ds){
		return round(add(ds));
	}
	/***
	 * 减法  保留两位小数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subtractRount(Double d1,Double d2){
		return round(subtract(d1, d2));
	}
	/***
	 * 除法  保留两位小数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double devideRound(Double d1,Double d2){
		return round(devide(d1, d2));
	}
	/***
	 * 乘法 保留两位小数
	 * @param arr
	 * @return
	 */
	public static double multiplyRound(Double ...arr){
		return round(multiply(arr));
	}
	
	/***
	 * 加法
	 * @param ds
	 * @return
	 */
	public static double add(Double ...ds){
		BigDecimal c = new BigDecimal(0);
		for(Double d : ds){
			c = c.add(new BigDecimal(getDouble(d)));
		}
		return c.doubleValue();
	}
	
	/***
	 * 减法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subtract(Double d1,Double d2){
		BigDecimal c1 = new BigDecimal(getDouble(d1));
		BigDecimal c2 = new BigDecimal(getDouble(d2));
		BigDecimal result = c1.subtract(c2);
		return result.doubleValue();
	}
	/***
	 * 除法
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double devide(Double d1,Double d2){
		BigDecimal c1 = new BigDecimal(getDouble(d1));
		BigDecimal c2 = new BigDecimal(getDouble(d2));
		if(d2 == 0){
			return 0;
		}
		BigDecimal result = c1.divide(c2,10,BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	/**
	 * 乘法
	 * @param arr
	 * @return
	 */
	public static double multiply(Double ...arr){
		BigDecimal result = new BigDecimal(1);
		for(Double d2 : arr){
			result = result.multiply(new BigDecimal(getDouble(d2)));
		}
		return result.doubleValue();
	}
	/***
	 * 四舍五入
	 * @param d1
	 * @param scale
	 * @return
	 */
	public static double round(double d1,int scale){
		BigDecimal c1 = new BigDecimal(getDouble(d1));
		BigDecimal result = c1.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
	/***
	 * 四舍五入  两位小数点
	 * @param d1
	 * @return
	 */
	public static double round(double d1){
		BigDecimal c1 = new BigDecimal(getDouble(d1));
		BigDecimal result = c1.setScale(2, BigDecimal.ROUND_HALF_UP);
		return result.doubleValue();
	}
}
