package in.datashow.kuaidi.pdf;

import in.datashow.kuaidi.util.PathUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PDFTmplConfiguration {

	private static Configuration config = null;

	/**
	 * Static initialization.
	 * 
	 * Initialize the configuration of Freemarker.
	 */
	static {
		config = new Configuration();
		config.setClassForTemplateLoading(PDFTmplConfiguration.class,
				"template");
//		String templateDir = PathUtil.getAbsoluteClassPath()+"in/datashow/kuaidi/pdf";
		String templateDir = PathUtil.getResourceDir()+"/";
		if(!templateDir.startsWith("/")){
			templateDir = "/" + templateDir;
		}
		templateDir = templateDir;//  +"/resources";  // Maven 的 resources打包之后不是类路径，需要加上这个
		FileTemplateLoader templateLoader = null;
		try {
			templateLoader = new FileTemplateLoader(new File(templateDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.setTemplateLoader(templateLoader);
	}

	public static Configuration getConfiguation() {
		return config;
	}

//	public static void main(String[] args) {
//
//		String template = "EMSQuery_Single.ftl";
//		Configuration config = PDFTmplConfiguration.getConfiguation();
//		Template tp = null;
//		try {
//			tp = config.getTemplate(template, "UTF-8");
//			Writer stringWriter = new StringWriter();
//			BufferedWriter writer = new BufferedWriter(stringWriter);
//			tp.setEncoding("UTF-8");
//			tp.process(new Object(), writer);
//			String htmlStr = stringWriter.toString();
//			writer.flush();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		}
//
//	}
}
