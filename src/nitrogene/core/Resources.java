package nitrogene.core;

import java.io.IOException;

import org.newdawn.slick.geom.Line;

import nitrogene.objecttree.PhysicalObject;
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
	public static float angleBetweenTwoLines(Line ln1, Line ln2) {
		float angle1 = (float) Math.atan2(ln1.getY1() - ln1.getY2(), ln1.getX1() - ln1.getX2());
		float angle2 = (float) Math.atan2(ln2.getY1() - ln2.getY2(), ln2.getX1() - ln2.getX2());
		return angle1 - angle2;
	}
	public static PhysicalObject getCloserObject(float x, float y, PhysicalObject p1, PhysicalObject p2) {
		Line l1 = new Line(x, y, p1.getRealCenterX(), p1.getRealCenterY());
		Line l2 = new Line(x, y, p2.getRealCenterX(), p2.getRealCenterY());
		if(l1.length() > l2.length()) {
			return p1;
		}
		return p2;
	}
}
