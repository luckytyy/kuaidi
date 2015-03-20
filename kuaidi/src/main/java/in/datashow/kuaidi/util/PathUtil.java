package in.datashow.kuaidi.util;

import in.datashow.kuaidi.pdf.PDFTmplConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**  
 * Title:PathUtil.java
 * Description： 
 * Author:tanyuanyi
 * Date:2013-4-12 下午06:20:56
 */
public class PathUtil {

	/**
	 *  获取到项目类的 跟绝对路径
	 *  如  /website/webApplications/HIMS_Agcy3/ROOT/WEB-INF/classes/
	* @return
	 */
	public static String getAbsoluteClassPath(){
//		
//		String result = "";  
//		   
//	    ClassLoader classLoader = PathUtil.class.getClassLoader();  
//	    
//	    System.out.println(classLoader.getSystemResource(""));
//	    System.out.println(classLoader.getSystemResource("/"));
//	    System.out.println(classLoader.getSystemResource("simsun.ttc"));
//	    
//	    System.out.println(classLoader.getResource(""));
//	    System.out.println(classLoader.getResource("/"));
//	    System.out.println(classLoader.getResource("simsun.ttc"));
//	    
//	    try {  
//	    	System.out.println("system_resource = "+classLoader.getSystemResource("simsun.ttc"));;
//	        result = classLoader.getResource("simsun.ttc").getPath();
//	        
//	        try {
//				File f = new File("jar:file:/F:/kuaidi/target/EmsQuery-jar-with-dependencies.jar!/simsun.ttc");
//				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//				System.out.println("))))))))))))))))"+br.readLine());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	        
//	        result = result.replaceAll("simsun.ttc", "")
//	        .replaceAll("file:", "");  
//	        System.out.println("result = "+result);
//	    } catch (Exception e) {  
//	        e.printStackTrace();  
//	    }  
//	   
//	    return result;  
		
//		System.out.println("****** = "+PathUtil.class.getClassLoader().getResource("")+" *******");
//		String rootDir = PDFTmplConfiguration.class.getClassLoader().getResource("").getPath();
//		String osName = System.getProperty("os.name");
////		if(osName!=null && osName.toLowerCase().contains("window")){
////			rootDir = rootDir.substring(1, rootDir.length());     // 对于windows环境下，去掉前面多余的斜杠
////		}
//		return rootDir;
		return System.getProperty("user.dir");
	}
	
	public static String getResourceDir(){
		return System.getProperty("user.dir")+"/config";
	}
	
	/**
	 *  获取到项目的 跟绝对路径
	 *  如  /website/webApplications/HIMS_Agcy3
	* @return
	 */
	public static String getAbsoluteProjectPath(){
		String rootDir = PDFTmplConfiguration.class.getClassLoader().getResource("").getPath();
		String osName = System.getProperty("os.name");
		rootDir = rootDir.replaceAll("/WEB-INF/classes","");
		return rootDir;
	}
}
