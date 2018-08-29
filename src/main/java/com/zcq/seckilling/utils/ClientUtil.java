package com.zcq.seckilling.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientUtil {

	public static String sendGet(String url) throws ClientProtocolException, IOException {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		// 设置超时时间 add by zhang_cq
		//httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		//httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);

		HttpGet get = new HttpGet(url);
		InputStream in = null;
		try {
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity = new BufferedHttpEntity(entity);
				in = entity.getContent();
				byte[] read = new byte[1024];
				byte[] all = new byte[0];
				int num;
				while ((num = in.read(read)) > 0) {
					byte[] temp = new byte[all.length + num];
					System.arraycopy(all, 0, temp, 0, all.length);
					System.arraycopy(read, 0, temp, all.length, num);
					all = temp;
				}
				result = new String(all, "UTF-8");
			}
		} finally {
			if (in != null) in.close();
			get.abort();
		}

		return result;
	}

	public static String sendPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpPost get = new HttpPost(url);

// 创建表单参数列表
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			qparams.add(new BasicNameValuePair(key, params.get(key)));
		}

// 填充表单
		get.setEntity(new UrlEncodedFormEntity(qparams, "UTF-8"));

		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			entity = new BufferedHttpEntity(entity);

			InputStream in = entity.getContent();
			byte[] read = new byte[1024];
			byte[] all = new byte[0];
			int num;
			while ((num = in.read(read)) > 0) {
				byte[] temp = new byte[all.length + num];
				System.arraycopy(all, 0, temp, 0, all.length);
				System.arraycopy(read, 0, temp, all.length, num);
				all = temp;
			}
			result = new String(all, "UTF-8");
			if (null != in) {
				in.close();
			}
		}
		get.abort();

		return result;
	}


	public static String sendHttpPost(String url, String body, String Authorization) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", Authorization);
		httpPost.setEntity(new StringEntity(body));

		CloseableHttpResponse response = httpClient.execute(httpPost);
		System.out.println(response.getStatusLine().getStatusCode() + "\n");
		HttpEntity entity = response.getEntity();
		String responseContent = EntityUtils.toString(entity, "UTF-8");
		System.out.println(responseContent);

		response.close();
		httpClient.close();
		return responseContent;
	}


	//public static String sendGet(String url, Map<String, String> params) throws ClientProtocolException, IOException {
	//Set<String> keys = params.keySet();
	//StringBuilder urlBuilder = new StringBuilder(url + "?");
	//for (String key : keys) {
	//urlBuilder.append(key).append("=").append(params.get(key)).append("&");
	//}
	//urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
	//return sendGet(urlBuilder.toString());
	//}


	public static void main(String args[]) {
		try {
			String url = sendGet("http://v.youku.com/v_show/id_XNzIyMDY1Njky.html?from=y1.3-idx-uhome-1519-20887.212790-212949.3-1.1-8-1-3-0");
			System.out.println(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}