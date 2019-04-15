package com.sophia.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WRObject {
	private String filePath;
	private int maxSize = 50000;
	
	/**
	 * 可读写对象
	 * @param filePath
	 */
	public WRObject(String filePath){
		this.filePath = filePath;
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	/**
	 * 可读写对象
	 * @param filePath
	 * @param maxSize
	 */
	public WRObject(String filePath,int maxSize){
		this(filePath);
		this.maxSize = maxSize;
	}
	
	/**
	 * 写对象
	 * @param obj
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> void writeObj(Object obj){
		if(obj instanceof List){
			List<T> list = (List) obj;
			List<T> writeList = new ArrayList<T>();
			for(int i=0;i<list.size();i+=maxSize){
				int end = i+maxSize;
				end = end > list.size() ? list.size() : end;
				String fileName = filePath + "/" + i + "_" + end+".obj";
				writeList.clear();
				for(int j=i;j<end;j++){
					writeList.add(list.get(j));
				}
				WRObject.writeObj(fileName, writeList);
			}
		}else{
			String fileName = filePath + "1"+".obj";
			WRObject.writeObj(fileName, obj);
		}
	}
	
	/**
	 * 读一个对象文件
	 * @return
	 */
	public <T> T readObj(){
		List<T> list = readObjs();
		return list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * 读对象文件
	 * @return
	 */
	public <T> List<T> readObjs(){
		List<T> list = new ArrayList<T>();
		String[] fileNames = new File(filePath).list();
		for(String fileName :fileNames){
			List<T> objs = WRObject.readObj(filePath + fileName);
			list.addAll(objs);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static void writeObj(String fileName,Object obj){
		try {
			File file = new File(fileName);
			file.createNewFile();
			
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			out.writeObject(obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			List<Object> list = (List<Object>) obj;
			
			String simpleName = list.get(0).getClass().getSimpleName();
			
			System.out.println("错误??"+simpleName);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readObj(String fileName){
		try {
			File file = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			T obj = (T)in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
