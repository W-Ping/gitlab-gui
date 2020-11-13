package utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author lwp
 * @create 2020/09/16
 */
public class ExceptionUtil {
	/**
	 * @param e
	 * @return
	 */
	public final static String getErrorMessage(Exception e) {
		if (null == e) {
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString().replaceAll("[\\t\\n\\r]", "");
	}
}
