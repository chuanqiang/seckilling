//package com.yuxin.wx.reptile;
//
//import java.io.*;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.zip.GZIPInputStream;
//
//import com.yeepay.shade.org.apache.commons.lang3.StringUtils;
//import com.yeepay.shade.org.apache.http.HttpEntity;
//import com.yeepay.shade.org.apache.http.HttpResponse;
//import com.yeepay.shade.org.apache.http.NameValuePair;
//import com.yeepay.shade.org.apache.http.client.HttpClient;
//import com.yeepay.shade.org.apache.http.client.entity.UrlEncodedFormEntity;
//import com.yeepay.shade.org.apache.http.client.methods.HttpGet;
//import com.yeepay.shade.org.apache.http.client.methods.HttpPost;
//import com.yeepay.shade.org.apache.http.entity.BufferedHttpEntity;
//import com.yeepay.shade.org.apache.http.impl.client.DefaultHttpClient;
//import com.yeepay.shade.org.apache.http.message.BasicNameValuePair;
//
//import com.yuxin.wx.api.crm.ICrmAdvertiseService;
//import com.yuxin.wx.common.ViewFiles;
//import com.yuxin.wx.model.crm.Advertise;
//import com.yuxin.wx.utils.DateUtil;
//import com.yuxin.wx.vo.crm.ChanceVo;
//import info.monitorenter.cpdetector.io.*;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping(value = "/ClientUtil")
//public class BaiduProcessor {
//	@Autowired
//	private ICrmAdvertiseService crmAdvertiseServiceImpl;
//
//
//	public static String sendGet(String url) throws org.apache.http.client.ClientProtocolException, IOException{
//		String result = null;
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpGet get = new HttpGet(url);
//		InputStream in = null;
//		try {
//			HttpResponse response = httpClient.execute(get);
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				entity = new BufferedHttpEntity(entity);
//				in = entity.getContent();
//				byte[] read = new byte[1024];
//				byte[] all = new byte[0];
//				int num;
//				while ((num = in.read(read)) > 0) {
//					byte[] temp = new byte[all.length + num];
//					System.arraycopy(all, 0, temp, 0, all.length);
//					System.arraycopy(read, 0, temp, all.length, num);
//					all = temp;
//				}
//				result = new String(all, "UTF-8");
//			}
//		} finally {
//			if (in != null) in.close();
//			get.abort();
//		}
//
//		return result;
//	}
//
//	public static String sendPost(String url, Map<String, String> params) throws ClientProtocolException, IOException{
//		String result = null;
//		HttpClient httpClient = new DefaultHttpClient();
//
//		HttpPost get = new HttpPost(url);
//
//// 创建表单参数列表
//		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
//		Set<String> keys = params.keySet();
//		for (String key : keys) {
//			qparams.add(new BasicNameValuePair(key, params.get(key)));
//		}
//
//// 填充表单
//		get.setEntity(new UrlEncodedFormEntity(qparams,"UTF-8"));
//
//		HttpResponse response = httpClient.execute(get);
//		HttpEntity entity = response.getEntity();
//		if (entity != null) {
//			entity = new BufferedHttpEntity(entity);
//
//			InputStream in = entity.getContent();
//			byte[] read = new byte[1024];
//			byte[] all = new byte[0];
//			int num;
//			while ((num = in.read(read)) > 0) {
//				byte[] temp = new byte[all.length + num];
//				System.arraycopy(all, 0, temp, 0, all.length);
//				System.arraycopy(read, 0, temp, all.length, num);
//				all = temp;
//			}
//			result = new String(all,"UTF-8");
//			if (null != in) {
//				in.close();
//			}
//		}
//		get.abort();
//
//		return result;
//	}
//
////public static String sendGet(String url, Map<String, String> params) throws ClientProtocolException, IOException {
////Set<String> keys = params.keySet();
////StringBuilder urlBuilder = new StringBuilder(url + "?");
////for (String key : keys) {
////urlBuilder.append(key).append("=").append(params.get(key)).append("&");
////}
////urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
////return sendGet(urlBuilder.toString());
////}
//
//	public static String get(String url,boolean zip,String chartset) {
//		BufferedReader in = null;
//		try {
//			URL realUrl = new URL(url);
//			// 打开和URL之间的连接
//			URLConnection connection = realUrl.openConnection();
//			// 设置通用的请求属性
//			connection.setRequestProperty("pragma", "no-cache");
//			//connection.setRequestProperty("Content-Type", "text/html; charset=gbk");
//			connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
//			connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.8");
//			connection.setRequestProperty("upgrade-insecure-requests", "1");
//			connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/5");
//			connection.setRequestProperty("accept", "gzip, deflate, br");
//			connection.setRequestProperty("cache-control", "no-cache");
//			connection.setRequestProperty("connection", "keep-alive");
//			connection.setConnectTimeout(5000);
//			connection.setReadTimeout(5000);
//			// 建立实际的连接
//			connection.connect();
//			// 定义 BufferedReader输入流来读取URL的响应
//			if(zip){
//				GZIPInputStream gzin = new GZIPInputStream(connection.getInputStream());
//				in = new BufferedReader(new InputStreamReader(gzin,chartset));
//			}else{
//				in = new BufferedReader(new InputStreamReader(connection.getInputStream(),chartset));
//			}
//
//
//			StringBuffer sb = new StringBuffer();
//			String line;
//			while ((line = in.readLine()) != null) {
//				sb.append(line);
//			}
//			return sb.toString();
//		} catch (Exception e) {
//		}
//		// 使用finally块来关闭输入流
//		finally {
//			try {
//				if (in != null) {
//					in.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}
//		}
//		return null;
//	}
//
//	/**
//	 *
//	 * @description 获取网页内容编码
//	 * @author zpl
//	 * @date 14:01 2018/8/23 0023
//	 * @param
//	 * @return
//	 */
//	public static String getEncodingByContentStream(String strUrl) {
//		Charset charset = null;
//		try {
//			URLConnection urlConn = new URL(strUrl).openConnection();
//			//打开链接,加上User-Agent,避免被拒绝
//			urlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
//
//			//解析页面内容
//			CodepageDetectorProxy cdp = CodepageDetectorProxy.getInstance();
//			cdp.add(JChardetFacade.getInstance());// 依赖jar包 ：antlr.jar & chardet.jar
//			cdp.add(ASCIIDetector.getInstance());
//			cdp.add(UnicodeDetector.getInstance());
//			cdp.add(new ParsingDetector(false));
//			cdp.add(new ByteOrderMarkDetector());
//
//			InputStream in = urlConn.getInputStream();
//			ByteArrayInputStream bais = new ByteArrayInputStream(IOUtils.toByteArray(in));
//			// detectCodepage(InputStream in, int length) 只支持可以mark的InputStream
//			charset = cdp.detectCodepage(bais, 2147483647);
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return charset == null ? null : charset.name().toLowerCase();
//	}
//
//	/**
//	 * 查询符合的固定电话
//	 * @author:zpl
//	 * @param str
//	 */
//	public static String checkTelephone(String str){
//		// 将给定的正则表达式编译到模式中
//		Pattern pattern = Pattern.compile("(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)");
//		// 创建匹配给定输入与此模式的匹配器。
//		Matcher matcher = pattern.matcher(str);
//		//查找字符串中是否有符合的子字符串
//		while(matcher.find()){
//			//查找到符合的即输出
//			/*System.out.println("查询到一个符合的固定号码："+matcher.group());*/
//			return matcher.group();
//		}
//		return null;
//	}
//
//	/**
//	 * 查询符合的手机号码
//	 * @author:zpl
//	 * @param str
//	 */
//	public static String checkCellphone(String str){
//		// 将给定的正则表达式编译到模式中
//		Pattern pattern = Pattern.compile("((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}");
//		// 创建匹配给定输入与此模式的匹配器。
//		Matcher matcher = pattern.matcher(str);
//		//查找字符串中是否有符合的子字符串
//		while(matcher.find()){
//			//查找到符合的即输出
//			/*System.out.println("查询到一个符合的手机号码："+matcher.group());*/
//			return matcher.group();
//		}
//		return null;
//	}
//
//	/**
//	 *
//	 * @description 过滤html所有标签样式以及js，只获取文本内容
//	 * @author zpl
//	 * @date 16:36 2018/8/30 0030
//	 * @param
//	 * @return
//	 */
//	public static String delHtmlTags(String htmlStr) {
//		//定义script的正则表达式，去除js可以防止注入
//		String scriptRegex = "<script[^>]*?>[\\s\\S]*?<\\/script>";
//		//定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
//		String styleRegex = "<style[^>]*?>[\\s\\S]*?<\\/style>";
//		//定义HTML标签的正则表达式，去除标签，只提取文字内容
//		String htmlRegex = "<[^>]+>";
//		//定义空格,回车,换行符,制表符
//		String spaceRegex = "\\s*|\t|\r|\n";
//
//		// 过滤script标签
//		htmlStr = htmlStr.replaceAll(scriptRegex, "");
//		// 过滤style标签
//		htmlStr = htmlStr.replaceAll(styleRegex, "");
//		// 过滤html标签
//		htmlStr = htmlStr.replaceAll(htmlRegex, "");
//		// 过滤空格等
//		htmlStr = htmlStr.replaceAll(spaceRegex, "");
//		return htmlStr.trim(); // 返回文本字符串
//	}
//
//	/**
//	 *
//	 * @description 可以缓存的线程池，如果线程不超时，会灵活回收空闲的线程执行,如果超时则会创建新的线程执行任务
//	 * @author zpl
//	 * @date 10:52 2018/8/30 0030
//	 * @param
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/pool")
//	public  void  pool1(){
//		//可以缓存的线程池，如果线程不超时，会灵活回收空闲的线程执行
//		//如果超时则会创建新的线程执行任务
//		ExecutorService cached = Executors.newCachedThreadPool();
//		String keyWordArray = "雅思长期培训,雅思怎么培训,雅思在线一对一培训,雅思在线学习网课,雅思在线学习,雅思在线培训学校,雅思在线培训网校,雅思在线培训哪家好,雅思在线培训课程,雅思在线培训班,雅思在线培训,雅思在线课程培训,雅思在哪里培训比较好,雅思在的培训,雅思阅读在线培训哪个好,雅思阅读一对一培训培训点,雅思阅读学习培训,雅思阅读培训资料,雅思阅读培训雅思考试培训中心,雅思阅读培训视频,雅思阅读培训课程,雅思阅读培训价格,雅思阅读培训多少钱,雅思阅读培训点,雅思阅读培训的费用是多少,雅思阅读培训班,雅思阅读培训,雅思阅读考试培训机构,雅思阅读基础培训,雅思有培训班吗,雅思有哪些培训机构,雅思有哪些培训班,雅思英语网上培训,雅思英语培训中心,雅思英语培训怎么样,雅思英语培训在哪,雅思英语培训一般多少钱,雅思英语培训雅思培训班,雅思英语培训学校排行榜,雅思英语培训学校,雅思英语培训宣传语,雅思英语培训网络课程,雅思英语培训培训,雅思英语培训哪里好,雅思英语培训哪家好,雅思英语培训哪个好,雅思英语培训课,雅思英语培训价格,雅思英语培训机构推荐,雅思英语培训机构,雅思英语培训辅导班,雅思英语培训费用,雅思英语培训多少钱,雅思英语培训报名,雅思英语培训班哪里好,雅思英语培训班价格,雅思英语培训班,雅思英语培训,雅思英语考试培训班,雅思英语考试培训,雅思英文培训班,雅思一年培训,雅思一对一雅思培训,雅思一对一培训中心,雅思一对一培训怎么样,雅思一对一培训学校价格,雅思一对一培训学校,雅思一对一培训效果,雅思一对一培训收费,雅思一对一培训哪里好,雅思一对一培训费,雅思一对一培训班,雅思一对一培训,雅思一对一辅导哪里好,雅思一定要培训吗,雅思一般培训多久,雅思要培训多久,雅思雅思一对一培训,雅思雅思学校,雅思雅思培训中心,雅思雅思培训学校,雅思雅思培训哪里好,雅思雅思培训哪家好,雅思雅思培训哪好,雅思雅思培训课程,雅思雅思培训机构,雅思雅思培训寒假班,雅思雅思培训班多少钱,雅思雅思培训班,雅思雅思培训,雅思雅思班,雅思学校培训报名,雅思学校培训,雅思学习网课,雅思学习培训中心,雅思学习培训哪里好,雅思学习培训机构排名,雅思学习培训机构,雅思学习培训班,雅思学习培训,雅思学习班什么好,雅思学为贵网课,雅思学为贵培训,雅思学术培训,雅思学培训,雅思选择什么培训机构,雅思需要上培训班吗,雅思需要培训吗,雅思需要培训班吗,雅思需要培训,雅思需要哪些培训,雅思新通培训,雅思写作培训哪里好,雅思小班培训,雅思线上培训机构,雅思线上培训班,雅思线上培训,雅思网站培训,雅思网站,雅思网校培训,雅思网上学习网课,雅思网上培训哪家好,雅思网上培训机构吗,雅思网上培训班,雅思网上培训,雅思网上辅导,雅思网络培训,雅思网课学习,雅思外语培训学校,雅思外语培训机构,雅思外语培训,雅思听力培训,雅思提高培训,雅思提分培训,雅思暑期培训机构,雅思暑期培训班费用,雅思暑期培训班,雅思暑期培训,雅思暑假培训机构,雅思暑假培训班,雅思暑假培训,雅思暑假班培训,雅思是否需要培训,雅思视频培训,雅思式培训班,雅思什么培训机构好,雅思什么培训班好,雅思什么班好,雅思入门培训机构,雅思如何培训,雅思全培训班,雅思全封闭培训,雅思全程培训,雅思权威一对一培训培训点,雅思秋季培训,雅思强化培训机构,雅思七分培训费用,雅思培训最好机构,雅思培训最好的学校,雅思培训最好的培训机构,雅思培训最好的机构,雅思培训最好的,雅思培训资料下载,雅思培训资料,雅思培训资格,雅思培训专业机构,雅思培训专家,雅思培训主页,雅思培训周末学校,雅思培训周末多少钱,雅思培训周末班,雅思培训周末,雅思培训中心在哪里,雅思培训中心学习,雅思培训中心哪家好,雅思培训中心哪个好,雅思培训中心哪个比较好,雅思培训中心费用,雅思培训知乎,雅思培训证明,雅思培训找哪里,雅思培训长期班,雅思培训怎么样,雅思培训怎么学,雅思培训怎么培训,雅思培训怎么个好,雅思培训在线学习,雅思培训在线课程,雅思培训在线,雅思培训在哪里,雅思培训在哪好,雅思培训在哪,雅思培训阅读班,雅思培训阅读,雅思培训院校,雅思培训有用么,雅思培训有用吗,雅思培训有效果吗,雅思培训有哪些机构,雅思培训有哪些好的,雅思培训有哪些,雅思培训有吗,雅思培训用书,雅思培训英语学校,雅思培训英语,雅思培训一年,雅思培训一个月要多少,雅思培训一个月,雅思培训一对一收费,雅思培训一对一,雅思培训一般是多少天,雅思培训一般价格,雅思培训一般花多少钱,雅思培训一般费用多少,雅思培训一般费用,雅思培训一般多少天,雅思培训一般多少时间,雅思培训一般多少钱,雅思培训一般多少,雅思培训一般多钱,雅思培训一般多久,雅思培训要上多少课时,雅思培训要几年,雅思培训要好多钱,雅思培训要多少钱,雅思培训要多少课时,雅思培训要多久,雅思培训雅思培训,雅思培训雅思哪个好,雅思培训雅思机构学费多少,雅思培训雅思班学费,雅思培训雅思,雅思培训学院,雅思培训学校怎么样,雅思培训学校在哪培训好,雅思培训学校在哪培训,雅思培训学校在哪里,雅思培训学校有哪些,雅思培训学校雅思学习,雅思培训学校学习,雅思培训学校推荐,雅思培训学校十强,雅思培训学校培训哪里好,雅思培训学校培训哪个好,雅思培训学校排名,雅思培训学校那里好,雅思培训学校那个好,雅思培训学校哪些班好,雅思培训学校哪里最好,雅思培训学校哪里好,雅思培训学校哪家好,雅思培训学校哪个最好,雅思培训学校哪个好,雅思培训学校类,雅思培训学校介绍,雅思培训学校价格,雅思培训学校好吗,雅思培训学校多少钱,雅思培训学校报名,雅思培训学校班,雅思培训学校,雅思培训学习费用,雅思培训学习班,雅思培训学什么,雅思培训学费知道,雅思培训学费要多少雅思培训,雅思培训学费要多少,雅思培训学费是什么,雅思培训学费是多少,雅思培训学费多少钱,雅思培训学费多少,雅思培训学费,雅思培训学,雅思培训选哪家,雅思培训需要多少钱,雅思培训需要多久,雅思培训效果如何,雅思培训效果好的机构,雅思培训效果,雅思培训校园,雅思培训校区有用吗,雅思培训校区哪个培训机构好,雅思培训校区,雅思培训小机构,雅思培训相关推荐,雅思培训线上哪家好,雅思培训下载,雅思培训系统,雅思培训问题,雅思培训网站,雅思培训网校,雅思培训网上学习,雅思培训网上机构,雅思培训网上报名,雅思培训网上,雅思培训网络一对一培训哪里好,雅思培训网络培训,雅思培训网络哪里好,雅思培训网络课程,雅思培训网络机构,雅思培训网络,雅思培训网课价格,雅思培训网课费用,雅思培训网,雅思培训推荐,雅思培训题,雅思培训谁最好,雅思培训暑期班,雅思培训暑期,雅思培训暑假班,雅思培训暑假,雅思培训书,雅思培训手续,雅思培训是什么,雅思培训是多少钱,雅思培训视频班哪家好,雅思培训视频,雅思培训时间和价格,雅思培训时间,雅思培训师,雅思培训什么机构好,雅思培训什么机构比较好,雅思培训什么好,雅思培训什么比较好,雅思培训啥价格,雅思培训如何,雅思培训权威机构,雅思培训权威,雅思培训去哪里,雅思培训去哪家,雅思培训去哪好,雅思培训秋季培训班,雅思培训情况,雅思培训强化班,雅思培训前景,雅思培训平台,雅思培训培训学校,雅思培训培训班,雅思培训培训,雅思培训排名,雅思培训排行榜,雅思培训排行,雅思培训内容,雅思培训呢,雅思培训难吗,雅思培训那些比较好,雅思培训那里最好,雅思培训那里专业,雅思培训那里有,雅思培训那里好,雅思培训那里比较好,雅思培训那家最好,雅思培训那家好,雅思培训那家,雅思培训那好,雅思培训那个学校好,雅思培训那个培训最好,雅思培训那个机构好,雅思培训那个号,雅思培训那个好,雅思培训那个比较好,雅思培训哪最好,雅思培训哪些机构好,雅思培训哪些好,雅思培训哪里最好,雅思培训哪里专业,雅思培训哪里有,雅思培训哪里可以,雅思培训哪里机构,雅思培训哪里好一些,雅思培训哪里好啊,雅思培训哪里好,雅思培训哪里的好,雅思培训哪里比较好,雅思培训哪里报名,雅思培训哪里啊个好,雅思培训哪里,雅思培训哪家最好,雅思培训哪家学校好,雅思培训哪家强,雅思培训哪家机构好,雅思培训哪家好,雅思培训哪家,雅思培训哪好,雅思培训哪个最好,雅思培训哪个学校最好,雅思培训哪个学校好,雅思培训哪个可以,雅思培训哪个机构好,雅思培训哪个好啊,雅思培训哪个好,雅思培训哪个比较好啊,雅思培训哪个比较好,雅思培训哪个班好,雅思培训哪儿好,雅思培训哪的好,雅思培训免费,雅思培训流程,雅思培训联系方式,雅思培训类最近,雅思培训类阅读,雅思培训类网站,雅思培训类哪家培训机构好,雅思培训类,雅思培训快速,雅思培训课程体系,雅思培训课程哪家好,雅思培训课程介绍,雅思培训课程报名,雅思培训课程班,雅思培训课程安排,雅思培训课程,雅思培训课,雅思培训考试哪家好,雅思培训考试,雅思培训介绍,雅思培训教育机构,雅思培训教育,雅思培训教学,雅思培训教程,雅思培训价钱,雅思培训价格知乎,雅思培训价格一般是多少,雅思培训价格多少,雅思培训价格2018,雅思培训加盟,雅思培训技巧,雅思培训机构知乎,雅思培训机构长期,雅思培训机构怎么样,雅思培训机构有用吗,雅思培训机构有那些,雅思培训机构有哪些,雅思培训机构学习,雅思培训机构推荐,雅思培训机构什么好,雅思培训机构上哪个好,雅思培训机构如何选择,雅思培训机构前十名,雅思培训机构排名,雅思培训机构排行榜,雅思培训机构排行,雅思培训机构排,雅思培训机构那些好,雅思培训机构那里好,雅思培训机构那家好,雅思培训机构那个好,雅思培训机构哪最好,雅思培训机构哪些个好,雅思培训机构哪些比较好,雅思培训机构哪里有,雅思培训机构哪里好,雅思培训机构哪里比较好,雅思培训机构哪家好,雅思培训机构哪家比较好,雅思培训机构哪好,雅思培训机构哪个最好,雅思培训机构哪个好知乎,雅思培训机构哪个好,雅思培训机构哪个不错,雅思培训机构哪个便宜,雅思培训机构哪个比较好,雅思培训机构哪儿个好,雅思培训机构价位,雅思培训机构价目表,雅思培训机构合作,雅思培训机构好吗,雅思培训机构还是好,雅思培训机构官网,雅思培训机构费用,雅思培训机构对比,雅思培训机构都有哪些,雅思培训机构电话,雅思培训机构的排名,雅思培训机构的价,雅思培训机构大全,雅思培训机构测试咨询,雅思培训机构比较,雅思培训机构办公楼要求,雅思培训机构,雅思培训活动,雅思培训环球雅思,雅思培训花费,雅思培训洪,雅思培训合作,雅思培训好些,雅思培训好吗,雅思培训好贵,雅思培训好的机构,雅思培训好的,雅思培训好不好,雅思培训好,雅思培训寒假班课程,雅思培训寒假班,雅思培训寒假,雅思培训含住,雅思培训贵学,雅思培训贵吗,雅思培训贵不贵,雅思培训广告语,雅思培训官网,雅思培训官方网站,雅思培训附近哪里有,雅思培训辅导班,雅思培训辅导,雅思培训费正常是多少,雅思培训费用知乎,雅思培训费用是多少呢,雅思培训费用是多少,雅思培训费用多少钱,雅思培训费用大概多少,雅思培训费用,雅思培训费多少钱,雅思培训费多少,雅思培训费大概多少钱,雅思培训费,雅思培训方案,雅思培训多长时间,雅思培训多少钱,雅思培训多少费用,雅思培训多少,雅思培训多钱,雅思培训多久,雅思培训短期,雅思培训都哪有,雅思培训电话,雅思培训点多少钱,雅思培训点,雅思培训地址学习,雅思培训地址,雅思培训地点,雅思培训的晒,雅思培训的排名,雅思培训的内容,雅思培训的哪家好,雅思培训的价钱,雅思培训的价格是多少,雅思培训的价格,雅思培训的机构,雅思培训的费用,雅思培训得多钱,雅思培训到哪里好,雅思培训单位,雅思培训大约多少钱,雅思培训大学,雅思培训大概需要多少钱,雅思培训大概费用,雅思培训大概多少钱,雅思培训大概多少,雅思培训大概多久,雅思培训春季培训,雅思培训春季机构,雅思培训冲刺班,雅思培训成本,雅思培训材料,雅思培训比较好是哪里,雅思培训比较好的学校,雅思培训比较好的机构,雅思培训比较好,雅思培训报名网站,雅思培训报名网,雅思培训报名官网,雅思培训报名费多少,雅思培训报名费,雅思培训报名,雅思培训报班的费用,雅思培训包过,雅思培训班最好,雅思培训班周末,雅思培训班知乎,雅思培训班怎么样,雅思培训班在哪里,雅思培训班有用么,雅思培训班有用吗,雅思培训班有什么用,雅思培训班有哪些,雅思培训班英语,雅思培训班一般价格,雅思培训班一般费用,雅思培训班要上多久,雅思培训班要费用,雅思培训班要多久,雅思培训班雅思培训机构,雅思培训班学校,雅思培训班学习,雅思培训班学费多少,雅思培训班学费,雅思培训班选择,雅思培训班推荐,雅思培训班是什么,雅思培训班时间,雅思培训班什么好,雅思培训班培训费用,雅思培训班排名,雅思培训班排行榜,雅思培训班那好,雅思培训班那个好,雅思培训班哪里有,雅思培训班哪里培训好,雅思培训班哪里好,雅思培训班哪里比较好,雅思培训班哪家最好,雅思培训班哪家好,雅思培训班哪好,雅思培训班哪个最好,雅思培训班哪个好,雅思培训班哪的好,雅思培训班哪,雅思培训班零基础,雅思培训班考试培训费用,雅思培训班价位,雅思培训班价格是多少,雅思培训班价格培训学校,雅思培训班价格,雅思培训班机构,雅思培训班合作,雅思培训班好吗,雅思培训班寒假,雅思培训班费用是多少,雅思培训班费用多少钱,雅思培训班费用多少,雅思培训班费用,雅思培训班多长时间,雅思培训班多少费用,雅思培训班多少,雅思培训班多钱,雅思培训班多久,雅思培训班地址,雅思培训班的学费,雅思培训班的价格,雅思培训班的费用,雅思培训班的多少钱,雅思培训班初中,雅思培训班报名,雅思培训班班,雅思培训班g类,雅思培训班5分,雅思培训班,雅思培训吧,雅思培训g类,雅思培训g,雅思培训8分,雅思培训7分,雅思培训6分,雅思培训1对1,雅思培训,雅思那里学,雅思那里培训好,雅思那个培训班好,雅思哪里培训最好,雅思哪里培训好,雅思哪里培训比较好,雅思哪里培训,雅思哪家培训机构好,雅思哪家培训好,雅思哪个培训班好,雅思面授培训机构,雅思面授培训,雅思零基础网络培训,雅思零基础培训学费,雅思零基础培训价格,雅思零基础培训机构,雅思零基础培训班,雅思快速培训,雅思口语培训学校,雅思口语培训哪里好,雅思口语培训哪家好,雅思口语培训机构,雅思口语培训,雅思口语1对1培训,雅思课程一般多长时间,雅思课程培训哪家好,雅思课程培训费,雅思课程培训班,雅思课程培训,雅思考试网络培训,雅思考试培训网站,雅思考试培训时间,雅思考试培训哪家好,雅思考试培训哪个好,雅思考试培训机构,雅思考试培训费用,雅思考试培训多少钱,雅思考试培训班,雅思考试培训,雅思考试机构,雅思考前培训中心,雅思考前培训机构排名,雅思考前培训机构,雅思考前培训班,雅思考前培训,雅思考官培训,雅思开始培训,雅思教育培训中心,雅思教育培训哪里好,雅思教育培训机构,雅思教育培训班,雅思教育培训,雅思教师培训,雅思假期培训,雅思集中培训,雅思基础阅读培训,雅思基础培训中心,雅思基础培训学校,雅思基础培训,雅思机构培训费用,雅思机构培训,雅思好培训,雅思好的培训机构,雅思寒假培训,雅思寒假班班,雅思寒假班,雅思贵学培训,雅思官网培训机构,雅思官方培训,雅思哥培训班,雅思高分培训中心,雅思辅导培训班,雅思辅导培训,雅思福培训行业,雅思费用培训,雅思多少分可以做培训,雅思短期培训中心,雅思短期培训机构,雅思等级培训,雅思的学习班,雅思的网站,雅思的培训中心,雅思的培训机构排名,雅思的培训机构,雅思的培训费用多少,雅思的培训多少钱,雅思的培训班,雅思的培训,雅思单科培训,雅思初中培训学习,雅思初中培训班,雅思初级培训班,雅思初级培训,雅思出国培训机构,雅思冲刺培训怎么做,雅思冲刺培训,雅思博培训,雅思必须培训吗,雅思比较好的培训机构,雅思报培训班,雅思报名通过培训学校,雅思报名培训班,雅思报名培训,雅思保分培训,雅思班怎么样,雅思班有用吗,雅思班英语,雅思班学习,雅思班学什么,雅思班型,雅思班谁好,雅思班是什么,雅思班什么培训机构好,雅思班什么好,雅思班全封闭,雅思班培训课程,雅思班培训价格,雅思班培训费用,雅思班培训多少钱,雅思班培训班,雅思班培训,雅思班那好,雅思班那个好,雅思班哪里好,雅思班哪好,雅思班哪的好,雅思班老师,雅思班口语,雅思班课程,雅思班考试,雅思班好不好,雅思班寒假,雅思班贵吗,雅思班辅导,雅思班报名,雅思班班,雅思班8分,雅思班7分,雅思班5分,雅思班,雅思vip培训,雅思g培训,雅思g类培训课程,雅思g类培训机构,雅思g类培训,雅思g类课程培训,雅思g类出国培训,雅思8分培训机构,雅思8分培训班哪里好,雅思7分雅思班,雅思7分培训学校,雅思7分口语班,雅思6分培训机构,雅思5分培训价格,雅思5分培训机构,雅思5分培训班,雅思2018培训,雅思2017寒假班,雅思1对1培训哪里,雅思1对1培训课程,雅思1对1培训机构,雅思1对1培训班,雅思1对1培训,雅思培训,雅思,学雅思阅读培训学校,学雅思英语培训,学雅思雅思,学雅思需要报培训机构吗,学雅思网课,学雅思培训哪个好,学雅思培训机构,学雅思培训费用,学雅思培训班哪个比较好呢,学雅思培训班,学雅思培训,学雅思哪里培训好,学雅思哪家培训机构好,学雅思哪个培训好,学雅思的网课,学雅思的培训哪家好,学雅思的培训机构,学习雅思培训中心,学习雅思培训需要多少钱,学习雅思培训费,学习雅思培训,学习雅思的网课,学习雅思的培训中心,学为贵雅思网课,学为贵雅思入门班,学为贵雅思培训怎么样,学为贵雅思培训机构,学为贵雅思培训,学为贵的雅思培训怎么样,学为贵的雅思培训,学术雅思和培训,学术类雅思和培训类雅思,性价比高的雅思培训一般多少钱,新雅思阅读培训机构,新雅思阅读培训班,新雅思阅读培训,新雅思培训怎样,新雅思培训怎么样,新雅思培训学校,新雅思培训视频,新雅思培训时间,新雅思培训课程,新雅思培训机构哪家好,新雅思培训机构,新雅思培训的价格是多少,新雅思培训班,新通雅思培训,新东方雅思英语培训,新东方雅思雅思培训班,新东方雅思学习,新东方雅思培训中心,新东方雅思培训怎么样,新东方雅思培训学校,新东方雅思培训网络课程,新东方雅思培训晚班,新东方雅思培训老师,新东方雅思培训考试,新东方雅思培训价目表,新东方雅思培训机构,新东方雅思培训好吗,新东方雅思培训寒假班,新东方雅思培训报名,新东方雅思培训,新东方雅思考试培训,新东方雅思环球雅思,新东方雅思寒假班,新东方雅思班培训,新东方雅思7分,新东方雅思5分,小雅思培训班,小雅思培训,小学雅思一对一培训,小班雅思培训,线上雅思培训机构,线上雅思培训,无忧雅思培训,网站雅思学习,网校雅思培训,网上雅思学校,网上雅思学习,网上雅思培训学校哪里好,网上雅思培训学校哪家好,网上雅思培训课程,网上雅思培训机构哪里好,网上雅思培训机构哪个好,网上雅思培训机构,网上雅思培训班,网上雅思课程,网上学雅思培训,网上培训雅思,网上报名雅思培训,网络雅思学习平台,网络雅思学习课程,网络雅思培训课程,网络雅思培训,网课学雅思怎么样,网课学雅思,托福雅思课程,泰国雅思培训机构,泰国雅思培训,暑期雅思培训中心,暑期雅思培训哪个好,暑期雅思培训机构,暑期雅思培训班,暑期雅思培训,暑期雅思班,暑假雅思培训班,暑假雅思班培训,暑假培训雅思,暑假班雅思,暑假班学雅思,市雅思培训周末班,市雅思培训哪里好,市雅思培训费用,市雅思培训,世纪雅思培训,实用雅思英语培训班,实用雅思培训,石家庄雅思g类培训,十一雅思培训,十大雅思培训机构,十大雅思培训,圣三一雅思培训,省有雅思培训地方吗,省有雅思培训班吗,什么雅思培训最好,什么雅思培训中心好,什么雅思培训学院好,什么雅思培训学校好,什么雅思培训较好,什么雅思培训好,什么雅思培训比较好,什么雅思培训班好,什么雅思培训班比较好,什么雅思班培训机构,什么叫雅思培训,深圳学雅思培训,上雅思培训班有用吗,上雅思培训班多少钱,上海雅思学习培训机构,上海雅思培训学习,上海雅思考前培训,上海新东方雅思培训费用,上大雅思培训,三立雅思培训机构,如何选择雅思培训班,如何选择雅思培训,如何选雅思培训机构,如何培训雅思,如何考雅思培训学校,如何成为雅思培训师,全天制雅思培训,全日制雅思培训寒假班,全球雅思培训机构,权威雅思培训,权威的雅思培训机构,去哪培训雅思,区雅思阅读培训,区雅思雅思培训班,区雅思培训周末班,秋季雅思培训班,秋季雅思培训,请问雅思培训机构哪个好一点,青少年雅思培训,齐市有没有雅思培训,培训中心雅思,培训英语雅思,培训一对一雅思,培训雅思作文,培训雅思中心,培训雅思在哪里,培训雅思阅读哪好,培训雅思阅读,培训雅思英语,培训雅思学习,培训雅思去那好,培训雅思培训机构,培训雅思培训费用,培训雅思那好,培训雅思哪里有,培训雅思哪里好,培训雅思哪里比较好,培训雅思哪家好,培训雅思哪个好,培训雅思口语班,培训雅思口语,培训雅思课程,培训雅思考试,培训雅思价格,培训雅思计划,培训雅思机构排名,培训雅思机构哪家好,培训雅思机构,培训雅思官方,培训雅思费用,培训雅思多少钱雅思培训费用是多少呢,培训雅思的学校,培训雅思的价格,培训雅思的费用,培训雅思班,培训新雅思阅读,培训新雅思,培训前培训雅思,培训留学雅思,培训类雅思阅读,培训类雅思,培训类,培训机构雅思学习,培训机构雅思零基础,培训机构雅思,培训机构学雅思,培训费用雅思,培训报名雅思,培训班雅思学习,培训g类雅思,牛学雅思培训好不好啊,年度最佳雅思培训机构,那有雅思培训,那有培训雅思,那里有雅思培训,那里雅思英语培训,那里雅思培训好,那个雅思培训机构好,那个雅思培训好,那个雅思培训班好,哪有专业雅思培训机构,哪有雅思培训雅思机构,哪有雅思培训班,哪有培训雅思,哪有好的雅思培训机构,哪雅思培训好,哪些雅思培训机构好,哪些雅思培训机构比较好,哪些机构雅思培训好,哪些地方培训雅思,哪所雅思培训学校好,哪培训雅思最好,哪培训雅思好,哪里找雅思培训,哪里有专业的雅思培训,哪里有雅思培训雅思班,哪里有雅思培训机构,哪里有雅思培训的,哪里有雅思培训班,哪里有雅思培训,哪里有培训雅思的,哪里有培训雅思,哪里有g类雅思培训,哪里雅思培训机构最好,哪里雅思培训机构好,哪里雅思培训好,哪里雅思培训班好,哪里雅思培训,哪里学习雅思培训机构,哪里培训雅思好,哪里培训雅思,哪里培训机构学习雅思,哪里可以培训雅思,哪里可以进行雅思培训,哪里好的雅思培训机构,哪里的雅思培训最好,哪里的雅思培训学校好,哪里的雅思培训机构好,哪里的雅思培训好,哪里的雅思培训班好,哪里的雅思培训,哪家雅思在线培训好,哪家雅思培训最好,哪家雅思培训中心好,哪家雅思培训雅思好,哪家雅思培训学校好,哪家雅思培训机构好,哪家雅思培训好,哪家雅思培训比较好,哪家雅思培训班好,哪家机构培训雅思最好,哪个雅思培训最好,哪个雅思培训学校最好,哪个雅思培训学校好,哪个雅思培训机构好,哪个雅思培训机构,哪个雅思培训好,哪个雅思培训比较好,哪个雅思培训班好,哪个雅思培训班,哪个雅思培训,哪个学校雅思培训好,哪个培训学校雅思好,哪个培训机构雅思好,哪个培训机构的雅思好,哪个机构培训雅思好,哪个比较好呢,哪的雅思培训机构好,免费雅思培训,美世雅思培训,美连雅思培训,留学培训雅思,零基础雅思培训中心,零基础雅思培训雅思考试培训中心,零基础雅思培训学校,零基础雅思培训哪家好,零基础雅思培训机构,零基础雅思培训班,零基础网络培训雅思,立刻说雅思培训机构,立刻说雅思培训班,口语培训雅思,考雅思需要上培训班吗,考雅思什么培训机构好,考雅思什么培训班好,考雅思培训中心,考雅思培训机构,考雅思培训费用多少,考雅思培训费用,考雅思培训班多少钱,考雅思培训班,考雅思培训,考雅思哪些培训机构好,考雅思哪个培训机构好,考雅思哪个培训班好,考雅思的培训机构,考雅思的培训班,考试雅思培训,考前雅思培训,考前雅思班,开设雅思培训班通知,开发区雅思培训机构,开发区雅思培训班,旧雅思培训,金融雅思培训班,较好的雅思培训机构,剑桥雅思班,及岸雅思培训,基础雅思培训,机构雅思培训,环球雅思怎么报名,环球雅思在线测试,环球雅思阅读7分,环球雅思有多少家,环球雅思英语培训机构,环球雅思雅思培训多少钱,环球雅思雅思培训班,环球雅思雅思培训,环球雅思学校官网,环球雅思外教口语班,环球雅思什么班好,环球雅思培训怎么样,环球雅思培训雅思怎么样,环球雅思培训雅思,环球雅思培训学习,环球雅思培训时间,环球雅思培训,环球雅思哪个班好,环球雅思连锁学校,环球雅思课程,环球雅思教的怎么样,环球雅思和新东方哪个好,环球雅思和新东方,环球雅思辅导班怎么样,环球雅思的老师怎么样,环球雅思班怎么样,环球雅思班报名,环球雅思7分班怎么样,呼市雅思培训班,合作雅思培训机构,合作雅思培训,好的雅思英语培训学校,好的雅思英语培训班,好的雅思英语培训,好的雅思培训学校,好的雅思培训哪里有,好的雅思培训哪里好,好的雅思培训课程,好的雅思培训机构,好的雅思培训点,好的雅思培训班,好的雅思培训,好的培训雅思,寒假雅思学校,寒假雅思培训班,寒假雅思培训,寒假雅思课程,哈市雅思培训,国外雅思培训,国内雅思培训价格,国内雅思培训,贵学雅思培训,贵学雅思班怎么样,广州雅思考前培训,广州新东方雅思培训价格,关于雅思培训,公共雅思培训班,高中雅思培训班,高中雅思培训,高三雅思培训,高二雅思培训,高端雅思培训,附近最好的雅思培训,附近有雅思培训么,附近有雅思培训吗,附近的雅思培训,佛山雅思培训学习,短期雅思培训费用,短期雅思培训,都有什么雅思培训机构,都有哪些雅思培训机构,冬季雅思培训班,东校区考雅思培训,电话雅思培训,地雅思培训,低基础雅思培训,的雅思学校,的雅思培训机构有哪些,的雅思培训班,的雅思培训,到底谁家雅思培训好,大学雅思培训机构,大学雅思培训班,大四雅思培训,大二雅思培训,春季雅思学校,春季雅思培训价格,春季雅思培训,初中雅思培训班,出过雅思培训,成人雅思培训,便宜雅思培训,便宜的雅思培训机构,便宜的雅思培训,比较好的雅思培训学校,比较好的雅思培训,北京雅思学习培训,北京雅思培训学习,北京雅思考前培训,北京学习雅思培训,报雅思培训多少钱,报雅思培训班哪个好,报雅思培训班,报雅思培训,报名雅思培训,报名雅思班,报考雅思班,百格雅思培训学校,ielts阅读培训班,ielts写作培训班,ielts听力培训,ielts培训机构哪家好,g专业雅思培训,g类雅思培训,6分雅思班,6分g类雅思班,5分雅思培训价格,2018雅思培训费用,2018新雅思培训费用,2018年雅思培训班,2018年雅思培训,1对1雅思培训,1对1雅思辅导,1对1雅思班,0基础雅思培训机构,做留学,作品集留学,作品集,最专业的留学,最值得留学的国家,最新留学,最新德国留学,最适宜留学的城市,最适合留学的国家,最容易留学的专业,最留学,最大留学,最大的留学中介,最便宜留学国家,最便宜的美国留学,最便宜的留学国家,最便宜的留学,最便宜的国外留学,自己怎样去日本留学,自己申请留学步骤,自己申请德国留学,自费美国留学条件,自费留学多少钱,自费赴美留学费用,自动控制专业留学,自动化日本留学,咨询去美国留学价格,准备留学美本,专业留学排行榜,筑人留学地址,重新留学,重庆留学服务网,重大国际预科留学中心,重大国际留学怎么样,中学生如何去美国留学,中学生赴日留学,中留学,中科留学怎么样,中介费美国留学,中国中学生留学德国,中国留学监督网,中国留学第一,中国韩国留学班,中国官方留学,中国赴美留学,中国到德国留学,中国出国留学网,中出外国留学生,直接留学,知艺艺术留学,知一留学,正规留学,这样留学,这么留学,找中介德国留学,长期出国,长春赴日留学,怎样在英国留学,怎样申请去瑞典留学,怎样申请出国读研,怎样申请出国大一留学,怎样去留学,怎样能去德国留学,怎样能留学,怎样留学最便宜,怎样留学去加拿大,怎样留学海外,怎样留学,怎样可以留学,怎样德国留学,怎样到荷兰留学,怎样出国读研,怎样出国,怎样才能上留学,怎样才能去国外留学,怎样办韩国留学,怎么做留学顾问,怎么样去瑞典留学,怎么样去美国留学,怎么样去留学法国,怎么样去留学,怎么样去丹麦留学,怎么样能留学,怎么样留学丹麦,怎么样留学北美,怎么样留学,怎么样可以留学,怎么样可以出国留学,怎么样从留学,怎么样出国,怎么样才能德国留学,怎么申请去瑞典留学,怎么申请出国读研,怎么申请出国大一留学,怎么去申请美国留学,怎么去留学,怎么去荷兰留学,怎么去丹麦留学,怎么能留学,怎么拿offer留学,怎么留学,怎么可以留学,怎么获得留学奖学金,怎么到外国留学,怎么到国外留学,怎么带薪留学,怎么出国读研,怎么出国,怎么办留学,怎么办出国,在中国留学的留学生,在英留学生,在英国留学一年要多,在英国留学感受,在英国的留学生,在外留学,在日留学,在欧洲留学怎么样,在那留学,在那好留学,在那个国家留学最便宜,在哪留学最好,在哪里留学好,在哪个国家留学好,在哪个国家留学便宜,在美留学生论坛,在美国一年留学费用,在美国留学一年费用,在美国留学一年的费用,在美国留学多少钱,在美国留学的条件,在美国留学的经历,在美国留下来留学生,在美国的中国留学,在美国的留学生活,在美国的留学生,在留学一年费用,在留学,在加拿大留学好吗,在韩留学生,在韩国留学好不好,在韩国留学多少钱,在韩国留学的条件,在韩国的留学生,在国内读研后出国留学,在德国留学一年费用大概多少,在德国留学一年的费用,在德国留学是什么体验,在德国留学的费用,在大学留学去德国,在北美留学,再来人留学好不好,再来人留学官网,再来人留学地址,运动人体科学留学,运动科学专业留学,预科怎么报留学,预科生是什么意思留学,预科留学是什么,预科留学好不好,预科留学好毕业吗,有什么专业艺术留学,有什么留学机构,有什么好的留学,有那些好的留学机构,有哪些留学机构,有名的留学机构,有没有免费留学,有没有必要出国留学,有没韩国留学,有留学机会的大学,有留学的大学,有奖学金的留学,有的是去留学,有德国的留学项目吗,有必要找留学中介吗,有必要留学吗,游学费用出国,优越留学怎么样,优越留学好不好,优秀留学推荐信,优楷士留学怎么样,应该留学,影视制作留学排名,影视留学哪里好,影视留学哪个国家好,影视留学多少钱,影视出国读研,英语出国读研,英语不好怎么留学,英语不好如何留学,英国转专业留学,英国一年留学费用多少,英国一年留学费用,英国一年留学多少钱,英国一年留学的生活费大概是多少,英国一年的留学费用,英国四年留学费用,英国适合留学的大学,英国全额奖学金留学,英国去留学的条件,英国女留学生,英国能留学吗,英国美国留学那个好,英国美国留学好吗,英国曼大留学,英国留学最好专业,英国留学转专业,英国留学专业大全,英国留学注意事项,英国留学与美国留学,英国留学英国留学,英国留学需要什么条件,英国留学生留学费用,英国留学留学,英国留学和美国留学,英国留学还是美国留学,英国留学读研申请条件,英国出国读研条件,英国出国读研费用,英国出国读研,一站式留学,一为留学,一人一梦想留学,一起去留学,一起留学网,一起留学吧的小站,一起留学吧,一起留学,一诺留学怎么样,一年留学需要多少钱,一名留学,一加三留学,一个人去澳洲留学,一个人留学,一个人出国留学,一个留学生告诉你,一本留学,一般艺术留学多少钱,一般留学预科费用多少,一般留学留几年,要日本留学怎么办,要去英国留学,要去艺术留学,要去韩国留学,要不要去德国留学,要不要留学,央美留学班,央美毕业留学,燕兴留学怎么样,研一留学,研究所留学,研究生专业留学申请,研究生怎么英国留学,研究生怎么留学日本,研究生怎么留学,研究生英国留学,研究生想去留学,研究生想留学条件,研究生外国留学,研究生瑞典留学条件,研究生如何英国留学,研究生如何留学,研究生去英国留学,研究生能德国留学吗,研究生丹麦留学条件,研究生成绩留学,研究生毕业留学,研究留学几年,研究留学,亚洲高端留学,学校国际留学班,性价比高的留学中介,新通留学地址,新通国际留学怎么样,新通国际留学,新留学,新航道留学怎么样,新东留学,新出留学,小留学,想去留学,想留学服务机构,想留学,现在留学怎么样,现在留学,现在出国,西安高端留学,我要去留学,我要留学,我想去美国留学,我想留学,我想澳洲留学,我们留学,我留学,我的留学,蔚蓝留学好不好,为什么要去留学,为什么要留学,为什么选择去留学,为什么去留学,为什么留学,为什么出国读研,为什么出国,为啥留学,为了出国,为何留学,为何出国,外大留学,通过留学机构留学,天津高端留学,天道留学好不好,天艾留学,藤门高端留学,太傻留学咨询网,塔夫斯大学留学条件,是否留学,十强留学中介,十大留学中介排名,十大留学中介机构排名,什么样的适合留学,什么是留学,什么时候留学,什么留学,申请出国读研中介费,申请出国读研的条件,上新留学,上大留学项目,上大留学,三本学校出国读研,三本学生出国读研,睿艺留学怎么样,如何做留学,如何自己留学,如何在留学,如何申请出国读研,如何去留学,如何去国外留学,如何免费留学,如何留学,如何到国外留学,如何出国读研,如何出国,认为是留学,认为留学,人员出国,人民大学留学中心,人留学,全奖出国留学,全国十大留学品牌,全国十大留学机构,全国前十留学机构,去外国留学多少钱,去哪留学最便宜,去哪留学,去哪里留学便宜,去哪里留学,去哪个国家留学最便宜,去哪个国家留学比较好,去美国留学网,去留学需要什么,去留学价格,去留学的要求,去留学,去国外留学需要什么条件,去国外留学的条件,去的留学,去澳洲留学条件,侨外留学网,前十留学机构,前留学,起的留学,凭原留学,欧美国家留学条件,女生出国读研,你的留学,那里留学好,那里留学,那个留学机构比较好,那次留学,哪留学,哪里留学好,哪里留学,哪家留学机构最好,哪家留学,哪国留学便宜,哪个留学中介比较好,哪个留学机构好,哪个国家留学好,免费留学,免费的留学,美国中国留学生网,美国研究生留学,美国留学与英国留学";
//		String[] keyWordArray1 = keyWordArray.split(",");
//		for (String keyWord : keyWordArray1) {
//			String url = ClientUtil.get("https://www.baidu.com/s?ie=utf-8&wd=" + keyWord, true, "utf-8");
//			Document doc = Jsoup.parse(url);
//			Element rows = doc.select("div[id=content_left]").get(0);
//
//			int i = 0;
//			for (Element row : rows.select("div[id~=\\d]")) {
//				final Advertise advertise =new Advertise();
//				Element a = row.select("a").get(0);
//				if (StringUtils.isBlank(a.text())) {
//					continue;
//				}
//				i += 1;//第几个
//				System.out.println("第" + i + "个");//标题
//				advertise.setKeyWord(keyWord);
//				if (row.select(".ec_tuiguang_ppimlink").size() > 0 || row.select(".ec_tuiguang_ppouter").size() > 0) {
//					System.out.print("推广  ");//是否推广
//					advertise.setAdvertiseType("推广");
//				} else {
//					advertise.setAdvertiseType("非推广");
//				}
//				System.out.println(" 百度的url:" + a.attr("href"));//百度的url
//				advertise.setBaiduUrl(a.attr("href"));
//				System.out.println("标题:" + a.text());//标题
//				advertise.setAdvertiseTitle(a.text());
//
//				String regex1 = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+[.][a-z]+";
//				String regex = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+";
//				Pattern pattern = Pattern.compile(regex1);
//				Matcher m = pattern.matcher(row.text());
//				String companyUrl = null;
//				if (m.find()) {
//					companyUrl=m.group();
//					System.out.println("url:" + companyUrl);//本身的网址
//				} else {
//					Pattern pattern2 = Pattern.compile(regex);
//					Matcher m2 = pattern2.matcher(row.text());
//					if (m2.find()) {
//						companyUrl=m2.group();
//						System.out.println("url:" + companyUrl);//本身的网址
//					}
//				}
//				advertise.setCompanyUrl(companyUrl);
//				//判断是否是百度网页，如若为baidu.com则不进行抓取以及存储
//				if(companyUrl!=null && companyUrl.contains("baidu.com")){
//					continue;
//				}
//				//查询页面编码格式，方式抓取网页信息时乱码
//				String charset = ClientUtil.getEncodingByContentStream(a.attr("href"));
//				System.out.println("编码====================================:" + charset);
//				//进一步解析网址中的内容，获取所需的电话或者公司名称
//				final  String url1 = StringUtils.deleteWhitespace(ClientUtil.get(a.attr("href"), true, charset));
//				System.out.println("HTML页面====================================:" + url1);
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				cached.execute(new  Runnable() {
//					public void run() {
//						if(url1!=null){
//							//1、找有限公司，取最后一个
//							//找联系我们 或者 关于我们 或者
//							Document docWeb = Jsoup.parse(url1);
//							//第一层查找公司名称
//							Elements elements = docWeb.getElementsContainingText("有限公司");
//							String companyName = "";
//							String mobilePhone ="";
//							String phone ="";
//							if(elements.size()>0){
//								companyName = elements.get(elements.size()-1).text();
//								advertise.setCompanyName(companyName);
//							}
//							String txtcontent = delHtmlTags(url1);
//							//查找座机号
//							phone = ClientUtil.checkTelephone(txtcontent);
//							if(phone!=null&&phone!=""){
//								advertise.setTelePhone(phone);
//							}
//							//查找手机号
//							mobilePhone = ClientUtil.checkCellphone(txtcontent);
//							if(mobilePhone!=null&&mobilePhone!=""){
//								advertise.setCellPhone(mobilePhone);
//							}
//							System.out.println("第一层页面最后一个公司名称:"+companyName);
//							System.out.println("第一层页面手机号:"+mobilePhone);
//							System.out.println("第一层页面座机号:"+phone);
//							if(companyName==""||mobilePhone==null||phone==null){
//								//接着进行深层次抓取，通过联系我们或关于我们来查找公司名称和手机号
//								elements = docWeb.getElementsContainingText("联系我们");
//								String companyName2 = "";
//								if(elements.size()>0){
//									System.out.println("进入第二层=====================寻找联系我们");
//									companyName2 = elements.get(elements.size()-1).text();
//									String regexPhone = "(.*)(<a)(.*)(联系我们)(.*)";
//									Pattern patternPhone = Pattern.compile(regexPhone);
//									Matcher matcherPhone = patternPhone.matcher(url1);
//									boolean b = matcherPhone.matches();
//									if(b){
//										/*System.out.println("联系我们页面中的==== aaaaaa:"+matcherPhone.group(3));*/
//										regexPhone = "(.*)(href=\")(.*)(shtml)(\")(.*)";
//										patternPhone = Pattern.compile(regexPhone);
//										matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//										if(matcherPhone.matches()){
//											String aTab = matcherPhone.group(3)+"shtml";
//											System.out.println("联系我们页面的a链接:"+aTab);
//											//获取联系我们的a链接后
//											//查询页面编码格式，方式抓取网页信息时乱码
//											String charsetDouble =ClientUtil.getEncodingByContentStream(aTab);
//											System.out.println("通过联系我们找到的第二层页面的编码====================================:"+charsetDouble);
//											String aLink= ClientUtil.get(aTab, true, charsetDouble);
//
//											//如果第一层页面找到了公司名称，对于深层页面就无需再找
//											if(companyName==null || companyName==""){
//												Document docLink = Jsoup.parse(aLink);
//												elements = docLink.getElementsContainingText("有限公司");
//												if(elements.size()>0){
//													companyName = elements.get(elements.size()-1).text();
//													advertise.setCompanyName(companyName);
//												}
//												System.out.println("第二层页面======联系我们====获取的公司名称:"+companyName);
//											}
//											System.out.println("======================联系我们=====第二层页面============================:" + aLink);
//											//剔除所有标签，防止录入非手机号的数据
//											String txtcontentaLink = delHtmlTags(aLink);
//											//判断是否已找到手机号
//											if(mobilePhone==null||mobilePhone==""){
//												//手机号
//												String cellPhone = ClientUtil.checkCellphone(txtcontentaLink);
//												if(cellPhone!=null){
//													advertise.setCellPhone(cellPhone);
//												}
//												System.out.println("第二层页面======联系我们====手机号:"+cellPhone);
//
//											}
//											//判断是否已找到座机号
//											if(phone==null||phone==""){
//												//固定电话
//												String telePhone = ClientUtil.checkTelephone(txtcontentaLink);
//												if(telePhone!=null){
//													advertise.setTelePhone(telePhone);
//												}
//												System.out.println("第二层页面======联系我们====固定电话:"+telePhone);
//											}
//											System.out.println("------------------第2222222222222222层结束--------------------------");
//										}
//									}
//								}
//							}
//
//							/*如果没有找到公司名称并且联系我们也没有，就查找关于我们*/
//							if(companyName==""||mobilePhone==null||phone==null){
//								Elements elementsAbout = docWeb.getElementsContainingText("关于我们");
//								String companyNameAbout = "";
//								if(elementsAbout.size()>0){
//									System.out.println("进入第二层=====================寻找关于我们");
//									companyNameAbout = elementsAbout.get(elementsAbout.size()-1).text();
//									String regexPhone = "(.*)(<a)(.*)(关于我们)(.*)";
//									Pattern patternPhone = Pattern.compile(regexPhone);
//									Matcher matcherPhone = patternPhone.matcher(url1);
//									boolean b = matcherPhone.matches();
//									if(b){
//										/*System.out.println("关于我们的==== aaaaaaa:"+matcherPhone.group(3));*/
//										/*patternPhone = Pattern.compile("(.*)(href=\")(.*)(\"target)(.*)");*/
//										patternPhone = Pattern.compile("(.*)(href=\")(.*)(/\")(.*)");
//										matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//										b = matcherPhone.matches();
//										if(b){
//											System.out.println("关于我们页面的a链接:"+matcherPhone.group(3));
//											String charsetDouble = ClientUtil.getEncodingByContentStream(matcherPhone.group(3));
//											System.out.println("通过关于我们找到的第二层页面的编码====================================:"+charsetDouble);
//											try{
//												String aLink= ClientUtil.get(matcherPhone.group(3), true, charsetDouble);
//												Document docLink = Jsoup.parse(aLink);
//
//												System.out.println("======================关于我们=====第二层页面============================:" + aLink);
//												//如果第一层页面找到了公司名称，对于深层页面就无需再找
//												if(companyName==null || companyName==""){
//													elements = docLink.getElementsContainingText("有限公司");
//													if(elements.size()>0){
//														companyName = elements.get(elements.size()-1).text();
//														advertise.setCompanyName(companyName);
//													}
//													System.out.println("第二层页面======关于我们====获取的公司名称:"+companyName);
//												}
//												//剔除所有标签，防止录入非手机号的数据
//												String txtcontentaLink = delHtmlTags(aLink);
//												//判断是否已找到手机号
//												if(mobilePhone==null||mobilePhone==""){
//													//手机号
//													String cellPhone = ClientUtil.checkCellphone(txtcontentaLink);
//													if(cellPhone!=null){
//														advertise.setCellPhone(cellPhone);
//													}
//													System.out.println("第二层页面======关于我们====手机号:"+cellPhone);
//
//												}
//												//判断是否已找到座机号
//												if(phone==null||phone==""){
//													//固定电话
//													String telePhone = ClientUtil.checkTelephone(txtcontentaLink);
//													if(telePhone!=null){
//														advertise.setTelePhone(telePhone);
//													}
//													System.out.println("第二层页面======关于我们====固定电话:"+telePhone);
//												}
//												System.out.println("------------------第2222222222222222层结束--------------------------");
//											}catch (Exception e){
//												e.printStackTrace();
//											}
//
//										}
//									}
//								}
//							}
//							try{
//								//如若存在已存储，并且为非推广，则不进行下面的操作了
//
//								Integer count = crmAdvertiseServiceImpl.findByWhere(advertise);
//								if(count>0){
//
//								}else{
//									crmAdvertiseServiceImpl.insert(advertise);
//								}
//
//
//							}catch (Exception e){
//								e.printStackTrace();
//							}
//						}
//					}
//				});
//			}
//		}
//	}
//
//
//	@ResponseBody
//	@RequestMapping(value = "/insertAdvertise")
//	public String insertAdvertise(){
//		try {
//			String keyWordArray="pmp认证项目经理,pmp认证深圳培训,pmp认证培训那个好,pmp认证培训哪家好,pmp认证培训哪个好,pmp认证培训机构,pmp认证培训费用,pmp认证培训,pmp认证考前培训,pmp培训主要内容,pmp培训在线学习,pmp培训有哪些机构,pmp培训学院,pmp培训学校哪个好,pmp培训学费,pmp培训网络课程,pmp培训网络班,pmp培训推荐,pmp培训团购,pmp培训苏州哪家好,pmp培训苏州,pmp培训深圳,pmp培训上海哪家好,pmp培训厦门,pmp培训哪里好,pmp培训哪里比较好,pmp培训哪家最好,pmp培训哪家机构比较好,pmp培训哪家好,pmp培训哪家比较好上海,pmp培训哪家比较好,pmp培训哪个机构好,pmp培训哪个好,pmp培训哪个比较好,pmp培训吗费用,pmp培训课程费用,pmp培训济南,pmp培训机构有哪些,pmp培训机构选择,pmp培训机构武汉,pmp培训机构推荐,pmp培训机构通过率,pmp培训机构苏州,pmp培训机构排名北京,pmp培训机构排名,pmp培训机构哪家强,pmp培训机构哪家比较好,pmp培训机构哪个最好,pmp培训机构哪个好,pmp培训机构济南,pmp培训机构杭州,pmp培训机构比较,pmp培训慧翔,pmp培训广州,pmp培训费用国际,pmp培训费,pmp培训点,pmp培训的目的,pmp培训的费用,pmp哪个培训机构好,pmp哪个培训好,pmp哪个机构好,pmp免试,pmp免费公开课,pmp免费,pmp没有项目管理经验,pmp论坛,pmp流程,pmp兰州培训机构,pmp课程介绍,pmp考试项目,pmp考前培训机构,pmp机构,pmp和项目管理,pmp和系统集成项目管理师,pmp和高级项目管理师,pmp杭州培训机构,pmp国际项目管理师,pmp国际认证培训,pmp管理质量,pmp管理实践,pmp管理认证,pmp管理圈,pmp管理培训,pmp官方指定培训机构,pmp官方网站,pmp官方培训机构,pmp官方教材,pmp工作经验要求,pmp工程管理,pmp的通过率,pmp的培训机构,pmp的培训,pmp不培训,pmp北京培训机构哪个好,pmp报名截止,pmp报名,pmp报考资质,pmp报考网站,pmp报考费,pmp报过,pmp保过,pmp9月报名,pmp,pmi项目管理培训,it项目管理pmp,2018项目经理pmp考试,2018项目管理pmp,2018年pmp项目管理师,2017pmp,2016年pmp培训,中职教师资格证考试条件,中小学教师资格证报名条件,中小学教师资格证报名入口,中小学教师资格证报考条件,中文教师资格证,怎样能考教师资格证,怎样考取教师资格证,怎样考教师资格证,怎样报考教师资格证,怎么考教师资格证,小学教师资格证如何报名,现在教师资格证怎么考,下半年教师资格证考试时间,体育教师资格证报名条件,如何考教师资格证,如何教师资格证,如何报考教师资格证,全国教师资格证报名入口,哪里考教师资格证,每年教师资格证考试时间,每年教师资格证报名时间,考试教师资格证,考教师资格证资格,考教师资格证怎么报名,考教师资格证在哪里报名,考教师资格证在哪报名,考教师资格证有用么,考教师资格证有哪些科目,考教师资格证要求,考教师资格证要考哪些,考教师资格证需要什么,考教师资格证时间,考教师资格证什么时候报名,考教师资格证考试时间,考教师资格证考试科目,考教师资格证都考什么,考教师资格证的资格,考教师资格证的要求,考教师资格证的时间,考教师资格证的内容,考教师资格证的报名时间,考教师资格证报名时间,考教师资格证报班,考教师资格证,考教师证资格,今年教师资格证什么时候报名,今年教师资格证考试时间,今年教师资格证报名时间,教师资格证最新,教师资格证证怎么考,教师资格证证考试时间,教师资格证证报名,教师资格证怎样考取,教师资格证怎么考取,教师资格证怎么考的,教师资格证怎么考,教师资格证怎么报名啊,教师资格证怎么报名,教师资格证怎么报考,教师资格证在那里考,教师资格证有用,教师资格证样子,教师资格证小学条件,教师资格证下半年考试时间,教师资格证下半年笔试时间,教师资格证网报名入口,教师资格证条件要求,教师资格证书报考条件,教师资格证是怎么考的,教师资格证什么时候报名,教师资格证啥时候考,教师资格证如何考取,教师资格证如何考,教师资格证如何报名,教师资格证认证照片要求,教师资格证认定网报,教师资格证人数,教师资格证哪个网报名,教师资格证每年考几次,教师资格证每年几次,教师资格证每年的考试时间,教师资格证每年的报名时间,教师资格证每年报名时间,教师资格证考试资格,教师资格证考试怎样报名,教师资格证考试在哪考,教师资格证考试要求,教师资格证考试项目,教师资格证考试下半年时间,教师资格证考试网上报名,教师资格证考试网官网,教师资格证考试时间,教师资格证考试时候,教师资格证考试什么时间报名,教师资格证考试什么时间,教师资格证考试什么时候考,教师资格证考试什么时候报名,教师资格证考试日期,教师资格证考试内容,教师资格证考试哪里报名,教师资格证考试科目有哪些,教师资格证考试考些什么,教师资格证考试考什么,教师资格证考试考啥,教师资格证考试官网,教师资格证考试都考什么,教师资格证考试都考啥,教师资格证考试的时间,教师资格证考试成绩,教师资格证考试报名要求,教师资格证考试报名入口,教师资格证考试,教师资格证考什么内容,教师资格证考什么,教师资格证考哪些内容,教师资格证考哪几门啊,教师资格证考录政策,教师资格证考几年,教师资格证考的内容,教师资格证考,教师资格证何时考,教师资格证合格证有效期,教师资格证好考不,教师资格证都考什么,教师资格证的考试,教师资格证的报考条件,教师资格证报名怎么报,教师资格证报名在哪里,教师资格证报名与考试时间,教师资格证报名一年有几次,教师资格证报名要求,教师资格证报名要多少钱,教师资格证报名网址,教师资格证报名网站,教师资格证报名条件,教师资格证报名时间,教师资格证报名入口官网,教师资格证报名入口,教师资格证报名类别,教师资格证报名考试时间,教师资格证报名考什么,教师资格证报名截止日,教师资格证报名和考试时间,教师资格证报名官网,教师资格证报名费用是多少,教师资格证报名方式,教师资格证报名地点,教师资格证报名的时间,教师资格证报名带什么,教师资格证报名,教师资格证报考网站,教师资格证报考网,教师资格证报考条件,教师资格证报考试时间,教师资格证报,教师资格证版本,教师资格证报考条件,教师资格证,教师教师资格证考试,教师教师资格证,教教师资格证考试时间,国考教师资格证报名条件,国考教师资格证报名时间,国考教师资格证报名,国考教师资格证报考条件,国家教师资格证认定网,高校教师资格证报名条件,高级教师资格证报考条件,初级教师资格证考试,北京教师资格证审核,报名教师资格证要求,报名教师资格证条件,报考教师资格证资格,报考教师资格证要求,报考教师资格证条件,报教师资格证,2018年教师资格证报名入口,2018教师资格证下半年报名时间,2018教师资格证下半年,2018教师资格证什么时候报名,2018教师资格证报名官网,做营养师好吗,做营养师的条件,注册营养师怎么样,注册营养师考试,注册营养师2018,注册考营养师高级营养师,注册国际营养师,注册高级营养师,中级营养师难考吗,中级营养师,中国注册营养师,怎样学营养师,怎样考营养师资格证,怎样考二级营养师,怎么样考营养师,怎么学营养师,怎么考营养师资格证,怎么考营养师管理师,怎么考营养师,怎么考取营养师,怎么报名营养师,怎么报考国家营养师,有关营养师的证书,营养师资格证有用吗,营养师资格证书,营养师注册,营养师中级,营养师证怎样考,营养师证怎么考,营养师证有用吗,营养师证有什么用,营养师证有几种,营养师证什么时候考,营养师证考试条件,营养师证好考吗,营养师证等级,营养师证报名条件,营养师证,营养师这个职业怎么样,营养师怎么学习,营养师怎么学,营养师怎么考,营养师怎么工作,营养师怎么报名学习,营养师怎么报名,营养师有没有用,营养师有,营养师一级培训,营养师一级管理师,营养师学习,营养师学慧网,营养师学费多少,营养师需要条件,营养师需要具备哪些条件,营养师条件,营养师收入,营养师时间,营养师什么时候考试,营养师什么时候考,营养师申报条件,营养师如何考取,营养师如何考,营养师如何报名,营养师普为,营养师难考吗,营养师哪里学,营养师论文,营养师考试营养师考试时间,营养师考试要求,营养师考试条件,营养师考试时间,营养师考试什么时候,营养师考试,营养师具体做什么,营养师具体是做什么的,营养师健康管理师,营养师基础,营养师好考不,营养师行业,营养师国家,营养师管理师怎么样,营养师管理师有用吗,营养师管理师费用,营养师管理师等级,营养师管理师,营养师管理好吗,营养师公共营养师,营养师高师,营养师高级证书,营养师高级,营养师分几级,营养师分几个级别,营养师二级,营养师等级,营养师的证怎么考,营养师的证有用吗,营养师的报名条件,营养师的报名,营养师的报考资格,营养师报名网,营养师报名条件,营养师报名,营养师报,营养师,学营养师的条件,学习营养师,学慧网营养师,想学营养师,想考营养师,想考个营养师,我想学营养师,我想考营养师,我考营养师,网上报名营养师,网上报考营养师,如何做营养师,如何学营养师,如何考营养师资格证,如何考营养师管理师,如何考营养师,如何考取营养师,如何考高级营养师证,如何成为营养师,如何才能考营养师,全国营养师资格证,全国营养师考试,全国营养师,请营养师,哪里学营养师,哪里需要营养师,哪里可以考营养师,考营养师证有用吗,考营养师有什么用,考营养师要什么条件,考营养师需要什么条件,考营养师需要什么,考营养师需要哪些条件,考营养师条件,考营养师管理师有用吗,考营养师的条件,考营养师,考取营养师,健康营养师资格证,好的营养师培训,国家注册营养师考试,国家中级营养师怎么考,国家中级营养师,国家营养师资格证书,国家营养师资格证,国家营养师资格考试,国家营养师证,国家营养师怎么考,国家营养师考试网,国家营养师考试条件,国家营养师考试时间,国家营养师考试,国家营养师,国家一级营养师证,国家一级营养师,国家高级营养师,国家二级营养师考试,国际注册营养师考试,国际注册营养师,国际营养师证,国际营养师,关于营养师考试,公共营养师考试条件,高级营养师资格证,高级营养师证怎么考,高级营养师证,高级营养师培训,高级营养师管理师有用吗,高级营养师报名条件,高级营养师报名,高级营养师报考,高级营养师,二级营养师,的营养师,初级营养师,保健营养师国际营养师,aci国际营养师,2018营养师,2018年营养师,自学物业管理,注册物业管理师证书,质量管理员证书,找物业管理公司,怎么办物业经理证书,扬州物业管理员证书,徐州物业管理经理上岗证,物业项目经理证书,物业项目负责人证书,物业人力资源管理,物业企业经理证书怎么考,物业企业经理证书,物业企业经理人证书,物业经理证书怎么考,物业经理证书有用吗,物业经理证书样本,物业经理证书如何考取,物业经理证书报考,物业经理需要什么证书,物业经理上岗证书,物业经理人资格证书,物业经理人上岗证书,物业经理人岗位证书,物业经理岗位证书报考,物业经理岗位证书,物业管理专业人员职业资格证书,物业管理员资格证书,物业管理员资格证,物业管理员证书,物业管理员上岗证书,物业管理员工作内容,物业管理员工作服,物业管理员工作,物业管理员报名条件,物业管理项目经理资格证,物业管理项目经理证书,物业管理特约服务,物业管理人员资格证书,物业管理人员证书,物业管理人力资源,物业管理企业经理证书,物业管理企业经理上岗证书,物业管理企业经理上岗证培训班,物业管理企业经理上岗证培训,物业管理企业经理上岗证考试,物业管理企业经理上岗证,物业管理经理资格证书,物业管理经理资格证,物业管理经理上岗证书,物业管理经理上岗证培训,物业管理经理上岗证,物业管理公司级别,物业管理公司查询,物业管理岗位证书,物业管理服务等级标准,物业管理部门经理证,物业管理部门经理上岗证,物业岗位经理证书,物业服务与管理,物业部门经理证书,物业安全管理的主要内容,苏州物业管理师挂靠,上海物业经理岗位证书,上海物业管理员证书,上海物业管理员上岗证培训,上海物业管理企业经理上岗证,上海物业管理经理人上岗证,全国物业管理企业经理证书,全国物业管理企业经理上岗证,全国物业管理经理资格证书,宁波物业管理经理证,宁波物业管理经理上岗证,考物业经理证书,建设部物业经理岗位证书,建设部物业管理经理上岗证,淮安物业管理证书,杭州物业管理员证书,杭州物业管理经理证,广东省物业管理行业协会,成都物业管理协会官网,常州物业管理员上岗证,常州物业管理经理上岗证,做心理咨询师,最好的心理咨询师培训班,咨询心理咨询师,专业心理咨询师,专业的心理咨询师,著名的心理咨询师,中山心理咨询师,中级心理咨询师,中级心理治疗师,中国国家心理咨询师,知名心理咨询师,知名的心理咨询师,正规心理咨询师培训机构,正规心理咨询师,找心理咨询师有用吗,怎样找心理咨询师,怎样成为心理咨询师,怎样才能成为一个心理咨询师,怎么样成为心理咨询师,怎么选择心理咨询师培训机构,怎么考心理咨询师证,怎么当心理咨询师,怎么才能当心理咨询师,在线心理咨询师免费,在线心理咨询师,在线心里医生咨询师,在哪里考心理咨询师,有心理咨询师吗,有名心理咨询师,一级心理咨询师条件,一级心理咨询师培训班,一级心理咨询师,心理咨询咨询师,心理咨询师做什么,心理咨询师最高几级,心理咨询师资格证怎么考,心理咨询师资格证考试,心理咨询师资格证报考条件,心理咨询师资格证,心理咨询师资格考试报名条件,心理咨询师咨询,心理咨询师著名,心理咨询师中心,心理咨询师中山,心理咨询师指导,心理咨询师直接考二级,心理咨询师证怎样报名,心理咨询师证怎么考,心理咨询师证有用,心理咨询师证有效期,心理咨询师证什么时候考,心理咨询师证考试时间,心理咨询师证报名,心理咨询师证,心理咨询师怎样,心理咨询师怎么考,心理咨询师在线咨询,心理咨询师在线,心理咨询师在哪里找,心理咨询师有哪些人,心理咨询师有几级,心理咨询师有,心理咨询师应具备的条件,心理咨询师一级报名条件,心理咨询师一级报名,心理咨询师一级,心理咨询师一般什么时候报名,心理咨询师一般多少钱,心理咨询师要考什么,心理咨询师学习网,心理咨询师学习,心理咨询师学会,心理咨询师需要考什么证,心理咨询师信息,心理咨询师心理医生,心理咨询师网站,心理咨询师网上咨询,心理咨询师网上报名系统,心理咨询师网上报名,心理咨询师网上,心理咨询师网,心理咨询师推荐,心理咨询师室,心理咨询师是做什么的,心理咨询师师报考条件,心理咨询师师,心理咨询师什么,心理咨询师三级考几门,心理咨询师全国,心理咨询师去哪考,心理咨询师培训班哪个好,心理咨询师培训班费用,心理咨询师排名,心理咨询师那里有,心理咨询师哪里找,心理咨询师哪里的好,心理咨询师哪好,心理咨询师免费咨询,心理咨询师免费,心理咨询师论坛,心理咨询师流程,心理咨询师可以做什么,心理咨询师可以自己考吗,心理咨询师考试咨询,心理咨询师考试怎么报名,心理咨询师考试条件,心理咨询师考试什么时候报名,心理咨询师考试入口,心理咨询师考试日期,心理咨询师考试难吗,心理咨询师考试难不难,心理咨询师考试级别,心理咨询师考试机构,心理咨询师考试官网,心理咨询师考试费用,心理咨询师考试费,心理咨询师考试答案,心理咨询师考试报名条件,心理咨询师考试班,心理咨询师考什么,心理咨询师考哪些科目,心理咨询师级别,心理咨询师基础,心理咨询师湖南,心理咨询师河南,心理咨询师河北,心理咨询师和心理医生,心理咨询师好考吗,心理咨询师好,心理咨询师过关率,心理咨询师国家考试,心理咨询师官网,心理咨询师分为几级,心理咨询师分类,心理咨询师分几级,心理咨询师二级资格证,心理咨询师二级正规培训,心理咨询师二级条件,心理咨询师二级视频,心理咨询师二级培训机构,心理咨询师二级理论,心理咨询师二级考试时间,心理咨询师二级考试科目,心理咨询师二级考试报名,心理咨询师二级考试,心理咨询师二级考几门,心理咨询师二级价格,心理咨询师二级代报名,心理咨询师二级答案,心理咨询师二级报名条件,心理咨询师二级报名,心理咨询师二级,心理咨询师都考什么,心理咨询师等级,心理咨询师的资格证,心理咨询师的资格,心理咨询师的条件,心理咨询师的考试条件,心理咨询师的级别,心理咨询师的工作内容,心理咨询师的等级,心理咨询师的,心理咨询师成绩查询网,心理咨询师成绩查询时间,心理咨询师查询成绩,心理咨询师报名哪家好,心理咨询师报,心理咨询师2018考试时间,心理咨询师2018,心理咨询师2016,心理咨询师2015,心理咨询师1级,心理咨询师,心理咨询,心理咨师,心理治疗治疗师,心理治疗师网,心理治疗师培训班,心理治疗师考试培训,心理治疗师和心理医生,心理治疗师二级,心理治疗师报名时间,心理在线咨询师,心理医生咨询师,心理医生与心理咨询师,心理一级咨询师,心理学咨询师怎么考,心理学咨询师怎么报名,心理心理咨询师,心理师在线咨询,心理免费咨询师,心理健康师,心理初级咨询师考试咨询,心里咨询师在线咨询,心里咨询师是什么,想考心理咨询师证,想看心理咨询师,我想找心理咨询师,网心理咨询师,网上咨询心理师,网上找心理咨询师,网上心理咨询专家,网上心理咨询师,网上免费心理咨询师,网上的心理咨询师,市心理咨询师,上海心理治疗师,上海市心理咨询师,如何做一名心理咨询师,如何成为一名心理咨询师,如何成为心理咨询师,全国知名心理咨询师,全国二级心理咨询师有用吗,权威心理咨询师,去哪里找心理咨询师,情感心理咨询师在线,那有心理咨询师,哪心理咨询师好,哪里能考心理咨询师证,哪里可以找心理咨询师,免费在线心理咨询师,免费心理咨询师在线咨询,免费心理咨询师在线,免费的心理咨询师,零基础心理咨询师,考心理咨询师有什么条件,考心理咨询师要多少钱,考心理咨询师哪家好,考心理咨询师费用,考心理咨询师二级,考心理咨询师的条件,济南市心理咨询师,湖南心理咨询师,河北心理咨询师,好的心理咨询师,国内有名的心理咨询师,国家注册二级心理咨询师,国家职业心理咨询师培训,国家一级心理咨询师,国家心理咨询师怎么考,国家心理咨询师条件,国家心理咨询师级别,国家心理咨询师机构,国家心理咨询师官网,国家心理咨询师二级,国家心理咨询师报名费用,国家心理咨询师吧,国家心理咨询师,国家心理治疗师,国家心理辅导师,国家级心理咨询师考试,国家高级心理咨询师,国家二级心理咨询师真题,国家二级心理咨询师培训机构,国家二级心理咨询师考试,国家二级心理咨询师查询,国家二级心理咨询师报考条件,国际心理咨询师怎么考,国际心理咨询师,广东省心理咨询师,高级心理咨询师,二级心理咨询师考试科目,二级心理咨询师考试报名条件,二级心理咨询师考试,二级心理咨询师机构,二级心理咨询师个人成长报告,二级心理咨询师成长报告,二级心理咨询师报名条件,二级心理咨询师案例分析,二级心理咨询师,儿童心理咨询师怎么考,的心理咨询师,从事心理咨询师,初级心理咨询师考试,比较好的心理咨询师,安徽省心理咨询师,2018心理咨询师证考试,2018心理咨询师新政策,2018心理咨询师,2018年心理咨询师证,2018年心理咨询师,2018年下半年心理咨询师报名时间,2018年还有心理咨询师考试吗,2018国家心理咨询师,2016心理咨询师,2016年心理咨询师,2016国家心理咨询师,2015心理咨询师,2015年心理咨询师,2015国家心理咨询师,注册一级消防师报考条件,注册一级消防工程师报名时间,注册消防师资格证,注册消防师证书,注册消防师证,注册消防师报名费用,注册消防工程师资格考试时间,注册消防工程师资格考试,注册消防工程师证书有什么用,注册消防工程师证好考吗,注册消防工程师一级证,注册消防工程师学习资料,注册消防工程师网上课程,注册消防工程师网课,注册消防工程师考试日期,注册消防工程师考试免试条件,注册消防工程师报名时间广西省,注册二级消防师报考条件,浙江一级注册消防师报考条件,怎样报考消防工程师证,怎么报考消防工程师证,一级注册消防师证,一级注册消防师报名条件,一级注册消防师报考条件,一级注册消防工程师资格证书,一级注册消防工程师网课,一级注册消防工程师的报名时间,一级注册消防工程师报名时间,一级消防注册师报名条件,一级消防注册师报考条件,一级消防注册工程师报名时间,一级消防证书报考条件,一级消防证书,一级消防师证怎么考,一级消防师证报考条件,一级消防师证,一级消防工程师资格考试,一级消防工程师证有什么用,一级消防工程师证考试条件,一级消防工程师证好考吗,一级消防工程师证报名条件,一级消防工程师证报考条件,一级消防工程师证报考,一级消防工程师免考条件,消防证中级报考条件,消防证书在哪里报名,消防证书有几级,消防证书报名条件,消防证书报考,消防证工程师报名条件,消防一级工程师证,消防师资格证报考条件,消防师资格证,消防师证怎么考,消防师证书,消防师证难考么,消防师证好考吗,消防师证报名资格,消防师证报名条件,消防师证报名时间,消防师证报名,消防师证报考要求,消防师证报考条件,消防师证,消防考证,消防工程证书怎么考,消防工程证书,消防工程师资格证书,消防工程师资格条件,消防工程师资格认证,消防工程师资格考试时间,消防工程师资格考试,消防工程师执业证书,消防工程师证值钱吗,消防工程师证怎么注册,消防工程师证怎么考,消防工程师证怎么报考,消防工程师证有用么,消防工程师证有什么用,消防工程师证有哪些,消防工程师证网上报名,消防工程师证条件,消防工程师证书有用吗,消防工程师证书是什么,消防工程师证书考几门,消防工程师证书好考吗,消防工程师证书好不好,消防工程师证书报考条件,消防工程师证书,消防工程师证是什么,消防工程师证试题,消防工程师证什么时候考试,消防工程师证如何考,消防工程师证培训机构,消防工程师证难考吗,消防工程师证考试条件,消防工程师证考试内容,消防工程师证考试,消防工程师证好考吗,消防工程师证都考什么内容,消防工程师证报名条件,消防工程师证报名时间,消防工程师证报名,消防工程师证报考条件,消防工程师证报考时间,消防工程师证报考,消防工程师证,消防工程师考试题,消防工程师考试好考吗,消防工程师考试好过吗,消防工程师报名资格不够,消防工程师报名资格,消防工程师报名需要什么条件,消防工程师报考资格,天津一级注册消防师报考条件,苏州二级消防师报名条件,苏州二级消防工程师报考条件,深圳消防工程师报名,上海二级消防师报名条件,山西注册消防工程师考试时间,山西省注册消防师报名条件,如何报考消防工程师证书,如何报考消防工程师证,考注册消防师什么条件,考消防证书,考消防师证,考消防工程师证条件,考消防工程师证书,考消防工程师证容易吗,江苏注册消防师报考条件,江苏消防师证报考条件,江苏消防师证,河南注册消防师报名条件,河北注册消防师报名条件,河北注册消防师报考时间,广西省注册消防师报名时间,广东消防工程师证报考条件,高级消防证书,福建注册消防师报考时间,二级注册消防师报名,二级注册消防师报考条件,二级消防师证报考条件,二级消防师报名条件,二级消防工程师资格证,二级消防工程师证书考试时间,二级消防工程师证书考试培训,二级消防工程师考试好吗,二级消防工程师考试好过么,二级消防工程师考试好过吗,二级消防工程师考试好,二级消防工程师报名资格,二级消防工程师报名条件广东省,二级消防工程师报考资格,二级级消防师报考条件,北京市一级消防工程师证书,报考注册消防师需要什么条件,报考一级消防工程师证,报考消防工程师资格,报考消防工程师证书,2016年消防工程师证书考试,重庆人力资源资格证报名,怎么考人力资源证书,怎么报考人力资源资格证,有关人力资源的证书,一级人力资源管理证书,想考人力资源证书,天津市人力资源证书,深圳人力资源证书,上海市人力资源证书,上海人力资源证书,上海人力资源三级证书,如何考人力资源证书,人力资源资格证书有用吗,人力资源资格证书考试,人力资源资格证书二级,人力资源资格证书,人力资源职业资格证书,人力资源职称证书,人力资源证书怎么考,人力资源证书怎么报考,人力资源证书有哪些,人力资源证书有几级,人力资源证书四级,人力资源证书三级,人力资源证书培训机构,人力资源证书考试条件,人力资源证书考试时间,人力资源证书考试报名条件,人力资源证书考试报名时间,人力资源证书考试,人力资源证书考什么,人力资源证书分几级,人力资源证书二级,人力资源证书等级,人力资源证书初级,人力资源证书补贴,人力资源证书报名条件,人力资源证书报名时间,人力资源证书报名费,人力资源证书报名,人力资源证书报考条件,人力资源证书报考时间,人力资源证书办理,人力资源证书,人力资源证的条件,人力资源证报考网站,人力资源证3级,人力资源证,人力资源有哪些证书,人力资源需要什么证书,人力资源相关证书,人力资源相关的证书,人力资源三级证书报考条件,人力资源三级证书,人力资源认证考试,人力资源认证,人力资源类证书,人力资源考试认证,人力资源管理资格证书,人力资源管理职业资格证书,人力资源管理职业证书,人力资源管理证书什么时候考,人力资源管理证书考试,人力资源管理证书报名,人力资源管理证书报考条件,人力资源管理证书报考,人力资源管理证书,人力资源管理员证书,人力资源管理师证书有什么用,人力资源管理师证书报名,人力资源管理师证书报考条件,人力资源管理师证书,人力资源管理从业资格证书,人力资源管理初级证书,人力资源管理3级证书,人力资源岗位证书,人力资源方面证书,人力资源方面的证书,人力资源二级证书报名,人力资源二级证书报考条件,人力资源二级证书,人力资源都有什么证书,人力资源等级证,人力资源的相关证书,人力资源从业资格证书报考条件,人力资源从业资格证书报考,人力资源从业资格证书,人力资源从业证书,人力资源初级证书,人力资源部颁发的证书,人力资源3级证书,人力资源2级证书,人力证书,全国人力资源资格证考试,全国人力资源证,企业人力资源证书,企业人力资源管理证书,企业人力资源管理师资格证书,考人力资源资格证书,考人力资源证书,考人力资源二级证书有用么,考取人力资源证书,国家人力资源证书,广州人力资源证书,高级人力资源证,二级人力资源证书报考条件,初级人力资源资格证,北京人力资源证书考试,北京人力资源证书,报考人力资源证书,报考人力资源证,3级人力资源证,2018人力资源证书,2018人力资源二级证书报考条件";
//			String[] keyWordArray1 = keyWordArray.split(",");
//			for(String keyWord:keyWordArray1){
//				System.out.println(DateUtil.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//				Thread.sleep(500); //设置暂停的时间 500 毫秒
//				String url = ClientUtil.get("https://www.baidu.com/s?ie=utf-8&wd="+keyWord,true,"utf-8");
//
//				Document doc = Jsoup.parse(url);
//				Element rows = doc.select("div[id=content_left]").get(0);
//
//				int i=0;public String insertAdvertise(){
//					try {
//						String keyWordArray="pmp认证项目经理,pmp认证深圳培训,pmp认证培训那个好,pmp认证培训哪家好,pmp认证培训哪个好,pmp认证培训机构,pmp认证培训费用,pmp认证培训,pmp认证考前培训,pmp培训主要内容,pmp培训在线学习,pmp培训有哪些机构,pmp培训学院,pmp培训学校哪个好,pmp培训学费,pmp培训网络课程,pmp培训网络班,pmp培训推荐,pmp培训团购,pmp培训苏州哪家好,pmp培训苏州,pmp培训深圳,pmp培训上海哪家好,pmp培训厦门,pmp培训哪里好,pmp培训哪里比较好,pmp培训哪家最好,pmp培训哪家机构比较好,pmp培训哪家好,pmp培训哪家比较好上海,pmp培训哪家比较好,pmp培训哪个机构好,pmp培训哪个好,pmp培训哪个比较好,pmp培训吗费用,pmp培训课程费用,pmp培训济南,pmp培训机构有哪些,pmp培训机构选择,pmp培训机构武汉,pmp培训机构推荐,pmp培训机构通过率,pmp培训机构苏州,pmp培训机构排名北京,pmp培训机构排名,pmp培训机构哪家强,pmp培训机构哪家比较好,pmp培训机构哪个最好,pmp培训机构哪个好,pmp培训机构济南,pmp培训机构杭州,pmp培训机构比较,pmp培训慧翔,pmp培训广州,pmp培训费用国际,pmp培训费,pmp培训点,pmp培训的目的,pmp培训的费用,pmp哪个培训机构好,pmp哪个培训好,pmp哪个机构好,pmp免试,pmp免费公开课,pmp免费,pmp没有项目管理经验,pmp论坛,pmp流程,pmp兰州培训机构,pmp课程介绍,pmp考试项目,pmp考前培训机构,pmp机构,pmp和项目管理,pmp和系统集成项目管理师,pmp和高级项目管理师,pmp杭州培训机构,pmp国际项目管理师,pmp国际认证培训,pmp管理质量,pmp管理实践,pmp管理认证,pmp管理圈,pmp管理培训,pmp官方指定培训机构,pmp官方网站,pmp官方培训机构,pmp官方教材,pmp工作经验要求,pmp工程管理,pmp的通过率,pmp的培训机构,pmp的培训,pmp不培训,pmp北京培训机构哪个好,pmp报名截止,pmp报名,pmp报考资质,pmp报考网站,pmp报考费,pmp报过,pmp保过,pmp9月报名,pmp,pmi项目管理培训,it项目管理pmp,2018项目经理pmp考试,2018项目管理pmp,2018年pmp项目管理师,2017pmp,2016年pmp培训,中职教师资格证考试条件,中小学教师资格证报名条件,中小学教师资格证报名入口,中小学教师资格证报考条件,中文教师资格证,怎样能考教师资格证,怎样考取教师资格证,怎样考教师资格证,怎样报考教师资格证,怎么考教师资格证,小学教师资格证如何报名,现在教师资格证怎么考,下半年教师资格证考试时间,体育教师资格证报名条件,如何考教师资格证,如何教师资格证,如何报考教师资格证,全国教师资格证报名入口,哪里考教师资格证,每年教师资格证考试时间,每年教师资格证报名时间,考试教师资格证,考教师资格证资格,考教师资格证怎么报名,考教师资格证在哪里报名,考教师资格证在哪报名,考教师资格证有用么,考教师资格证有哪些科目,考教师资格证要求,考教师资格证要考哪些,考教师资格证需要什么,考教师资格证时间,考教师资格证什么时候报名,考教师资格证考试时间,考教师资格证考试科目,考教师资格证都考什么,考教师资格证的资格,考教师资格证的要求,考教师资格证的时间,考教师资格证的内容,考教师资格证的报名时间,考教师资格证报名时间,考教师资格证报班,考教师资格证,考教师证资格,今年教师资格证什么时候报名,今年教师资格证考试时间,今年教师资格证报名时间,教师资格证最新,教师资格证证怎么考,教师资格证证考试时间,教师资格证证报名,教师资格证怎样考取,教师资格证怎么考取,教师资格证怎么考的,教师资格证怎么考,教师资格证怎么报名啊,教师资格证怎么报名,教师资格证怎么报考,教师资格证在那里考,教师资格证有用,教师资格证样子,教师资格证小学条件,教师资格证下半年考试时间,教师资格证下半年笔试时间,教师资格证网报名入口,教师资格证条件要求,教师资格证书报考条件,教师资格证是怎么考的,教师资格证什么时候报名,教师资格证啥时候考,教师资格证如何考取,教师资格证如何考,教师资格证如何报名,教师资格证认证照片要求,教师资格证认定网报,教师资格证人数,教师资格证哪个网报名,教师资格证每年考几次,教师资格证每年几次,教师资格证每年的考试时间,教师资格证每年的报名时间,教师资格证每年报名时间,教师资格证考试资格,教师资格证考试怎样报名,教师资格证考试在哪考,教师资格证考试要求,教师资格证考试项目,教师资格证考试下半年时间,教师资格证考试网上报名,教师资格证考试网官网,教师资格证考试时间,教师资格证考试时候,教师资格证考试什么时间报名,教师资格证考试什么时间,教师资格证考试什么时候考,教师资格证考试什么时候报名,教师资格证考试日期,教师资格证考试内容,教师资格证考试哪里报名,教师资格证考试科目有哪些,教师资格证考试考些什么,教师资格证考试考什么,教师资格证考试考啥,教师资格证考试官网,教师资格证考试都考什么,教师资格证考试都考啥,教师资格证考试的时间,教师资格证考试成绩,教师资格证考试报名要求,教师资格证考试报名入口,教师资格证考试,教师资格证考什么内容,教师资格证考什么,教师资格证考哪些内容,教师资格证考哪几门啊,教师资格证考录政策,教师资格证考几年,教师资格证考的内容,教师资格证考,教师资格证何时考,教师资格证合格证有效期,教师资格证好考不,教师资格证都考什么,教师资格证的考试,教师资格证的报考条件,教师资格证报名怎么报,教师资格证报名在哪里,教师资格证报名与考试时间,教师资格证报名一年有几次,教师资格证报名要求,教师资格证报名要多少钱,教师资格证报名网址,教师资格证报名网站,教师资格证报名条件,教师资格证报名时间,教师资格证报名入口官网,教师资格证报名入口,教师资格证报名类别,教师资格证报名考试时间,教师资格证报名考什么,教师资格证报名截止日,教师资格证报名和考试时间,教师资格证报名官网,教师资格证报名费用是多少,教师资格证报名方式,教师资格证报名地点,教师资格证报名的时间,教师资格证报名带什么,教师资格证报名,教师资格证报考网站,教师资格证报考网,教师资格证报考条件,教师资格证报考试时间,教师资格证报,教师资格证版本,教师资格证报考条件,教师资格证,教师教师资格证考试,教师教师资格证,教教师资格证考试时间,国考教师资格证报名条件,国考教师资格证报名时间,国考教师资格证报名,国考教师资格证报考条件,国家教师资格证认定网,高校教师资格证报名条件,高级教师资格证报考条件,初级教师资格证考试,北京教师资格证审核,报名教师资格证要求,报名教师资格证条件,报考教师资格证资格,报考教师资格证要求,报考教师资格证条件,报教师资格证,2018年教师资格证报名入口,2018教师资格证下半年报名时间,2018教师资格证下半年,2018教师资格证什么时候报名,2018教师资格证报名官网,做营养师好吗,做营养师的条件,注册营养师怎么样,注册营养师考试,注册营养师2018,注册考营养师高级营养师,注册国际营养师,注册高级营养师,中级营养师难考吗,中级营养师,中国注册营养师,怎样学营养师,怎样考营养师资格证,怎样考二级营养师,怎么样考营养师,怎么学营养师,怎么考营养师资格证,怎么考营养师管理师,怎么考营养师,怎么考取营养师,怎么报名营养师,怎么报考国家营养师,有关营养师的证书,营养师资格证有用吗,营养师资格证书,营养师注册,营养师中级,营养师证怎样考,营养师证怎么考,营养师证有用吗,营养师证有什么用,营养师证有几种,营养师证什么时候考,营养师证考试条件,营养师证好考吗,营养师证等级,营养师证报名条件,营养师证,营养师这个职业怎么样,营养师怎么学习,营养师怎么学,营养师怎么考,营养师怎么工作,营养师怎么报名学习,营养师怎么报名,营养师有没有用,营养师有,营养师一级培训,营养师一级管理师,营养师学习,营养师学慧网,营养师学费多少,营养师需要条件,营养师需要具备哪些条件,营养师条件,营养师收入,营养师时间,营养师什么时候考试,营养师什么时候考,营养师申报条件,营养师如何考取,营养师如何考,营养师如何报名,营养师普为,营养师难考吗,营养师哪里学,营养师论文,营养师考试营养师考试时间,营养师考试要求,营养师考试条件,营养师考试时间,营养师考试什么时候,营养师考试,营养师具体做什么,营养师具体是做什么的,营养师健康管理师,营养师基础,营养师好考不,营养师行业,营养师国家,营养师管理师怎么样,营养师管理师有用吗,营养师管理师费用,营养师管理师等级,营养师管理师,营养师管理好吗,营养师公共营养师,营养师高师,营养师高级证书,营养师高级,营养师分几级,营养师分几个级别,营养师二级,营养师等级,营养师的证怎么考,营养师的证有用吗,营养师的报名条件,营养师的报名,营养师的报考资格,营养师报名网,营养师报名条件,营养师报名,营养师报,营养师,学营养师的条件,学习营养师,学慧网营养师,想学营养师,想考营养师,想考个营养师,我想学营养师,我想考营养师,我考营养师,网上报名营养师,网上报考营养师,如何做营养师,如何学营养师,如何考营养师资格证,如何考营养师管理师,如何考营养师,如何考取营养师,如何考高级营养师证,如何成为营养师,如何才能考营养师,全国营养师资格证,全国营养师考试,全国营养师,请营养师,哪里学营养师,哪里需要营养师,哪里可以考营养师,考营养师证有用吗,考营养师有什么用,考营养师要什么条件,考营养师需要什么条件,考营养师需要什么,考营养师需要哪些条件,考营养师条件,考营养师管理师有用吗,考营养师的条件,考营养师,考取营养师,健康营养师资格证,好的营养师培训,国家注册营养师考试,国家中级营养师怎么考,国家中级营养师,国家营养师资格证书,国家营养师资格证,国家营养师资格考试,国家营养师证,国家营养师怎么考,国家营养师考试网,国家营养师考试条件,国家营养师考试时间,国家营养师考试,国家营养师,国家一级营养师证,国家一级营养师,国家高级营养师,国家二级营养师考试,国际注册营养师考试,国际注册营养师,国际营养师证,国际营养师,关于营养师考试,公共营养师考试条件,高级营养师资格证,高级营养师证怎么考,高级营养师证,高级营养师培训,高级营养师管理师有用吗,高级营养师报名条件,高级营养师报名,高级营养师报考,高级营养师,二级营养师,的营养师,初级营养师,保健营养师国际营养师,aci国际营养师,2018营养师,2018年营养师,自学物业管理,注册物业管理师证书,质量管理员证书,找物业管理公司,怎么办物业经理证书,扬州物业管理员证书,徐州物业管理经理上岗证,物业项目经理证书,物业项目负责人证书,物业人力资源管理,物业企业经理证书怎么考,物业企业经理证书,物业企业经理人证书,物业经理证书怎么考,物业经理证书有用吗,物业经理证书样本,物业经理证书如何考取,物业经理证书报考,物业经理需要什么证书,物业经理上岗证书,物业经理人资格证书,物业经理人上岗证书,物业经理人岗位证书,物业经理岗位证书报考,物业经理岗位证书,物业管理专业人员职业资格证书,物业管理员资格证书,物业管理员资格证,物业管理员证书,物业管理员上岗证书,物业管理员工作内容,物业管理员工作服,物业管理员工作,物业管理员报名条件,物业管理项目经理资格证,物业管理项目经理证书,物业管理特约服务,物业管理人员资格证书,物业管理人员证书,物业管理人力资源,物业管理企业经理证书,物业管理企业经理上岗证书,物业管理企业经理上岗证培训班,物业管理企业经理上岗证培训,物业管理企业经理上岗证考试,物业管理企业经理上岗证,物业管理经理资格证书,物业管理经理资格证,物业管理经理上岗证书,物业管理经理上岗证培训,物业管理经理上岗证,物业管理公司级别,物业管理公司查询,物业管理岗位证书,物业管理服务等级标准,物业管理部门经理证,物业管理部门经理上岗证,物业岗位经理证书,物业服务与管理,物业部门经理证书,物业安全管理的主要内容,苏州物业管理师挂靠,上海物业经理岗位证书,上海物业管理员证书,上海物业管理员上岗证培训,上海物业管理企业经理上岗证,上海物业管理经理人上岗证,全国物业管理企业经理证书,全国物业管理企业经理上岗证,全国物业管理经理资格证书,宁波物业管理经理证,宁波物业管理经理上岗证,考物业经理证书,建设部物业经理岗位证书,建设部物业管理经理上岗证,淮安物业管理证书,杭州物业管理员证书,杭州物业管理经理证,广东省物业管理行业协会,成都物业管理协会官网,常州物业管理员上岗证,常州物业管理经理上岗证,做心理咨询师,最好的心理咨询师培训班,咨询心理咨询师,专业心理咨询师,专业的心理咨询师,著名的心理咨询师,中山心理咨询师,中级心理咨询师,中级心理治疗师,中国国家心理咨询师,知名心理咨询师,知名的心理咨询师,正规心理咨询师培训机构,正规心理咨询师,找心理咨询师有用吗,怎样找心理咨询师,怎样成为心理咨询师,怎样才能成为一个心理咨询师,怎么样成为心理咨询师,怎么选择心理咨询师培训机构,怎么考心理咨询师证,怎么当心理咨询师,怎么才能当心理咨询师,在线心理咨询师免费,在线心理咨询师,在线心里医生咨询师,在哪里考心理咨询师,有心理咨询师吗,有名心理咨询师,一级心理咨询师条件,一级心理咨询师培训班,一级心理咨询师,心理咨询咨询师,心理咨询师做什么,心理咨询师最高几级,心理咨询师资格证怎么考,心理咨询师资格证考试,心理咨询师资格证报考条件,心理咨询师资格证,心理咨询师资格考试报名条件,心理咨询师咨询,心理咨询师著名,心理咨询师中心,心理咨询师中山,心理咨询师指导,心理咨询师直接考二级,心理咨询师证怎样报名,心理咨询师证怎么考,心理咨询师证有用,心理咨询师证有效期,心理咨询师证什么时候考,心理咨询师证考试时间,心理咨询师证报名,心理咨询师证,心理咨询师怎样,心理咨询师怎么考,心理咨询师在线咨询,心理咨询师在线,心理咨询师在哪里找,心理咨询师有哪些人,心理咨询师有几级,心理咨询师有,心理咨询师应具备的条件,心理咨询师一级报名条件,心理咨询师一级报名,心理咨询师一级,心理咨询师一般什么时候报名,心理咨询师一般多少钱,心理咨询师要考什么,心理咨询师学习网,心理咨询师学习,心理咨询师学会,心理咨询师需要考什么证,心理咨询师信息,心理咨询师心理医生,心理咨询师网站,心理咨询师网上咨询,心理咨询师网上报名系统,心理咨询师网上报名,心理咨询师网上,心理咨询师网,心理咨询师推荐,心理咨询师室,心理咨询师是做什么的,心理咨询师师报考条件,心理咨询师师,心理咨询师什么,心理咨询师三级考几门,心理咨询师全国,心理咨询师去哪考,心理咨询师培训班哪个好,心理咨询师培训班费用,心理咨询师排名,心理咨询师那里有,心理咨询师哪里找,心理咨询师哪里的好,心理咨询师哪好,心理咨询师免费咨询,心理咨询师免费,心理咨询师论坛,心理咨询师流程,心理咨询师可以做什么,心理咨询师可以自己考吗,心理咨询师考试咨询,心理咨询师考试怎么报名,心理咨询师考试条件,心理咨询师考试什么时候报名,心理咨询师考试入口,心理咨询师考试日期,心理咨询师考试难吗,心理咨询师考试难不难,心理咨询师考试级别,心理咨询师考试机构,心理咨询师考试官网,心理咨询师考试费用,心理咨询师考试费,心理咨询师考试答案,心理咨询师考试报名条件,心理咨询师考试班,心理咨询师考什么,心理咨询师考哪些科目,心理咨询师级别,心理咨询师基础,心理咨询师湖南,心理咨询师河南,心理咨询师河北,心理咨询师和心理医生,心理咨询师好考吗,心理咨询师好,心理咨询师过关率,心理咨询师国家考试,心理咨询师官网,心理咨询师分为几级,心理咨询师分类,心理咨询师分几级,心理咨询师二级资格证,心理咨询师二级正规培训,心理咨询师二级条件,心理咨询师二级视频,心理咨询师二级培训机构,心理咨询师二级理论,心理咨询师二级考试时间,心理咨询师二级考试科目,心理咨询师二级考试报名,心理咨询师二级考试,心理咨询师二级考几门,心理咨询师二级价格,心理咨询师二级代报名,心理咨询师二级答案,心理咨询师二级报名条件,心理咨询师二级报名,心理咨询师二级,心理咨询师都考什么,心理咨询师等级,心理咨询师的资格证,心理咨询师的资格,心理咨询师的条件,心理咨询师的考试条件,心理咨询师的级别,心理咨询师的工作内容,心理咨询师的等级,心理咨询师的,心理咨询师成绩查询网,心理咨询师成绩查询时间,心理咨询师查询成绩,心理咨询师报名哪家好,心理咨询师报,心理咨询师2018考试时间,心理咨询师2018,心理咨询师2016,心理咨询师2015,心理咨询师1级,心理咨询师,心理咨询,心理咨师,心理治疗治疗师,心理治疗师网,心理治疗师培训班,心理治疗师考试培训,心理治疗师和心理医生,心理治疗师二级,心理治疗师报名时间,心理在线咨询师,心理医生咨询师,心理医生与心理咨询师,心理一级咨询师,心理学咨询师怎么考,心理学咨询师怎么报名,心理心理咨询师,心理师在线咨询,心理免费咨询师,心理健康师,心理初级咨询师考试咨询,心里咨询师在线咨询,心里咨询师是什么,想考心理咨询师证,想看心理咨询师,我想找心理咨询师,网心理咨询师,网上咨询心理师,网上找心理咨询师,网上心理咨询专家,网上心理咨询师,网上免费心理咨询师,网上的心理咨询师,市心理咨询师,上海心理治疗师,上海市心理咨询师,如何做一名心理咨询师,如何成为一名心理咨询师,如何成为心理咨询师,全国知名心理咨询师,全国二级心理咨询师有用吗,权威心理咨询师,去哪里找心理咨询师,情感心理咨询师在线,那有心理咨询师,哪心理咨询师好,哪里能考心理咨询师证,哪里可以找心理咨询师,免费在线心理咨询师,免费心理咨询师在线咨询,免费心理咨询师在线,免费的心理咨询师,零基础心理咨询师,考心理咨询师有什么条件,考心理咨询师要多少钱,考心理咨询师哪家好,考心理咨询师费用,考心理咨询师二级,考心理咨询师的条件,济南市心理咨询师,湖南心理咨询师,河北心理咨询师,好的心理咨询师,国内有名的心理咨询师,国家注册二级心理咨询师,国家职业心理咨询师培训,国家一级心理咨询师,国家心理咨询师怎么考,国家心理咨询师条件,国家心理咨询师级别,国家心理咨询师机构,国家心理咨询师官网,国家心理咨询师二级,国家心理咨询师报名费用,国家心理咨询师吧,国家心理咨询师,国家心理治疗师,国家心理辅导师,国家级心理咨询师考试,国家高级心理咨询师,国家二级心理咨询师真题,国家二级心理咨询师培训机构,国家二级心理咨询师考试,国家二级心理咨询师查询,国家二级心理咨询师报考条件,国际心理咨询师怎么考,国际心理咨询师,广东省心理咨询师,高级心理咨询师,二级心理咨询师考试科目,二级心理咨询师考试报名条件,二级心理咨询师考试,二级心理咨询师机构,二级心理咨询师个人成长报告,二级心理咨询师成长报告,二级心理咨询师报名条件,二级心理咨询师案例分析,二级心理咨询师,儿童心理咨询师怎么考,的心理咨询师,从事心理咨询师,初级心理咨询师考试,比较好的心理咨询师,安徽省心理咨询师,2018心理咨询师证考试,2018心理咨询师新政策,2018心理咨询师,2018年心理咨询师证,2018年心理咨询师,2018年下半年心理咨询师报名时间,2018年还有心理咨询师考试吗,2018国家心理咨询师,2016心理咨询师,2016年心理咨询师,2016国家心理咨询师,2015心理咨询师,2015年心理咨询师,2015国家心理咨询师,注册一级消防师报考条件,注册一级消防工程师报名时间,注册消防师资格证,注册消防师证书,注册消防师证,注册消防师报名费用,注册消防工程师资格考试时间,注册消防工程师资格考试,注册消防工程师证书有什么用,注册消防工程师证好考吗,注册消防工程师一级证,注册消防工程师学习资料,注册消防工程师网上课程,注册消防工程师网课,注册消防工程师考试日期,注册消防工程师考试免试条件,注册消防工程师报名时间广西省,注册二级消防师报考条件,浙江一级注册消防师报考条件,怎样报考消防工程师证,怎么报考消防工程师证,一级注册消防师证,一级注册消防师报名条件,一级注册消防师报考条件,一级注册消防工程师资格证书,一级注册消防工程师网课,一级注册消防工程师的报名时间,一级注册消防工程师报名时间,一级消防注册师报名条件,一级消防注册师报考条件,一级消防注册工程师报名时间,一级消防证书报考条件,一级消防证书,一级消防师证怎么考,一级消防师证报考条件,一级消防师证,一级消防工程师资格考试,一级消防工程师证有什么用,一级消防工程师证考试条件,一级消防工程师证好考吗,一级消防工程师证报名条件,一级消防工程师证报考条件,一级消防工程师证报考,一级消防工程师免考条件,消防证中级报考条件,消防证书在哪里报名,消防证书有几级,消防证书报名条件,消防证书报考,消防证工程师报名条件,消防一级工程师证,消防师资格证报考条件,消防师资格证,消防师证怎么考,消防师证书,消防师证难考么,消防师证好考吗,消防师证报名资格,消防师证报名条件,消防师证报名时间,消防师证报名,消防师证报考要求,消防师证报考条件,消防师证,消防考证,消防工程证书怎么考,消防工程证书,消防工程师资格证书,消防工程师资格条件,消防工程师资格认证,消防工程师资格考试时间,消防工程师资格考试,消防工程师执业证书,消防工程师证值钱吗,消防工程师证怎么注册,消防工程师证怎么考,消防工程师证怎么报考,消防工程师证有用么,消防工程师证有什么用,消防工程师证有哪些,消防工程师证网上报名,消防工程师证条件,消防工程师证书有用吗,消防工程师证书是什么,消防工程师证书考几门,消防工程师证书好考吗,消防工程师证书好不好,消防工程师证书报考条件,消防工程师证书,消防工程师证是什么,消防工程师证试题,消防工程师证什么时候考试,消防工程师证如何考,消防工程师证培训机构,消防工程师证难考吗,消防工程师证考试条件,消防工程师证考试内容,消防工程师证考试,消防工程师证好考吗,消防工程师证都考什么内容,消防工程师证报名条件,消防工程师证报名时间,消防工程师证报名,消防工程师证报考条件,消防工程师证报考时间,消防工程师证报考,消防工程师证,消防工程师考试题,消防工程师考试好考吗,消防工程师考试好过吗,消防工程师报名资格不够,消防工程师报名资格,消防工程师报名需要什么条件,消防工程师报考资格,天津一级注册消防师报考条件,苏州二级消防师报名条件,苏州二级消防工程师报考条件,深圳消防工程师报名,上海二级消防师报名条件,山西注册消防工程师考试时间,山西省注册消防师报名条件,如何报考消防工程师证书,如何报考消防工程师证,考注册消防师什么条件,考消防证书,考消防师证,考消防工程师证条件,考消防工程师证书,考消防工程师证容易吗,江苏注册消防师报考条件,江苏消防师证报考条件,江苏消防师证,河南注册消防师报名条件,河北注册消防师报名条件,河北注册消防师报考时间,广西省注册消防师报名时间,广东消防工程师证报考条件,高级消防证书,福建注册消防师报考时间,二级注册消防师报名,二级注册消防师报考条件,二级消防师证报考条件,二级消防师报名条件,二级消防工程师资格证,二级消防工程师证书考试时间,二级消防工程师证书考试培训,二级消防工程师考试好吗,二级消防工程师考试好过么,二级消防工程师考试好过吗,二级消防工程师考试好,二级消防工程师报名资格,二级消防工程师报名条件广东省,二级消防工程师报考资格,二级级消防师报考条件,北京市一级消防工程师证书,报考注册消防师需要什么条件,报考一级消防工程师证,报考消防工程师资格,报考消防工程师证书,2016年消防工程师证书考试,重庆人力资源资格证报名,怎么考人力资源证书,怎么报考人力资源资格证,有关人力资源的证书,一级人力资源管理证书,想考人力资源证书,天津市人力资源证书,深圳人力资源证书,上海市人力资源证书,上海人力资源证书,上海人力资源三级证书,如何考人力资源证书,人力资源资格证书有用吗,人力资源资格证书考试,人力资源资格证书二级,人力资源资格证书,人力资源职业资格证书,人力资源职称证书,人力资源证书怎么考,人力资源证书怎么报考,人力资源证书有哪些,人力资源证书有几级,人力资源证书四级,人力资源证书三级,人力资源证书培训机构,人力资源证书考试条件,人力资源证书考试时间,人力资源证书考试报名条件,人力资源证书考试报名时间,人力资源证书考试,人力资源证书考什么,人力资源证书分几级,人力资源证书二级,人力资源证书等级,人力资源证书初级,人力资源证书补贴,人力资源证书报名条件,人力资源证书报名时间,人力资源证书报名费,人力资源证书报名,人力资源证书报考条件,人力资源证书报考时间,人力资源证书办理,人力资源证书,人力资源证的条件,人力资源证报考网站,人力资源证3级,人力资源证,人力资源有哪些证书,人力资源需要什么证书,人力资源相关证书,人力资源相关的证书,人力资源三级证书报考条件,人力资源三级证书,人力资源认证考试,人力资源认证,人力资源类证书,人力资源考试认证,人力资源管理资格证书,人力资源管理职业资格证书,人力资源管理职业证书,人力资源管理证书什么时候考,人力资源管理证书考试,人力资源管理证书报名,人力资源管理证书报考条件,人力资源管理证书报考,人力资源管理证书,人力资源管理员证书,人力资源管理师证书有什么用,人力资源管理师证书报名,人力资源管理师证书报考条件,人力资源管理师证书,人力资源管理从业资格证书,人力资源管理初级证书,人力资源管理3级证书,人力资源岗位证书,人力资源方面证书,人力资源方面的证书,人力资源二级证书报名,人力资源二级证书报考条件,人力资源二级证书,人力资源都有什么证书,人力资源等级证,人力资源的相关证书,人力资源从业资格证书报考条件,人力资源从业资格证书报考,人力资源从业资格证书,人力资源从业证书,人力资源初级证书,人力资源部颁发的证书,人力资源3级证书,人力资源2级证书,人力证书,全国人力资源资格证考试,全国人力资源证,企业人力资源证书,企业人力资源管理证书,企业人力资源管理师资格证书,考人力资源资格证书,考人力资源证书,考人力资源二级证书有用么,考取人力资源证书,国家人力资源证书,广州人力资源证书,高级人力资源证,二级人力资源证书报考条件,初级人力资源资格证,北京人力资源证书考试,北京人力资源证书,报考人力资源证书,报考人力资源证,3级人力资源证,2018人力资源证书,2018人力资源二级证书报考条件";
//						String[] keyWordArray1 = keyWordArray.split(",");
//						for(String keyWord:keyWordArray1){
//							System.out.println(DateUtil.getDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
//							Thread.sleep(500); //设置暂停的时间 500 毫秒
//							String url = ClientUtil.get("https://www.baidu.com/s?ie=utf-8&wd="+keyWord,true,"utf-8");
//
//							Document doc = Jsoup.parse(url);
//							Element rows = doc.select("div[id=content_left]").get(0);
//
//							int i=0;
//							for(Element row:rows.select("div[id~=\\d]")){
//								Element a = row.select("a").get(0);
//								if(StringUtils.isBlank(a.text())){
//									continue;
//								}
//								i+=1;//第几个
//								System.out.println("第"+i+"个");//标题
//								Advertise advertise = new Advertise();
//								advertise.setKeyWord(keyWord);
//								if(row.select(".ec_tuiguang_ppimlink").size()>0 || row.select(".ec_tuiguang_ppouter").size()>0){
//									System.out.print("推广  ");//是否推广
//									advertise.setAdvertiseType("推广");
//								}else{
//									advertise.setAdvertiseType("非推广");
//								}
//								System.out.println(" 百度的url:"+a.attr("href"));//百度的url
//								advertise.setBaiduUrl(a.attr("href"));
//								System.out.println("标题:"+a.text());//标题
//								advertise.setAdvertiseTitle(a.text());
//
//								String regex1 = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+[.][a-z]+";
//								String regex = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+";
//								Pattern pattern = Pattern.compile(regex1);
//								Matcher m = pattern.matcher(row.text());
//								if(m.find()){
//									System.out.println("url:"+m.group());//本身的网址
//									advertise.setCompanyUrl(m.group());
//								}else{
//									Pattern pattern2 = Pattern.compile(regex);
//									Matcher m2 = pattern2.matcher(row.text());
//									if(m2.find()){
//										System.out.println("url:"+m2.group());//本身的网址
//										advertise.setCompanyUrl(m2.group());
//									}
//								}
//								//查询页面编码格式，方式抓取网页信息时乱码
//								String charset = getEncodingByContentStream(a.attr("href"));
//								System.out.println("编码====================================:"+charset);
//								//进一步解析网址中的内容，获取所需的电话或者公司名称
//								String url1 = StringUtils.deleteWhitespace(ClientUtil.get(a.attr("href"), true, charset));
//								System.out.println("HTML页面====================================:"+url1);
//								if(url1!=null){
//									//1、找有限公司，取最后一个
//									//找联系我们 或者 关于我们 或者
//									Document docWeb = Jsoup.parse(url1);
//									//第一层查找公司名称
//									Elements elements = docWeb.getElementsContainingText("有限公司");
//									String companyName = "";
//									String mobilePhone ="";
//									String phone ="";
//									if(elements.size()>0){
//										companyName = elements.get(elements.size()-1).text();
//										advertise.setCompanyName(companyName);
//									}
//									//查找座机号
//									phone = checkTelephone(url1);
//									if(phone!=null&&phone!=""){
//										advertise.setTelePhone(phone);
//									}
//									//查找手机号
//									mobilePhone = checkCellphone(url1);
//									if(mobilePhone!=null&&mobilePhone!=""){
//										advertise.setCellPhone(mobilePhone);
//									}
//									System.out.println("第一层页面最后一个公司名称:"+companyName);
//									System.out.println("第一层页面手机号:"+mobilePhone);
//									System.out.println("第一层页面座机号:"+phone);
//									if(companyName==""||mobilePhone==null||phone==null){
//										//接着进行深层次抓取，通过联系我们或关于我们来查找公司名称和手机号
//										elements = docWeb.getElementsContainingText("联系我们");
//										String companyName2 = "";
//										if(elements.size()>0){
//											System.out.println("进入第二层=====================寻找联系我们");
//											companyName2 = elements.get(elements.size()-1).text();
//											String regexPhone = "(.*)(<a)(.*)(联系我们)(.*)";
//											Pattern patternPhone = Pattern.compile(regexPhone);
//											Matcher matcherPhone = patternPhone.matcher(url1);
//											boolean b = matcherPhone.matches();
//											if(b){
//												/*System.out.println("联系我们页面中的==== aaaaaa:"+matcherPhone.group(3));*/
//												regexPhone = "(.*)(href=\")(.*)(shtml)(\")(.*)";
//												patternPhone = Pattern.compile(regexPhone);
//												matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//												if(matcherPhone.matches()){
//													String aTab = matcherPhone.group(3)+"shtml";
//													System.out.println("联系我们页面的a链接:"+aTab);
//													//获取联系我们的a链接后
//													//查询页面编码格式，方式抓取网页信息时乱码
//													String charsetDouble = getEncodingByContentStream(aTab);
//													System.out.println("通过联系我们找到的第二层页面的编码====================================:"+charset);
//													String aLink= ClientUtil.get(aTab, true, charsetDouble);
//
//													//如果第一层页面找到了公司名称，对于深层页面就无需再找
//													if(companyName==null || companyName==""){
//														Document docLink = Jsoup.parse(aLink);
//														elements = docLink.getElementsContainingText("有限公司");
//														if(elements.size()>0){
//															companyName = elements.get(elements.size()-1).text();
//															advertise.setCompanyName(companyName);
//														}
//														System.out.println("第二层页面======联系我们====获取的公司名称:"+companyName);
//													}
//													System.out.println("======================联系我们=====第二层页面============================:" + aLink);
//													//判断是否已找到手机号
//													if(mobilePhone==null||mobilePhone==""){
//														//手机号
//														String cellPhone = checkCellphone(aLink);
//														if(cellPhone!=null){
//															advertise.setCellPhone(cellPhone);
//														}
//														System.out.println("第二层页面======联系我们====手机号:"+cellPhone);
//
//													}
//													//判断是否已找到座机号
//													if(phone==null||phone==""){
//														//固定电话
//														String telePhone = checkTelephone(aLink);
//														if(telePhone!=null){
//															advertise.setTelePhone(telePhone);
//														}
//														System.out.println("第二层页面======联系我们====固定电话:"+telePhone);
//													}
//													System.out.println("------------------第2222222222222222层结束--------------------------");
//												}
//											}
//										}
//									}
//
//									/*如果没有找到公司名称并且联系我们也没有，就查找关于我们*/
//									if(companyName==""||mobilePhone==null||phone==null){
//										Elements elementsAbout = docWeb.getElementsContainingText("关于我们");
//										String companyNameAbout = "";
//										if(elementsAbout.size()>0){
//											System.out.println("进入第二层=====================寻找关于我们");
//											companyNameAbout = elementsAbout.get(elementsAbout.size()-1).text();
//											String regexPhone = "(.*)(<a)(.*)(关于我们)(.*)";
//											Pattern patternPhone = Pattern.compile(regexPhone);
//											Matcher matcherPhone = patternPhone.matcher(url1);
//											boolean b = matcherPhone.matches();
//											if(b){
//												/*System.out.println("关于我们的==== aaaaaaa:"+matcherPhone.group(3));*/
//												/*patternPhone = Pattern.compile("(.*)(href=\")(.*)(\"target)(.*)");*/
//												patternPhone = Pattern.compile("(.*)(href=\")(.*)(/\")(.*)");
//												matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//												b = matcherPhone.matches();
//												if(b){
//													System.out.println("关于我们页面的a链接:"+matcherPhone.group(3));
//													String charsetDouble = getEncodingByContentStream(matcherPhone.group(3));
//													System.out.println("通过关于我们找到的第二层页面的编码====================================:"+charset);
//													try{
//														String aLink= ClientUtil.get(matcherPhone.group(3), true, charsetDouble);
//														Document docLink = Jsoup.parse(aLink);
//
//														System.out.println("======================关于我们=====第二层页面============================:" + aLink);
//														//如果第一层页面找到了公司名称，对于深层页面就无需再找
//														if(companyName==null || companyName==""){
//															elements = docLink.getElementsContainingText("有限公司");
//															if(elements.size()>0){
//																companyName = elements.get(elements.size()-1).text();
//																advertise.setCompanyName(companyName);
//															}
//															System.out.println("第二层页面======关于我们====获取的公司名称:"+companyName);
//														}
//														//判断是否已找到手机号
//														if(mobilePhone==null||mobilePhone==""){
//															//手机号
//															String cellPhone = checkCellphone(aLink);
//															if(cellPhone!=null){
//																advertise.setCellPhone(cellPhone);
//															}
//															System.out.println("第二层页面======关于我们====手机号:"+cellPhone);
//
//														}
//														//判断是否已找到座机号
//														if(phone==null||phone==""){
//															//固定电话
//															String telePhone = checkTelephone(aLink);
//															if(telePhone!=null){
//																advertise.setTelePhone(telePhone);
//															}
//															System.out.println("第二层页面======关于我们====固定电话:"+telePhone);
//														}
//														System.out.println("------------------第2222222222222222层结束--------------------------");
//													}catch (Exception e){
//														e.printStackTrace();
//													}
//
//												}
//											}
//										}
//									}
//									try{
//										crmAdvertiseServiceImpl.insert(advertise);
//									}catch (Exception e){
//										e.printStackTrace();
//									}
//
//								}
//								System.out.println("------------------第一层结束--------------------------");
//
//							}
//
//						}
//
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					return "ok";
//				}
//				for(Element row:rows.select("div[id~=\\d]")){
//					Element a = row.select("a").get(0);
//					if(StringUtils.isBlank(a.text())){
//						continue;
//					}
//					i+=1;//第几个
//					System.out.println("第"+i+"个");//标题
//					Advertise advertise = new Advertise();
//					advertise.setKeyWord(keyWord);
//					if(row.select(".ec_tuiguang_ppimlink").size()>0 || row.select(".ec_tuiguang_ppouter").size()>0){
//						System.out.print("推广  ");//是否推广
//						advertise.setAdvertiseType("推广");
//					}else{
//						advertise.setAdvertiseType("非推广");
//					}
//					System.out.println(" 百度的url:"+a.attr("href"));//百度的url
//					advertise.setBaiduUrl(a.attr("href"));
//					System.out.println("标题:"+a.text());//标题
//					advertise.setAdvertiseTitle(a.text());
//
//					String regex1 = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+[.][a-z]+";
//					String regex = "[a-zA-z0-9-]+[.][a-zA-z0-9-]+[.][a-z]+";
//					Pattern pattern = Pattern.compile(regex1);
//					Matcher m = pattern.matcher(row.text());
//					if(m.find()){
//						System.out.println("url:"+m.group());//本身的网址
//						advertise.setCompanyUrl(m.group());
//					}else{
//						Pattern pattern2 = Pattern.compile(regex);
//						Matcher m2 = pattern2.matcher(row.text());
//						if(m2.find()){
//							System.out.println("url:"+m2.group());//本身的网址
//							advertise.setCompanyUrl(m2.group());
//						}
//					}
//					//查询页面编码格式，方式抓取网页信息时乱码
//					String charset = getEncodingByContentStream(a.attr("href"));
//					System.out.println("编码====================================:"+charset);
//					//进一步解析网址中的内容，获取所需的电话或者公司名称
//					String url1 = StringUtils.deleteWhitespace(ClientUtil.get(a.attr("href"), true, charset));
//					System.out.println("HTML页面====================================:"+url1);
//					if(url1!=null){
//						//1、找有限公司，取最后一个
//						//找联系我们 或者 关于我们 或者
//						Document docWeb = Jsoup.parse(url1);
//						//第一层查找公司名称
//						Elements elements = docWeb.getElementsContainingText("有限公司");
//						String companyName = "";
//						String mobilePhone ="";
//						String phone ="";
//						if(elements.size()>0){
//							companyName = elements.get(elements.size()-1).text();
//							advertise.setCompanyName(companyName);
//						}
//						//查找座机号
//						phone = checkTelephone(url1);
//						if(phone!=null&&phone!=""){
//							advertise.setTelePhone(phone);
//						}
//						//查找手机号
//						mobilePhone = checkCellphone(url1);
//						if(mobilePhone!=null&&mobilePhone!=""){
//							advertise.setCellPhone(mobilePhone);
//						}
//						System.out.println("第一层页面最后一个公司名称:"+companyName);
//						System.out.println("第一层页面手机号:"+mobilePhone);
//						System.out.println("第一层页面座机号:"+phone);
//						if(companyName==""||mobilePhone==null||phone==null){
//							//接着进行深层次抓取，通过联系我们或关于我们来查找公司名称和手机号
//							elements = docWeb.getElementsContainingText("联系我们");
//							String companyName2 = "";
//							if(elements.size()>0){
//								System.out.println("进入第二层=====================寻找联系我们");
//								companyName2 = elements.get(elements.size()-1).text();
//								String regexPhone = "(.*)(<a)(.*)(联系我们)(.*)";
//								Pattern patternPhone = Pattern.compile(regexPhone);
//								Matcher matcherPhone = patternPhone.matcher(url1);
//								boolean b = matcherPhone.matches();
//								if(b){
//									/*System.out.println("联系我们页面中的==== aaaaaa:"+matcherPhone.group(3));*/
//									regexPhone = "(.*)(href=\")(.*)(shtml)(\")(.*)";
//									patternPhone = Pattern.compile(regexPhone);
//									matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//									if(matcherPhone.matches()){
//										String aTab = matcherPhone.group(3)+"shtml";
//										System.out.println("联系我们页面的a链接:"+aTab);
//										//获取联系我们的a链接后
//										//查询页面编码格式，方式抓取网页信息时乱码
//										String charsetDouble = getEncodingByContentStream(aTab);
//										System.out.println("通过联系我们找到的第二层页面的编码====================================:"+charset);
//										String aLink= ClientUtil.get(aTab, true, charsetDouble);
//
//										//如果第一层页面找到了公司名称，对于深层页面就无需再找
//										if(companyName==null || companyName==""){
//											Document docLink = Jsoup.parse(aLink);
//											elements = docLink.getElementsContainingText("有限公司");
//											if(elements.size()>0){
//												companyName = elements.get(elements.size()-1).text();
//												advertise.setCompanyName(companyName);
//											}
//											System.out.println("第二层页面======联系我们====获取的公司名称:"+companyName);
//										}
//										System.out.println("======================联系我们=====第二层页面============================:" + aLink);
//										//判断是否已找到手机号
//										if(mobilePhone==null||mobilePhone==""){
//											//手机号
//											String cellPhone = checkCellphone(aLink);
//											if(cellPhone!=null){
//												advertise.setCellPhone(cellPhone);
//											}
//											System.out.println("第二层页面======联系我们====手机号:"+cellPhone);
//
//										}
//										//判断是否已找到座机号
//										if(phone==null||phone==""){
//											//固定电话
//											String telePhone = checkTelephone(aLink);
//											if(telePhone!=null){
//												advertise.setTelePhone(telePhone);
//											}
//											System.out.println("第二层页面======联系我们====固定电话:"+telePhone);
//										}
//										System.out.println("------------------第2222222222222222层结束--------------------------");
//									}
//								}
//							}
//						}
//
//						/*如果没有找到公司名称并且联系我们也没有，就查找关于我们*/
//						if(companyName==""||mobilePhone==null||phone==null){
//							Elements elementsAbout = docWeb.getElementsContainingText("关于我们");
//							String companyNameAbout = "";
//							if(elementsAbout.size()>0){
//								System.out.println("进入第二层=====================寻找关于我们");
//								companyNameAbout = elementsAbout.get(elementsAbout.size()-1).text();
//								String regexPhone = "(.*)(<a)(.*)(关于我们)(.*)";
//								Pattern patternPhone = Pattern.compile(regexPhone);
//								Matcher matcherPhone = patternPhone.matcher(url1);
//								boolean b = matcherPhone.matches();
//								if(b){
//									/*System.out.println("关于我们的==== aaaaaaa:"+matcherPhone.group(3));*/
//									/*patternPhone = Pattern.compile("(.*)(href=\")(.*)(\"target)(.*)");*/
//									patternPhone = Pattern.compile("(.*)(href=\")(.*)(/\")(.*)");
//									matcherPhone = patternPhone.matcher(matcherPhone.group(3));
//									b = matcherPhone.matches();
//									if(b){
//										System.out.println("关于我们页面的a链接:"+matcherPhone.group(3));
//										String charsetDouble = getEncodingByContentStream(matcherPhone.group(3));
//										System.out.println("通过关于我们找到的第二层页面的编码====================================:"+charset);
//										try{
//											String aLink= ClientUtil.get(matcherPhone.group(3), true, charsetDouble);
//											Document docLink = Jsoup.parse(aLink);
//
//											System.out.println("======================关于我们=====第二层页面============================:" + aLink);
//											//如果第一层页面找到了公司名称，对于深层页面就无需再找
//											if(companyName==null || companyName==""){
//												elements = docLink.getElementsContainingText("有限公司");
//												if(elements.size()>0){
//													companyName = elements.get(elements.size()-1).text();
//													advertise.setCompanyName(companyName);
//												}
//												System.out.println("第二层页面======关于我们====获取的公司名称:"+companyName);
//											}
//											//判断是否已找到手机号
//											if(mobilePhone==null||mobilePhone==""){
//												//手机号
//												String cellPhone = checkCellphone(aLink);
//												if(cellPhone!=null){
//													advertise.setCellPhone(cellPhone);
//												}
//												System.out.println("第二层页面======关于我们====手机号:"+cellPhone);
//
//											}
//											//判断是否已找到座机号
//											if(phone==null||phone==""){
//												//固定电话
//												String telePhone = checkTelephone(aLink);
//												if(telePhone!=null){
//													advertise.setTelePhone(telePhone);
//												}
//												System.out.println("第二层页面======关于我们====固定电话:"+telePhone);
//											}
//											System.out.println("------------------第2222222222222222层结束--------------------------");
//										}catch (Exception e){
//											e.printStackTrace();
//										}
//
//									}
//								}
//							}
//						}
//						try{
//							crmAdvertiseServiceImpl.insert(advertise);
//						}catch (Exception e){
//							e.printStackTrace();
//						}
//
//					}
//					System.out.println("------------------第一层结束--------------------------");
//
//				}
//
//			}
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return "ok";
//	}
//
//	@RequestMapping(value = "/exportChanceLevelList")
//	public ModelAndView exportAdvertiselList(Model model, ChanceVo chance) {
//		ViewFiles excel = new ViewFiles();
//		HSSFWorkbook wb = this.crmAdvertiseServiceImpl.exportStaticChanceLevel();
//		Map map = new HashMap();
//		map.put("workbook", wb);
//		map.put("fileName", "百度资源信息统计.xls");
//		return new ModelAndView(excel, map);
//	}
//
//	public static void main(String args[]){
//		/*pool1();*/
//	}
//}