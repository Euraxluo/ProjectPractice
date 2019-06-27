package com.lailelaodi.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   


public class Sendsms {
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	private int mobile_code(){
		return (int)((Math.random()*9+1)*100000);
	}


	public int postcode(String mobile){
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		int mobile_code = mobile_code();

		client.getParams().setContentCharset("Utf-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");


		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

		NameValuePair[] data = {
				new NameValuePair("account", "c22021306"), //APIID
				new NameValuePair("password", "4f17f0bf6730383fc9929015d1e858c0"),  //APIKEY
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			System.out.println(code+":"+msg+":"+smsid);

			if("2".equals(code)){
				return mobile_code;
			}
			else{
				return 0;
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally{
			// Release connection
			method.releaseConnection();
		}
		return 0;
	}
}