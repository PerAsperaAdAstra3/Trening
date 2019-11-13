package training.util;

public class LoggingUtil {

	public static String LoggingMethod(Exception e) {
		StringBuilder build = new StringBuilder();
		StackTraceElement[] trace = e.getStackTrace();
		for(StackTraceElement traceTemp : trace) {
			build.append("\n");
			build.append(traceTemp.toString());
		}
		return build.toString();
	}
}