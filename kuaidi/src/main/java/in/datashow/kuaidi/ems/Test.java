package in.datashow.kuaidi.ems;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.services.PdfService;
import in.datashow.kuaidi.services.QueryService;
import in.datashow.kuaidi.services.ems.EmsQueryServiceImpl;
import in.datashow.kuaidi.services.ems.PdfServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.alibaba.fastjson.JSON;

/**
 * EMS 测试
 * 
 * @author YY
 * 
 */
public class Test {
	public static void main(String[] args) {
		QueryService q = new EmsQueryServiceImpl();
		PdfService ps = new PdfServiceImpl();
		
		String orderNum = "XA24435337842";
		CaptchaEntity en = q.captcha(orderNum);
		Order o = null;
		Order o1 = null;
		if (en != null) {
			try {
				//  好像可以直接不用验证码即可查询,所以这里不下载验证码了
//				FileUtils.writeByteArrayToFile(new File(TestEnv.TEMP_FILE_DIRECTORY, System.currentTimeMillis()
//								+ ".jpg"), en.getImage());
//				Scanner s = new Scanner(System.in);
//				String v = s.nextLine();
				String v = "1234";
				o = InitDataSet.createInfo(); //q.query(orderNum, v, en.getCookies());
//				System.out.println(JSON.toJSONString(o,true));
				o1 = InitDataSet.createDetail(); // q.queryDetail(orderNum, v, en.getCookies());
//				System.out.println(JSON.toJSONString(o1,true));
				
				OutputStream os = new FileOutputStream(new File("C:\\a.pdf"));
				ps.generatePdf(o, o1, os);
//				System.out.println(ToStringBuilder.reflectionToString(o1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
