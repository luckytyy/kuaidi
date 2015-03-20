package in.datashow.kuaidi.taskInit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;


/**  
 * Description： 
 * Author:lucktyy@gmail.com
 * Date:2015-3-11下午03:24:27
 */
public class Start {

	public static void main(String[] args) {
//		if(args == null || args.length<0 ){
//			System.out.println("请输入有效参数");
//			overTask("退出");
//		}else{
//			System.out.println("**********启动：Start *******************");
			
//			InputStream is = Start.class.getClassLoader().getResourceAsStream(""+args[0]);
			
			try {
				InputStream is = new FileInputStream(System.getProperty("user.dir")+"/keys.txt");
				Task.process(is,System.getProperty("user.dir"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
//		}
	}
	
	private static void overTask(String msg){
		System.out.println(msg);
		System.out.println("---------end--------------");
		System.exit(0);
	}
	
}
