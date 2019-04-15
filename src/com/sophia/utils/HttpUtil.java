package com.sophia.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.qssh.base.util.http.HttpClientConnectionManager;
import com.qssh.bidata.util.EmojiFilter;

public class HttpUtil {
	 //http客户端    
    public static DefaultHttpClient httpclient;  
    
    static {    
        httpclient = new DefaultHttpClient();    
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端  
    }
    
    public static String _get(String url){
    	DefaultHttpClient httpclient = new DefaultHttpClient();    
        httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端  
    	HttpGet get = HttpClientConnectionManager.getGetMethod(url);
    	try {
			HttpResponse response = httpclient.execute(get);
			String result = getStringFromHttp(response.getEntity());
			get.releaseConnection();
			httpclient.close();
			return result;
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String post(String url,Map<String, String> params){
    	HttpPost post = HttpClientConnectionManager.getPostMethod(url);
    	try {
    		List<NameValuePair> pms = new ArrayList<NameValuePair>();
    		for(Entry<String, String> en:params.entrySet()){
    			pms.add(new BasicNameValuePair(en.getKey(), en.getValue()));
    		}
    		 HttpEntity httpentity = new UrlEncodedFormEntity(pms, "Utf-8");
    		 // 请求httpRequest
    		 post.setEntity(httpentity);
    		 
			HttpResponse response = httpclient.execute(post);
			return getStringFromHttp(response.getEntity());
    	} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
 // 获取所有的网页信息以String 返回
    private static String getStringFromHttp(HttpEntity entity) {

        StringBuffer buffer = new StringBuffer();

        try {
            // 获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    entity.getContent(),"UTF-8"));

            // 将返回的数据读到buffer中
            String temp = null;

            while ((temp = reader.readLine()) != null) {
                buffer.append(temp);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EmojiFilter.filterEmoji(buffer.toString());
    }
    
}
