package nitrogene.collision;

public class Ellipse {
	public Vector center;
	public float r[];
	public Vector F1, F2;
	
	//constant point for distA + distB
	public float e;
	
	 public Ellipse(final float width, final float height, final Vector center) {
	      this.center = new Vector();
	      this.center.x = center.x;
	      this.center.y = center.y;
	      r = new float[2];
	      r[0] = width * 0.5f;
	      r[1] = height * 0.5f;
	      //get foci
	      double foci = Math.sqrt(Math.pow(r[0], 2) - Math.pow(r[1],2));
	      F1 = new Vector();
	      F2 = new Vector();
	      F1.x = (float) (center.x - foci);
	      F2.x = (float) (center.x + foci);
	      F1.y = center.y;
	      F2.y = center.y;
	      //get e
	      Vector vec = new Vector();
	      vec.x = center.x;
	      vec.y = center.y + r[1];
	      e = (float) (Math.sqrt(vec.distSQ(F1)) + Math.sqrt(vec.distSQ(F2)));
	   }
}
