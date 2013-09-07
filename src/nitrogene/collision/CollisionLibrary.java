package nitrogene.collision;

public class CollisionLibrary {

	public static boolean testboxbox(final AABB box1, final AABB box2) {
		   if (Math.abs(box1.center.x - box2.center.x) > (box1.r[0] + box2.r[0])) return false;
		   if (Math.abs(box1.center.y - box2.center.y) > (box1.r[1] + box2.r[1])) return false;
		   return true;
		}
	public static boolean testCircleCircle(final Circle c1, final Circle c2) {
		   final float distSQ = c1.center.distSQ(c2.center);
		   final float radiusSum = c1.radius + c2.radius;
		  
		   return distSQ <= radiusSum * radiusSum;
		}
	public static float sqDistPointAABB(final Vector p, final AABB aabb) {
		   float sqDist = 0.0f;
		   float v;
		   float minX, minY, maxX, maxY;
		      
		   // get the minX, maxX, minY and maxY points of the AABB
		   minX = aabb.center.x - aabb.r[0];
		   maxX = aabb.center.x + aabb.r[0];
		      
		   minY = aabb.center.y - aabb.r[1];
		   maxY = aabb.center.y + aabb.r[1];
		      
		   // test the bounds against the points X axis
		   v = p.x;
		      
		   if (v < minX) sqDist += (minX - v) * (minX - v);
		   if (v > maxX) sqDist += (v - maxX) * (v - maxX);
		      
		   // test the bounds against the points Y axis
		   v = p.y;
		      
		   if (v < minY) sqDist += (minY - v) * (minY - v);
		   if (v > maxY) sqDist += (v - maxY) * (v - maxY);
		      
		   return sqDist;
		}
	
	public static boolean testCircleAABB(final Circle circle, final AABB box) {
		   // get the squared distance between circle center and the AABB
		   float sqDist = sqDistPointAABB(circle.center, box);
		   float r = circle.radius;
		  
		   return sqDist <= r * r;
		}
	
	public static boolean testEllipseCircle(final Ellipse ellipse, final Circle circle){
		Vector P = new Vector();
		Vector Q = new Vector();
		Q.x = 0;
		Q.y = 0;
		P.x = 0;
		P.y = 0;
		if(ellipse.F1.distSQ(P) + ellipse.F2.distSQ(P) <= ellipse.e) return true;
		if(ellipse.F1.distSQ(Q) + ellipse.F2.distSQ(Q) <= ellipse.e) return true;
		return false;
	}
}
