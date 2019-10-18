package training.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllerRest {
	/*
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/hello")
	public String sayHello() {
		System.out.println("Hello World - RestController!");
	//	return "{\"message\": \"Hello, World!\"}";
		return "{message: Hello, World!}";
	}*/
	

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/hello")
	public String sayHelloJSON() {
			JSONObject obj = new JSONObject();
		obj.put("message", "Hello World! JSON");
	//	obj.put("first", "Here we go again");
		obj.put("first", "BLA BLA BLA");
		System.out.println("Hello World - RestController! JSON 16.06.2019 ");
	//	return "{\"message\": \"Hello, World!\"}";
		return obj.toString(); //"{message: Hello, World!}";

	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/helloarray")
	public String angularSubmit() {
		
		JSONObject obj = new JSONObject();
		String names[] = {"PrvoIme","DrugoIme","TreceIme"};
		obj.put("names", names);
		
		return obj.toString();
	}
}
