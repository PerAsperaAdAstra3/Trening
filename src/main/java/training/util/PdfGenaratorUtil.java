package training.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
    Logger logger = LoggerFactory.getLogger(PdfGenaratorUtil.class);
	
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
		  String filePath = "";
		  String fileName = UUID.randomUUID().toString();
	        try {

	        	String home = System.getProperty("user.home");
	        	
	        	String clientFolder = map.get("name").toString(); //TODO Make this configurable in the future.
	        	java.nio.file.Path pathTest = java.nio.file.Paths.get(home, "treninzi", clientFolder);
	        	
	        	if (Files.notExists(pathTest)) {
	        		File directory = new File(pathTest.toString());
	        	    if (! directory.exists()){
	        	        directory.mkdirs();
	        	    }
	        	}
	        	
	        	String fileNameString = map.get("name").toString()+" " +map.get("trainingNumber").toString() + ".pdf";
	        	java.nio.file.Path path = java.nio.file.Paths.get(pathTest.toString(), fileNameString);
	        	File outputFile = new File(path.toString());
	        	
	        	boolean exists = outputFile.exists();
	        	os = new FileOutputStream(outputFile);
	        	filePath = path.toString();
	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(processedHtml);
	            renderer.layout();
	            renderer.createPDF(os, false);
	            renderer.finishPDF();
	        }catch (IOException e) {
				LoggingUtil.LoggingMethod(logger, e);
	            isThereError = 1;
	        }
	        finally {
	            if (os != null) {
	                try {
	                    os.close();
	      	          ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "explorer " + filePath);
	      	          processBuilder.start();
	                } catch (IOException e) {
	        			LoggingUtil.LoggingMethod(logger, e);
	        		}
	            }
	        }
	   return isThereError;
	}
	
	public int clientReportPdf(String templateName, Map map) throws Exception {
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
		  String filePath = "";
		  String fileName = UUID.randomUUID().toString();
		  File outputFile = null;
	        try {

	        	String home = System.getProperty("user.home");
	        	
	        	String clientFolder = map.get("name").toString(); //TODO Make this configurable in the future.
	        	java.nio.file.Path pathTest = java.nio.file.Paths.get(home, "reports", clientFolder);
	        	
	        	if (Files.notExists(pathTest)) {
	        		File directory = new File(pathTest.toString());
	        	    if (! directory.exists()){
	        	        directory.mkdirs();
	        	    }
	        	}
	        	
	        	String fileNameString = map.get("name").toString()+" " +map.get("startDate") +" " +map.get("endDate");
	        	java.nio.file.Path path = java.nio.file.Paths.get(pathTest.toString(), fileNameString);
	       	    outputFile = File.createTempFile(fileNameString, ".pdf");
	        	
	        	boolean exists = outputFile.exists();
	        	os = new FileOutputStream(outputFile);
	        	
	        	filePath = outputFile.getAbsolutePath();
	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(processedHtml);
	            renderer.layout();
	            renderer.createPDF(os, false);
	            renderer.finishPDF();
	        }catch (IOException e) {
				LoggingUtil.LoggingMethod(logger, e);
	            isThereError = 1;
	        }
	        finally {
	            if (os != null) {
	                try {
	                    os.close();
	      	          ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/C", "explorer " + filePath);
	      	          processBuilder.start();
	                } catch (IOException e) {
	        			LoggingUtil.LoggingMethod(logger, e);
	        		}
	            }
	        }
	        if(outputFile != null) {
	            outputFile.deleteOnExit();
	        }
	   return isThereError;
	}
}