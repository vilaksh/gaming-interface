package utilityObjects;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger 
{	
	private static Throwable _stack = new Throwable();
	private static StackTraceElement[] _stack_elements;	
	
	private static void _writeToOutput(String m)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(_time_format);
		System.out.print(dateFormat.format(Calendar.getInstance().getTime()));
		System.out.print(m + "\n");
	}
	
	private static void _log(String m)
	{
		_writeToOutput(m);
	}
	
	public static Integer logLevel = Constants.LOGGING_VERBOSE; 
	public static String _time_format = "H:mm:ss:SSS";
	
	public static void LogVerbose(String m)
	{
		_stack_elements = _stack.getStackTrace();
		if(logLevel >= Constants.LOGGING_VERBOSE)
			_log("[VERBOSE][" + _stack_elements[1].getClassName() + "::"
					+ _stack_elements[1].getMethodName() + "]" + m);
	}
	
	public static void LogMessage(String m)
	{
		_stack_elements = _stack.getStackTrace();
		if(logLevel >= Constants.LOGGING_INFO)
			_log("[INFO][" + _stack_elements[1].getClassName() + "::"
					+ _stack_elements[1].getMethodName() + "]" + m);
	}
	
	public static void LogWarning(String m)
	{
		_stack_elements = _stack.getStackTrace();
		if(logLevel >= Constants.LOGGING_WARNING)
			_log("[WARNING][" + _stack_elements[1].getClassName() + "::"
					+ _stack_elements[1].getMethodName() + "]" + m);
	}
	
	public static void LogError(String m)
	{
		_stack_elements = _stack.getStackTrace();
		if(logLevel >= Constants.LOGGING_ERROR)
			_log("[ERROR][" + _stack_elements[1].getClassName() + "::"
					+ _stack_elements[1].getMethodName() + "]" + m);
	}
}