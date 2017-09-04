package nitrogene.core;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;

import nitrogene.objecttree.PhysicalObject;
import nitrogene.slick.BetterScalableGame;
import nitrogene.slick.BetterAppGameContainer;
import nitrogene.util.AppData;
import nitrogene.world.ObjectMap;
import nitrogene.world.TravelPath;

public class Resources {
	public static String logStream = "";
	public static BetterAppGameContainer appInstance;
	public static BetterScalableGame scaleGameInstance;
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
	public static void updateGraphics(GameContainer container) 	{	
		if(Display.getWidth() != container.getWidth() || Display.getHeight() != container.getHeight()) {
	        try {
	            appInstance.setDisplayMode(Display.getWidth(), Display.getHeight(), false);
	        	//scaleGameInstance.recalculateScale();

	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	public static ArrayList<float[]> getPath(PhysicalObject o, float x1f, float y1f, float x2f, float y2f, int chunkWidth, ArrayList<PhysicalObject> ignore) {
		int maxSteps = (int) 100000;
		int x1 = (int) Math.floor(x1f/chunkWidth);
		int x2 = (int) Math.floor(x2f/chunkWidth);
		int y1 = (int) Math.floor(y1f/chunkWidth);
		int y2 = (int) Math.floor(y2f/chunkWidth);	
		AStarPathFinder finder = new AStarPathFinder(new ObjectMap(o, chunkWidth, ignore), maxSteps, true, new ClosestHeuristic());
		org.newdawn.slick.util.pathfinding.Path p = finder.findPath(o, x1, y1, x2, y2);
		ArrayList<float[]> points = new ArrayList<float[]>();
		if(p!=null) {
			for(int i = 0; i < p.getLength(); i++) {		
				points.add(new float[] {chunkWidth*(p.getX(i)),chunkWidth*(p.getY(i))});
			}
			return points;
		}
		return null;
	}
	public static Path toPathShape(ArrayList<float[]> points) {
		Path d = new Path(points.get(0)[0], points.get(0)[1]);
		for(int i = 1; i < points.size(); i++) {		
			d.lineTo(points.get(i)[0], points.get(i)[1]);
		}
		return d;
	}
	public static ArrayList<Vector2f> toVectorArray(ArrayList<float[]> p) {
		ArrayList<Vector2f> vectors = new ArrayList<Vector2f>();
		for(int i = 0; i < p.size()-1; i++) {		
			vectors.add(new Vector2f(new float[] {(p.get(i+1)[0]-p.get(i)[0]),(p.get(i+1)[1]-p.get(i)[1])}));
		}
		return vectors;
	}
	public static TravelPath toTravelPath(ArrayList<float[]> p) {
		return new TravelPath(toVectorArray(p));
	}
	/*public static Path getPathShape(PhysicalObject o, float x1f, float y1f, float x2f, float y2f, int chunkWidth, ArrayList<PhysicalObject> ignore) {
	int maxSteps = (int) 100000;
	int x1 = (int) Math.floor(x1f/chunkWidth);
	int x2 = (int) Math.floor(x2f/chunkWidth);
	int y1 = (int) Math.floor(y1f/chunkWidth);
	int y2 = (int) Math.floor(y2f/chunkWidth);	
	AStarPathFinder finder = new AStarPathFinder(new ObjectMap(o, chunkWidth, ignore), maxSteps, true, new ClosestHeuristic());
	org.newdawn.slick.util.pathfinding.Path p = finder.findPath(o, x1, y1, x2, y2);
	if(p!=null) {
		Path d = new Path(p.getX(0)*chunkWidth, p.getY(1)*chunkWidth);
		for(int i = 1; i < p.getLength(); i++) {		
			int x = p.getX(i)*chunkWidth;
			int y = p.getY(i)*chunkWidth;
			d.lineTo(x, y);
		}
		return d;
	}
	return null;
}*/
	/*
	public static TravelPath getTravelPath(PhysicalObject o, float x1f, float y1f, float x2f, float y2f, int chunkWidth, ArrayList<PhysicalObject> ignore) {
		int maxSteps = (int) 100000;
		int x1 = (int) Math.floor(x1f/chunkWidth);
		int x2 = (int) Math.floor(x2f/chunkWidth);
		int y1 = (int) Math.floor(y1f/chunkWidth);
		int y2 = (int) Math.floor(y2f/chunkWidth);	
		AStarPathFinder finder = new AStarPathFinder(new ObjectMap(o, chunkWidth, ignore), maxSteps, true, new ClosestHeuristic());
		org.newdawn.slick.util.pathfinding.Path p = finder.findPath(o, x1, y1, x2, y2);
		ArrayList<Vector2f> vectors = new ArrayList<Vector2f>();
		if(p!=null) {
			for(int i = 0; i < p.getLength()-1; i++) {		
				vectors.add(new Vector2f(new float[] {chunkWidth*(p.getX(i+1)-p.getX(i)),chunkWidth*(p.getY(i+1)-p.getY(i))}));
			}
			return new TravelPath(vectors);
		}
		return null;
	}*/
}
