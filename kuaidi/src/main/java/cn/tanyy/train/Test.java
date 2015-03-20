package cn.tanyy.train;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.TestEnv;
import in.datashow.kuaidi.services.PdfService;
import in.datashow.kuaidi.services.QueryService;
import in.datashow.kuaidi.services.ems.PdfServiceImpl;
import in.datashow.kuaidi.services.ems.TrainQueryServiceImpl;

import java.io.File;

import org.apache.commons.io.FileUtils;

/**
 * EMS 测试
 * 
 * @author YY
 * 
 */
public class Test {
	public static void main(String[] args) {
		TrainQueryServiceImpl q = new TrainQueryServiceImpl();
		
		String orderNum = "XA24435337842";
		CaptchaEntity en = q.captcha(orderNum);
		
		if (en != null) {
			String timeName = System.currentTimeMillis()+"";
			File f = null;
			timeName = "a";
			try {
				f = new File(TestEnv.TEMP_FILE_DIRECTORY, timeName + ".png");
				//  好像可以直接不用验证码即可查询,所以这里不下载验证码了
//				FileUtils.writeByteArrayToFile(f, en.getImage());
				q.uploadBaiduImg(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
