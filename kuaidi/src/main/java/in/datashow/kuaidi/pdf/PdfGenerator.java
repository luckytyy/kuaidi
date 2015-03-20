package in.datashow.kuaidi.pdf;

import in.datashow.kuaidi.util.PathUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import com.itextpdf.text.pdf.BaseFont;

public class PdfGenerator {

	/**
	 * Output a pdf to the specified outputstream
	 * 
	 * @param htmlStr     the htmlstr
	 * @param out         the specified outputstream
	 * @throws Exception
	 */
	public static void generate(String htmlStr, OutputStream out)
			throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
		ITextRenderer renderer = new ITextRenderer();
		
		String baseDir = PathUtil.getResourceDir()+"/";
		// 解决图片引用路径问题
		String tempDir = baseDir;
		if(!tempDir.startsWith("/")){
			tempDir = "file:/"+tempDir;
		}else{
			tempDir = "file:"+tempDir;
		}
		if(!tempDir.endsWith("/")){
			tempDir = tempDir+"/";
		}
		
		// 解决中文不输出问题
		ITextFontResolver fontResolver = renderer.getFontResolver();
		String fontPath = baseDir+"simsun.ttc";
//		String fontPath2 = baseDir+"STXIHEI.TTF";
		fontResolver.addFont(fontPath, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);  
//		fontResolver.addFont(fontPath2, BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);  
		
		ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(renderer.getOutputDevice());
		callback.setSharedContext(renderer.getSharedContext());
		renderer.getSharedContext().setUserAgentCallback(callback);
		renderer.setDocument(doc, null);
		renderer.getSharedContext().setBaseURL(tempDir);
		renderer.layout();
		renderer.createPDF(out); // 这里是有可能发生 503 验证码获取不到的异常的
		
	}

	private static class ResourceLoaderUserAgent extends ITextUserAgent {
		public ResourceLoaderUserAgent(ITextOutputDevice outputDevice) {
			super(outputDevice);
		}

		protected InputStream resolveAndOpenStream(String uri) {
			// uri="file:/"+SystemProperties.getInstance().getProperty("css")+uri.substring(uri.lastIndexOf("/"));
			InputStream is = super.resolveAndOpenStream(uri);
//			System.out.println("IN resolveAndOpenStream() " + uri);
			return is;
		}
	}
}
