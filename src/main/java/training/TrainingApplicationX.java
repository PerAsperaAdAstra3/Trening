package training;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
@SpringBootApplication
public class TrainingApplicationX extends SpringBootServletInitializer {
		
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TrainingApplicationX.class) ;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TrainingApplicationX.class, args);
		openHomePage();
	}
	
	private static void openHomePage() {
	      try {
		      Runtime rt = Runtime.getRuntime();
	          rt.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/login");
	      } catch(IOException e) {
	    	  e.printStackTrace();
	      }
	 }
}
