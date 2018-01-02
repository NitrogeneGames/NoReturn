package nitrogene.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JFrame;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.heuristics.ClosestHeuristic;

import nitrogene.gui.Sprite;
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
	public static GameObject getCloserObject(float x, float y, GameObject p1, GameObject p2) {
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
	public static ArrayList<float[]> getPath(GameObject o, float x1f, float y1f, float x2f, float y2f, int chunkWidth, ArrayList<GameObject> ignore) {
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
	public static ArrayList<float[]> generateBoundboxFloats(String s) {
		ArrayList<float[]> points = new ArrayList<float[]>();
		
		Image i = new Sprite(s).getImage();
		for(int x = 0; x < i.getWidth()+1; x++) {
			for(int y = 0; y < i.getHeight()+1; y++) {
				if(i.getColor(x, y).getAlpha() != 0) {
					boolean flag = false;
					if(x > 0) {
						if(y > 0) {
							if(i.getColor(x-1, y-1).getAlpha() == 0) {
								flag = true;
							}
						} else {
							flag = true;
						}
						if(y < i.getHeight()) {
							if(i.getColor(x-1, y+1).getAlpha() == 0) {
								flag = true;
							}
						}
					} else {
						if(y < i.getHeight() || y > 0) {
							flag = true;
						}
					}
					if(x < i.getWidth()) {
						if(y > 0) {
							if(i.getColor(x+1, y-1).getAlpha() == 0) {
								flag = true;
							}	
						} else {
							flag = true;
						}
						if(y < i.getHeight()) {
							if(i.getColor(x+1, y+1).getAlpha() == 0) {
								flag = true;
							}
						} else {
							flag = true;
						}
					} else {
						if(y < i.getHeight() || y > 0) {
							flag = true;
						}
					}
					if(flag) {
						points.add(new float[] {x,y});
					}
				}
			}
		}
		return points;
	}
	public static ArrayList<float[]> sortClockwise(ArrayList<float[]> points, float xCenter, float yCenter) {
		ArrayList<float[]> finala = new ArrayList<float[]>();
		HashMap<Float, Float[]> hmap = new HashMap<Float, Float[]>();
		ArrayList<Float> angles = new ArrayList<Float>();
		for(float[] f : points) {
			float x = f[0] - xCenter;
			float y = f[1] - yCenter;
			Float theta = (float) Math.atan2(y, x);
			angles.add(theta);
			hmap.put(theta, new Float[] {x,y});
		}
		Collections.sort(angles);
		for(int i = 0; i < angles.size(); i++) {
			float theta = angles.get(i);
		
			finala.add(new float[] {hmap.get(theta)[0]+xCenter, hmap.get(theta)[1]+yCenter});
		}
		return finala;
	}
	public static ArrayList<float[]> removeInnards(ArrayList<float[]> list) {
		Comparator<float[]> floatCompareX = new Comparator<float[]>() {
	        public int compare(float[] p1, float[] p2) {
	            if(p1[0]>p2[0]) {
	            	return 1;
	            } else if(p1[0]<p2[0]) {
	            	return -1;
	            } else if(p1[1]>p2[1]) {
	            	return 1;
	            } else if(p1[1]<p2[1]) {
	            	return -1;
	            } else {
	            	return 0;
	            }
	        }
	    };
	    Collections.sort(list, floatCompareX);
		ArrayList<float[]> markedForDeath = new ArrayList<float[]>();
	    for(int i = 0; i < list.size(); i++) {
	    	int j =0;
	    	while(list.get(i+j)[0] == list.get(i)[0]) {
	    		
	    	}
	    }
	    return null;
		
	}
	public static Polygon toPolygon(ArrayList<float[]> arr) {
		float[] points = new float[arr.size()*2];
		for(int i = 0; i < arr.size(); i++) {
			points[2*i] = arr.get(i)[0];
			points[2*i+1] = arr.get(i)[1];
		}
		return new Polygon(points);
	}
	public static ArrayList<float[]> sortPointsJared(ArrayList<float[]> m) {
		ArrayList<float[]> orderedList = new ArrayList<float[]>();

		orderedList.add(m.remove(0)); //Arbitrary starting point

		while (m.size() > 0) {
		   //Find the index of the closest point (using another method)
		   int nearestIndex=findNearestIndexJared(orderedList.get(orderedList.size()-1), m);

		   //Remove from the unorderedList and add to the ordered one
		   orderedList.add(m.remove(nearestIndex));
		}
		return orderedList;
	}
	static int findNearestIndexJared (float[] thisPoint, ArrayList<float[]> listToSearch) {
	    double nearestDistSquared=Double.POSITIVE_INFINITY;
	    int nearestIndex = 0;
	    for (int i=0; i< listToSearch.size(); i++) {
	        float[] point2=listToSearch.get(i);
	        float distsq = (thisPoint[0] - point2[0])*(thisPoint[0] - point2[0]) 
	               + (thisPoint[1] - point2[1])*(thisPoint[1] - point2[1]);
	        if(distsq < nearestDistSquared) {
	            nearestDistSquared = distsq;
	            nearestIndex=i;
	        }
	    }
	    return nearestIndex;
	}
	public static Polygon generateBoundboxJoey(String s) {
		Image i = new Sprite(s).getImage();
		float x = i.getWidth()/2;
		float y = i.getHeight()/2;
		return toPolygon(sortClockwise(generateBoundboxFloats(s), x, y));
	}
	public static Polygon generateBoundboxJared(String s) {
		Image i = new Sprite(s).getImage();
		return toPolygon(sortPointsJared(generateBoundboxFloats(s)));
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
