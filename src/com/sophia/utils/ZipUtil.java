package com.sophia.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 * 
 * @author hyz 2014-06-12
 *
 */
public class ZipUtil {
	
	public static void zip(String filePath,FileOutputStream out){
		Map<String, File> files = getFiles(filePath);
		zip(out, files);
	}
	
	public static Map<String, File> getFiles(String fileName){
		File file = new File(fileName);
		Map<String, File> fileMap = new HashMap<String, File>();
		getFiles(fileMap, file, "");
		return fileMap;
	}
	private static void getFiles(Map<String, File> fileMap,File file,String path){
		if(file.isDirectory()){
			path += file.getName() + "/";
			File[] files = file.listFiles();
			for(File child : files){
				getFiles(fileMap, child, path);
			}
		}else{
			fileMap.put(path+file.getName(), file);
		}
	}
	
	/**
	 * 压缩
	 * @param out
	 * @param files
	 */
	public static void zip(OutputStream out,Map<String, File> files){
		ZipOutputStream zipOut = new ZipOutputStream(out);
		for(Entry<String, File> en : files.entrySet()){
			try {
				zipOut.putNextEntry(new ZipEntry(en.getKey()));
				FileInputStream in = new FileInputStream(en.getValue());
				
				byte[] buffer = new byte[1024*512]; 
				int length = 0;
				while ((length = in.read(buffer)) > 0) {
					zipOut.write(buffer, 0, length);
				}
				//这里一定要关闭  
				in.close();
				zipOut.closeEntry();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			zipOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解压缩
	 * @param in
	 * @param folder
	 */
	public static void unZip(InputStream in,String folder){
		File file = new File(folder);
		if(!file.exists()){
			file.mkdirs();
		}
		
		ZipInputStream zipIn = new ZipInputStream(in);
		ZipEntry zipEntry ;
		try {
			while((zipEntry = zipIn.getNextEntry()) != null){
				if(zipEntry.isDirectory()){
					File zipFile = new File(file,zipEntry.getName());
					zipFile.mkdirs();
				}else{
					File zipFile = new File(folder + "/"+zipEntry.getName());
					
					String parentPath = zipEntry.getName().substring(0,zipEntry.getName().lastIndexOf("/"));
					File parent = new File(folder,parentPath);
					if(!parent.exists()){
						parent.mkdirs();
					}
					zipFile.createNewFile();
					
					FileOutputStream out = new FileOutputStream(zipFile);
					byte[] buffer = new byte[1024*512]; 
					int length = 0;
					while ((length = zipIn.read(buffer)) > 0) {
						out.write(buffer, 0, length);
					}
					//这里一定要关闭  
					out.close();
				}
				//这里一定要关闭  
				zipIn.closeEntry();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//测试
	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream(new File("c://mm.zip"));
		
		unZip(in, "c://mm");
		in.close();
		
		File f = new File("c://mm.zip");
		f.createNewFile();
		FileOutputStream out = new FileOutputStream(f);
		Map<String, File> files = new HashMap<String, File>();
		files.put("tt/test.zip", new File("c://test.zip"));
		zip(out, files);
		out.close();
	}
}
