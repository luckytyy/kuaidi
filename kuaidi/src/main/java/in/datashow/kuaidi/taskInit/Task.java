package in.datashow.kuaidi.taskInit;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.services.PdfService;
import in.datashow.kuaidi.services.QueryService;
import in.datashow.kuaidi.services.ems.EmsQueryServiceImpl;
import in.datashow.kuaidi.services.ems.PdfServiceImpl;
import in.datashow.kuaidi.util.DateUtil;
import in.datashow.kuaidi.util.PdfOperate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**  
 * Description： 
 * Author:lucktyy@gmail.com
 * Date:2015-3-11下午02:46:00
 */
public class Task {

	public static List<String> orderNums = new ArrayList<String>();
	public static List<String> orderNumsSucc = new ArrayList<String>();
	public static List<String> pdfFailedRecords = new ArrayList<String>();
	public static Map<String,Integer> repeatRecords = new HashMap<String,Integer>();
	public static Map<String,String> failedRecords = new HashMap<String,String>();
	
	public static void process(InputStream is,String fatherDir){
		File pdfDir = new File(fatherDir+"/"+getDateStr());
		StringBuilder sb = null;
		if(!pdfDir.exists()){
			pdfDir.mkdir();
		}
		
		try {
//			System.setOut(new PrintStream(new FileOutputStream(pdfDir+"/log.txt")));
			
			sb = new StringBuilder();
			sb.append(DateUtil.convertDateToYYYYMMDDHHMMSS(new Date())+" : 开始处理!").append("\r\n");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			String line = null;
			Map<String,Object> orders = new HashMap<String,Object>();
			while((line = br.readLine())!=null){
				if(StringUtils.isNotEmpty(line) && !line.trim().startsWith("#")){
					line = line.toUpperCase();  // 变成大写的
					line= new String(line.getBytes("ISO-8859-1"),"UTF-8");//字符编码
					
					if(orders.containsKey(line)){
						repeatRecords.put(line,repeatRecords.get(line)+1);
					}else{
						orderNums.add(line);
						orders.put(line, "1");
						repeatRecords.put(line, 1);
					}
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		// 读取完毕
		
		// 开始遍历快递单号，查询快递信息
		QueryService q = new EmsQueryServiceImpl();
		PdfService ps = new PdfServiceImpl();
		
		List<String> pdfPaths = new ArrayList<String>();
		int flag = 0;
		CaptchaEntity en = null;
		for(String orderNum :orderNums){
			
			
			if(flag == 0){
				try{
					en = q.captcha(orderNum);
				}catch (Exception e) {
					System.out.println("获取验证码失败，程序正在准备5秒后再重试！");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						
					}
					try{
						en = q.captcha(orderNum);
					}catch (Exception e1) {
						System.out.println("获取验证码失败，请过几分钟后重新运行，程序即将退出！");
						System.exit(1);
					}
					
				}
				
				flag ++;
			}
			
			Order o = null;
			Order o1 = null;
			
				//  好像可以直接不用验证码即可查询,所以这里不下载验证码了
//			FileUtils.writeByteArrayToFile(new File(TestEnv.TEMP_FILE_DIRECTORY, System.currentTimeMillis() + ".jpg"), en.getImage());
//			Scanner s = new Scanner(System.in);
//			String v = s.nextLine();
			String v = "1234"; // 否则这里做验证码识别工作！
			
			o = q.query(orderNum, v, en.getCookies());
			o1 = q.queryDetail(orderNum, v, en.getCookies());
			
			
			// 如果查取的结果都有值的话，则生成 pdf
			if(o!=null && o1 !=null && o.getRecords().get(0)!=null && o.getRecords().get(0).getDatetime()!=null
					&& o1.getRecords().get(0) !=null && o1.getRecords().get(0).getDatetime()!=null){
				String pdfFile = pdfDir+"/"+orderNum+".pdf";
				
				try{
					OutputStream os = new FileOutputStream(new File(pdfFile));
					ps.generatePdf(o, o1, os);
					
					pdfPaths.add(pdfFile);  // 添加到pdf文件
					sb.append(DateUtil.convertDateToYYYYMMDDHHMMSS(new Date())+" : 生成"+pdfFile+"成功!").append("\r\n");
					orderNumsSucc.add(orderNum);
				}catch (Exception e) {
					File temp = new File(pdfFile);  // 如果发生了异常，则直接删掉这个文件
					if(temp.exists()){
						temp.deleteOnExit();
					}
					pdfFailedRecords.add(orderNum);
				}
			}else{
				failedRecords.put(orderNum,"failed");
			}
			
			try {
				Thread.sleep(1000);      /// 休息一秒钟
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		
		// 合并生成的pdf文件
		if(pdfPaths.size()>0){
			String []a = new String[pdfPaths.size()];
			String []pdfs =(String[]) pdfPaths.toArray(a);
			String totalPdf = pdfDir+"/total.pdf";
			PdfOperate.mergePdfFiles(pdfs,totalPdf);
			sb.append(DateUtil.convertDateToYYYYMMDDHHMMSS(new Date())+" : 合成 total.pdf 成功!").append("\r\n");;
		}
		
		generateEnter(sb,3);
		
		
		
		sb.append("本次查询中，有效单号共有 "+orderNums.size()+"个：").append("\r\n");
		for(String temp:orderNumsSucc){
			sb.append(temp+"、 ");
		}
		
		
		generateEnter(sb,3);
		
		sb.append("重复的记录为：");
		Iterator iter =  null;
		iter = repeatRecords.keySet().iterator();
		while(iter.hasNext()){
			String temp = (String)iter.next();
			Integer count = repeatRecords.get(temp);
			if(count >= 2){
				sb.append(temp+" 出现 "+repeatRecords.get(temp)+" 次 ");
			}
			
		}
		
		generateEnter(sb,3);
		
		iter = failedRecords.keySet().iterator();
		sb.append("查询失败的记录为：");
		while(iter.hasNext()){
			String temp = (String)iter.next();
			sb.append(temp+" ");
		}
		
		generateEnter(sb,3);
		sb.append("生成PDF失败【主要原因是验证码被屏蔽了】的记录共有 "+pdfFailedRecords.size()+"个：").append("\r\n");
		for(String temp:pdfFailedRecords){
			sb.append(temp+"、 ");
		}
		try {
			IOUtils.write(sb.toString(), new FileOutputStream(pdfDir+"/info.txt"),"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void generateEnter(StringBuilder sb ,int j){
		for(int i=0;i<j;i++){
			sb.append("\r\n");
		}
	}
	
	public static String getDateStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		return sdf.format(new Date());
	}
	
	public static void main(String[] args) {
		System.out.println(getDateStr());
	}
}
