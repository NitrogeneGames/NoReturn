package nitrogene.collision;

public class Circle {
   public Vector center;
   public float radius;
   
   public Circle(final float radius) {
      center = new Vector();
      this.radius = radius;
   }
   
   public void update(final Vector position) {
      center.x = position.x;
      center.y = position.y;
   }
}