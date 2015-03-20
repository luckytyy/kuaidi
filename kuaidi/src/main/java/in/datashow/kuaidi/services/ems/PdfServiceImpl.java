package in.datashow.kuaidi.services.ems;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import in.datashow.kuaidi.Order;
import in.datashow.kuaidi.OrderFreemarkerObj;
import in.datashow.kuaidi.pdf.PDFTmplConfiguration;
import in.datashow.kuaidi.pdf.PdfGenerator;
import in.datashow.kuaidi.services.PdfService;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class PdfServiceImpl implements PdfService {

	public void generatePdf(Order info, Order detail, OutputStream os) throws Exception {
		Map<String, Object> resMap = new HashMap<String, Object>();
		Configuration config = PDFTmplConfiguration.getConfiguation();
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrderFreemarkerObj ofo = null;
		
		ofo = new OrderFreemarkerObj();
		ofo.setInfo(info);
		ofo.setDetail(detail);
		ofo.setOrderNum(info.getOrderNum());
		
		map.put("type", "OrderFreemarkerObj");
		map.put("object",ofo);

		String template = null;
		template = "EMSQuery.ftl";
		Template tp = null;
		String htmlStr = null;
		try {
			tp = config.getTemplate(template, "UTF-8");
			Writer stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			tp.setEncoding("UTF-8");
			tp.process(map, writer);
			htmlStr = stringWriter.toString();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		InputStream is = null;
		// ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfGenerator.generate(htmlStr, os);
		// is = new ByteArrayInputStream(os.toByteArray());

		// 下面这部分 用于下载打包的 zip 报告
		// List<EnteringAntenatalSample> sampleList = new
		// ArrayList<EnteringAntenatalSample>();
		// sampleList.add(eas);
		// sampleList.add(eas);
		// ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// ZipUtil.createZipFile(sampleList,bos);
		// is = new ByteArrayInputStream(bos.toByteArray());

		os.flush();
		os.close();

	}
}