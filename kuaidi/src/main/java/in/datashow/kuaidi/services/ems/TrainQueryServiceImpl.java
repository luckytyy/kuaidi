package in.datashow.kuaidi.services.ems;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.Order.OrderRecord;
import in.datashow.kuaidi.TestEnv;
import in.datashow.kuaidi.services.QueryService;
import in.datashow.kuaidi.util.CommonParametersGetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.tanyy.train.HTTPCommonUtil;

import com.alibaba.fastjson.JSON;

/**
 * http://datashow.in
 * 
 * @author yangyan
 * 
 */
public class TrainQueryServiceImpl implements QueryService {

	public static final String CHECKCODE_URL = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew";
	public static final String CHECKCODE_REFER_URL = "https://kyfw.12306.cn/otn/login/init";

	public static final String BAIDU_UPLOAD_URL = "http://shitu.baidu.com/n/image?fr=html5&needRawImageUrl=true&id=WU_FILE_0";
	public static final String BAIDU_UPLOAD_REFER_URL = "http://shitu.baidu.com/";

	public static final String REFER_URL = "https://kyfw.12306.cn/otn/login/init";
	public static final String ORGI_URL = "http://yjcx.chinapost.com.cn"; // 初始的HTML页面
	public static final String QUERY_PAGE_URL = "http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_gjyjqcgzcxDqztQueryPage.action";
	public static final String QUERY_PAGE_URL1 = "http://yjcx.chinapost.com.cn/zdxt/gjyjqcgzcx/gjyjqcgzcx_gjyjqcgzcxLzxxQueryPage.action";
	public static final String USER_AGENT = CommonParametersGetter
			.getRandomUserAgents();
	public static final String REFER_NEW_URL = "http://yjcx.chinapost.com.cn/zdxt/jsp/zhdd/gjyjgzcx/gjyjqcgzcx/yjqclz.jsp?vYjhm=XA24435342142&fromFlag=0&gngjFlag=0";

	public String queryHTML(String orderNum, Map<String, String> cookies) {
		Document document = null;
		try {
			document = Jsoup.connect(ORGI_URL).referrer(REFER_URL)
					.userAgent(USER_AGENT).cookies(cookies).timeout(10000)
					.post();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return document.html();
	}

	public Order query(String orderNum, String captchaCode,
			Map<String, String> cookies) {
		Document document = null;
		String data1 = getShortQuery(orderNum, captchaCode);
		try {
			document = Jsoup.connect(QUERY_PAGE_URL).referrer(REFER_NEW_URL)
					.userAgent(USER_AGENT).cookies(cookies)
					.data("submitType", "2").data("ajax", data1)
					// .data("vYjhm",orderNum)
					// .data("fromFlag","0")
					// .data("gngjFlag","0")
					.timeout(10000).post();
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		String count = document.select("data").select("count").text();
		if (count != null && count != "") {
			Integer s = 0;
			try {
				s = Integer.parseInt(count.trim());
			} catch (Exception e) {
				s = 0;
			}

			if (s <= 0) {
				System.err.println(new Date() + "没有查到" + orderNum + "的概要记录！");
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
				date = DateUtils.parseDate(dateStr.replaceAll("   ", " ")
						.trim(), new String[] { "yyyy-MM-dd HH:mm:ss.SSS" });
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			OrderRecord r1 = new OrderRecord(date, address, status);
			o.addRecords(r1);
			// System.out.println(ToStringBuilder.reflectionToString(r1));
		}
		// if (ok) {
		// return o;
		// } else {
		// System.out.println("没有记录");
		// }
		return o;
	}

	public Order queryDetail(String orderNum, String captchaCode,
			Map<String, String> cookies) {
		return null;
	}

	private String getShortQuery(String orderNum, String captchaCode) {
		String data1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><params><param><key>vYjhm</key><value>"
				+ orderNum
				+ "</value>"
				+ "</param><param><key>vYjhmLst</key><value>'"
				+ orderNum
				+ "'</value></param><param><key>validcode</key><value>"
				+ captchaCode
				+ "</value>"
				+ "</param></params><data><criteria><_entity></_entity></criteria><page><begin>0</begin><length>100</length>"
				+ "<count>-1</count><isCount>true</isCount></page></data></root>";
		return data1;
	}

	private String getLongQuery(String orderNum, String captchaCode) {
		String data1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><params><param><key>vYjhm</key>"
				+ "<value>"
				+ orderNum
				+ "</value></param><param><key>FROM_FLAG</key><value>0</value></param>"
				+ "<param><key>gngjFlag</key><value>0</value></param></params><data></data></root>";
		return data1;
	}

	public static void uploadBaiduImg(File file) {
		Response resp = null;
		String url = BAIDU_UPLOAD_URL
				+ "&name="
				+ file.getName()
				+ "&type=image%2Fpng&lastModifiedDate="
				+ "Tue+Mar+10+2015+16%3A46%3A29+GMT%2B0800+(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)&size="
				+ file.length();

		FileBody file1 = new FileBody(file);
		try {
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("file", file1);
			System.out.println("url = " + url);
			HttpPost post = new HttpPost(url);
			post.setEntity(entity);
			
			post.addHeader("Accept-Encoding", "gzip,deflate");
			post.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
			post.addHeader("Connection", "keep-alive");
			post.addHeader("Content-Type", "image/png");
			post.addHeader("Content-Encoding", "gzip");
			post.addHeader("Referer", "http://shitu.baidu.com/");
			post.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.104 Safari/537.36");
			post.addHeader("Cookie", "BAIDUID=C53CD23EC3671141A72BCD95C6DBB61A:FG=1; Hm_lvt_ab3198b987623f3b0badd14a0" +
					"8dae181=1426762293,1426762479,1426762589,1426820266; Hm_lpvt_ab3198b987623f3b0badd14a08dae181=1426820266");
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(post);
			System.out.println(response.getEntity().getContentEncoding());
			System.out.println(response.getEntity().getContentType());
			
			InputStream is = response.getEntity().getContent();

//			InputStream is = IOUtils.toInputStream(html,"UTF-8");
			GZIPInputStream gzip = new GZIPInputStream(is);
			StringBuffer out = new StringBuffer();
			byte[] b = new byte[4096];
			for (int n; (n = gzip.read(b)) != -1;) {
				out.append(new String(b, 0, n));
			}
			System.out.println(out);

			// System.out.println("html decode = "+new
			// String(html.getBytes("iso8859-1"),"utf-8"));

			// System.out.println("html "+JSON.toJSONString(response.getEntity(),true)
			// );
			System.out.println("html "
					+ JSON.toJSONString(response.getParams(), true));
			System.out.println("html "
					+ JSON.toJSONString(response.getProtocolVersion(), true));
			System.out.println("html "
					+ JSON.toJSONString(response.getStatusLine(), true));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (org.apache.http.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 下载验证码图片
	public CaptchaEntity captcha(String orderNum) {
		HTTPCommonUtil.trustEveryone();

		Response resp = null;
		try {
			resp = Jsoup
					.connect(
							CHECKCODE_URL + "?module=login&rand=sjrand&"
									+ RandomUtils.nextDouble())
					.referrer(CHECKCODE_REFER_URL).userAgent(USER_AGENT)
					.header("Accept-Encoding", "gzip,deflate,sdch")
					.header("Connection", "close").timeout(10000).execute();
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

		TrainQueryServiceImpl q = new TrainQueryServiceImpl();

		String orderNum = "XA24435337842";
		CaptchaEntity en = q.captcha(orderNum);

		if (en != null) {
			String timeName = System.currentTimeMillis() + "";
			File f = null;
			timeName = "a";
			try {
				f = new File(TestEnv.TEMP_FILE_DIRECTORY, timeName + ".png");
				// 好像可以直接不用验证码即可查询,所以这里不下载验证码了
				// FileUtils.writeByteArrayToFile(f, en.getImage());
				q.uploadBaiduImg(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
