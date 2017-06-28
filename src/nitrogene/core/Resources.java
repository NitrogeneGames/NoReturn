package nitrogene.core;

import java.io.IOException;

import nitrogene.util.AppData;

public class Resources {
	public static String logStream = "";
	public final static boolean systemPrintLn = true;
	public static void log(Object ll){
		ll = ll.toString();
		logStream = logStream + ll + System.lineSeparator();
		if(systemPrintLn) System.out.println(ll);
		try {
			AppData.log(logStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
