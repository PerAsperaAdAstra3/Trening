package training.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Component
public class PdfGenaratorUtil {
	@Autowired
	private TemplateEngine templateEngine;
	public int createPdf(String templateName, Map map) throws Exception {
		Assert.notNull(templateName, "The templateName can not be null");
		int isThereError = 0;
		Context ctx = new Context();
		if (map != null) {
		     Iterator itMap = map.entrySet().iterator();
		       while (itMap.hasNext()) {
			  Map.Entry pair = (Map.Entry) itMap.next();
		          ctx.setVariable(pair.getKey().toString(), pair.getValue());
			}
		}
		
		String processedHtml = templateEngine.process(templateName, ctx);
		  FileOutputStream os = null;
		  String fileName = UUID.randomUUID().toString();
	        try {

	        	String home = System.getProperty("user.home");
	        	
	        	String clientFolder = map.get("name").toString(); //TODO Make this configurable in the future.
	        	java.nio.file.Path pathTest = java.nio.file.Paths.get(home, "treninzi", clientFolder);
	        	
	        	if (Files.notExists(pathTest)) {
	        		File directory = new File(pathTest.toString());
	        	    if (! directory.exists()){
	        	        directory.mkdir();
	        	    }
	        	}
	        	
	        	String fileNameString = map.get("name").toString()+" " +map.get("trainingNumber").toString() + ".pdf";
	        	java.nio.file.Path path = java.nio.file.Paths.get(pathTest.toString(), fileNameString);
	        	File outputFile = new File(path.toString());
	        	os = new FileOutputStream(outputFile);

	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(processedHtml);
	            renderer.layout();
	            renderer.createPDF(os, false);
	            renderer.finishPDF();
	        }catch (IOException e) {
	           isThereError = 1;
	        }
	        finally {
	            if (os != null) {
	                try {
	                    os.close();
	                } catch (IOException e) { /*ignore*/ }
	            }
	        }
	   return isThereError;
	}
}