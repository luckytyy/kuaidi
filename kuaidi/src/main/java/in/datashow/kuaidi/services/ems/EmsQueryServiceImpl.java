package in.datashow.kuaidi.services.ems;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.Order.OrderRecord;
import in.datashow.kuaidi.services.QueryService;
import in.datashow.kuaidi.util.CommonParametersGetter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;

/**
 * http://datashow.in
 * 
 * @author yangyan
 * 
 */
public class EmsQueryServiceImpl implements QueryService {
	
	
	public static final String CHECKCODE_URL  ="http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_drawValidCode.action";
//	public static final String CHECKCODE_REFER_URL="http://yjcx.chinapost.com.cn/zdxt/jsp/zhdd/gjyjgzcx/gjyjqcgzcx/gjyjqcgzcx.jsp";
	public static final String ORGI_URL = "http://yjcx.chinapost.com.cn"; // 初始的HTML页面
	public static final String QUERY_PAGE_URL ="http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_gjyjqcgzcxDqztQueryPage.action";
	
	public static final String QUERY_PAGE_URL1="http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_gjyjqcgzcxLzxxQueryPage.action";
	public static final String REFER_URL      ="http://yjcx.chinapost.com.cn/zdxt/jsp/zhdd/gjyjgzcx/gjyjqcgzcx/gjyjqcgzcx.jsp";
	public static final String USER_AGENT = CommonParametersGetter.getRandomUserAgents() ;
	
	public static final String REFER_NEW_URL ="http://yjcx.chinapost.com.cn/zdxt/jsp/zhdd/gjyjgzcx/gjyjqcgzcx/yjqclz.jsp?vYjhm=XA24435342142&fromFlag=0&gngjFlag=0";
	
	public String queryHTML(String orderNum,Map<String, String> cookies) {
		Document document = null;
		try {
			document = Jsoup
					.connect(ORGI_URL)
					.referrer(REFER_URL)
					.userAgent(USER_AGENT)
					.cookies(cookies)
					.timeout(10000).post();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return document.html();
	}
	
	public Order query(String orderNum,String captchaCode, Map<String, String> cookies) {
		Document document = null;
		String data1 = getShortQuery(orderNum, captchaCode);
		try {
			document = Jsoup
					.connect(QUERY_PAGE_URL)
					.referrer(REFER_NEW_URL)
					.userAgent(USER_AGENT)
					.cookies(cookies)
					.data("submitType", "2")
					.data("ajax", data1)
//					.data("vYjhm",orderNum)
//					.data("fromFlag","0")
//					.data("gngjFlag","0")
					.timeout(10000).post();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		String count = document.select("data").select("count").text();
		if(count !=null && count !=""){
			Integer s = 0;
			try{
				s = Integer.parseInt(count.trim());
			}catch (Exception e) {
				s = 0;
			}
			
			if(s<=0){
				System.err.println(new Date()+"没有查到"+orderNum+"的概要记录！");
				return new Order();
			}
		}
		Elements rows = document.select("data").select("rdata");
		Order o = new Order(orderNum);
		Iterator<Element> row2 = rows.iterator();
		boolean ok = false;
		
		while (row2.hasNext()) {
			Element e = row2.next();
			// 时间
			Elements td_time = e.select("d_sjsj");
			// address
			Elements td_address = e.select("V_JDJMC");
			// status
			Elements td_num = e.select("V_ZT");

			String dateStr = td_time.text();
			String address = td_address.text();
			String status = td_num.text();
			Date date = null;
			try {
				date = DateUtils.parseDate(dateStr.replaceAll("   ", " ").trim(), new String[] { "yyyy-MM-dd HH:mm:ss.SSS" });
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			OrderRecord r1 = new OrderRecord(date, address, status);
			o.addRecords(r1);
//			System.out.println(ToStringBuilder.reflectionToString(r1));
		}
//		if (ok) {
//			return o;
//		} else {
//			System.out.println("没有记录");
//		}
		return o;
	}
	
	
	public Order queryDetail(String orderNum, String captchaCode,
			Map<String, String> cookies) {
		Document document = null;
		String data1 = getLongQuery(orderNum, captchaCode);
		try {
			document = Jsoup
					.connect(QUERY_PAGE_URL1)
					.referrer(REFER_NEW_URL)
					.userAgent(USER_AGENT)
					.cookies(cookies)
					.data("submitType", "1")
					.data("ajax", data1)
//					.data("vYjhm",orderNum)
//					.data("fromFlag","0")
//					.data("gngjFlag","0")
					.timeout(10000).post();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		String count = document.select("data").select("count").text();
		if(count !=null && count !=""){
			Integer s = Integer.parseInt(count.trim());
			if(s<=0){
				System.err.println(new Date()+"没有查到"+orderNum+"的详细记录！");
				return null;
			}
		}
		Elements rows = document.select("data").select("rdata");
		Order o = new Order(orderNum);
		Iterator<Element> row2 = rows.iterator();
		boolean ok = false;
		
		while (row2.hasNext()) {
			Element e = row2.next();
			// 时间
			Elements td_time = e.select("d_sjsj");
			// address
			Elements td_address = e.select("v_zt");
			// 编号
			Elements td_num = e.select("n_xh");

			String dateStr = td_time.text();
			String address = td_address.text();
			String status = td_num.text();
			Date date = null;
			try {
				date = DateUtils.parseDate(dateStr.replaceAll("   ", " ").trim(), new String[] { "yyyy-MM-dd HH:mm:ss.SSS" });
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			

			OrderRecord r1 = new OrderRecord(date, address, status);
			o.addRecords(r1);

//			System.out.println(ToStringBuilder.reflectionToString(r1));
		}
//		if (ok) {
//			return o;
//		} else {
//			System.out.println("没有记录");
//		}
		return o;
	}

	private String getShortQuery(String orderNum, String captchaCode) {
		String data1 ="<?xml version=\"1.0\" encoding=\"utf-8\"?><root><params><param><key>vYjhm</key><value>"+orderNum+"</value>"
				+ "</param><param><key>vYjhmLst</key><value>'"+orderNum+"'</value></param><param><key>validcode</key><value>"+captchaCode+"</value>"
				+ "</param></params><data><criteria><_entity></_entity></criteria><page><begin>0</begin><length>100</length>"
				+ "<count>-1</count><isCount>true</isCount></page></data></root>";
		return data1;
	}
	
	private String getLongQuery(String orderNum, String captchaCode) {
		String data1 ="<?xml version=\"1.0\" encoding=\"utf-8\"?><root><params><param><key>vYjhm</key>"
				+ "<value>"+orderNum+"</value></param><param><key>FROM_FLAG</key><value>0</value></param>"
				+ "<param><key>gngjFlag</key><value>0</value></param></params><data></data></root>";
		return data1;
	}

	public CaptchaEntity captcha(String orderNum) {

		Response resp = null;
		try {
			resp = Jsoup
					.connect(CHECKCODE_URL+"?rand=" + RandomUtils.nextDouble())
					.referrer(REFER_URL)
					.userAgent(USER_AGENT)
					.timeout(10000).execute();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		Map<String, String> cookies = resp.cookies();
		resp.contentType();
		byte[] image = resp.bodyAsBytes();
		CaptchaEntity captchaEntity = new CaptchaEntity();
		captchaEntity.setCookies(cookies);
		captchaEntity.setImage(image);
		return captchaEntity;
	}
	
	public static void main(String[] args) {
	}
}
