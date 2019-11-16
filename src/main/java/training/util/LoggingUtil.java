package training.util;

import org.slf4j.Logger;

public class LoggingUtil {

	private static String LoggingLogic(Exception e) {
		StringBuilder build = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		for(StackTraceElement traceTemp : trace) {
			build.append("\n");
			build.append(traceTemp.toString());
		}
		return build.toString();
	}
	public static void LoggingMethod(Logger logger, Exception e){
		logger.error(e.getMessage());
		logger.error(LoggingLogic(e));
	}
}
