package nitrogene.collision;

public class Vector {
	   public float x;
	   public float y;
	   
	   public Vector() {
	      x = 0.0f;
	      y = 0.0f;
	   }
	        
	   // returns the (squared) distance between this Vector and another
	   public float distSQ(final Vector vec) {
	      float distX = x - vec.x;
	      float distY = y - vec.y;
	      
	      return distX * distX + distY * distY;
	   }
	}