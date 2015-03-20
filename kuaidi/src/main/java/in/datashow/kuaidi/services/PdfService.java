package in.datashow.kuaidi.services;

import in.datashow.kuaidi.Order;

import java.io.OutputStream;

/**  
 * Description： 
 * Author:lucktyy@gmail.com
 * Date:2015-3-9下午05:06:57
 */
public interface PdfService {

	public void generatePdf(Order info,Order detail,OutputStream os) throws Exception;
	
}
