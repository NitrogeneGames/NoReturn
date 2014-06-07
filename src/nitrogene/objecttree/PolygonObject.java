package nitrogene.objecttree;

import java.util.ArrayList;

import nitrogene.util.ImageBase;
import nitrogene.world.ArenaMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

@Deprecated
public class PolygonObject extends PhysicalObject{

	public PolygonObject(float x, float y, Image img, float scale, ArenaMap map) {
		super(0F, 0F, img, scale, map);
		ArrayList<float[]> points = ImageBase.pixelize(img);
		boundbox = new Polygon();
		for(int i = 0; i<points.size(); i++) {
			((Polygon) boundbox).addPoint(points.get(i)[0], points.get(i)[1]);
		}
		init(boundbox.getWidth(), boundbox.getHeight());
	}
}
