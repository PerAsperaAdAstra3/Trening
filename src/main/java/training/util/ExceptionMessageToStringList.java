package training.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

public class ExceptionMessageToStringList {
	public static List<String> createErrorMessageListForPrinting(Exception e) {
		List<String> messageList = new ArrayList<>();
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i < trace.length; i++ ) {
			messageList.add(trace[i].toString());
		}
		return messageList;
	}
}
