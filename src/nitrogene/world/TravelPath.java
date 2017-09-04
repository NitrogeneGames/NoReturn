package nitrogene.world;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import nitrogene.core.Resources;
import nitrogene.util.Target;

public class TravelPath {
	private ArrayList<Vector2f> vectors;
	
	
	public int currentVectorNum = 0;
	public int vectorMerge = 2;
	
	
	public float deltaX = 0;
	public float deltaY = 0;
	public float deltXcumu = 0;
	public float deltYcumu = 0;
	public float cumulativeX = 0;
	public float cumulativeY = 0;
	public float vectorX = 0;
	public float vectorY = 0;
	public float lastTheta = 0;
	boolean done = false;
	public TravelPath(ArrayList<Vector2f> vectors) {
		this.vectors = vectors;
		updateVectors();
	}
	public void updateVectors() {
		deltaX = 0;
		deltaY = 0;
		for(int i = 0; i < vectorMerge; i++) {
			if(vectors.size() > currentVectorNum + i) {
				deltaX = deltaX + vectors.get(currentVectorNum + i).x;
				vectorX = vectorX + vectors.get(currentVectorNum + i).x;
				deltaY = deltaY + vectors.get(currentVectorNum + i).y;
				vectorY = vectorY + vectors.get(currentVectorNum + i).y;
			} else {
				done = true;
			}
		}
		currentVectorNum = currentVectorNum + vectorMerge;
	}
	public void skipVectors(int v) {
		int x = v;
		for(int i = 0; i < v; i++) {
			if(vectors.size() > currentVectorNum + i) {
				deltaX = deltaX + vectors.get(currentVectorNum + i).x;
				vectorX = vectorX + vectors.get(currentVectorNum + i).x;
				deltaY = deltaY + vectors.get(currentVectorNum + i).y;
				vectorY = vectorY + vectors.get(currentVectorNum + i).y;
			} else {
				x = i;
				done = true;
			}
		}
		currentVectorNum = currentVectorNum + x;
	}	
	public float distToTurn() {//returns distance x, y, then degree of turn
		float xDist = -deltXcumu;
		float yDist = -deltYcumu;
		float dist = 0;
		int i = currentVectorNum;
		if(!(i<vectors.size())) {
			return 0;
		}
		float theta = (float) Math.toDegrees(Math.atan2(vectors.get(i).y, vectors.get(i).x));
		while(i<vectors.size()) {
			float theta2 = (float) Math.toDegrees(Math.atan2(vectors.get(i).y+vectors.get(i+1).y, vectors.get(i).x+vectors.get(i+1).x));
			dist = Math.abs(Target.getRotation(theta, theta2));
			if(dist < 10) {
				i = i+2;
				if(vectors.size()>i) {
					xDist = xDist + vectors.get(i).x;
					yDist = yDist + vectors.get(i).y;
				} else {
					xDist = xDist + vectors.get(i).x+vectors.get(i+1).x;
					yDist = yDist + vectors.get(i).y+vectors.get(i+1).y;				
				}
			} else {
				break;
			}
		}
		float c = (float) Math.pow(xDist*xDist+yDist*yDist, .5);
		return c;
	}
	boolean skipFlag = true;
	public float getAngle() {
		float dX = vectorX - cumulativeX;
		float dY = vectorY - cumulativeY;
		float hyp = (float) Math.pow(dX*dX+dY*dY, .5);
		//return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
		//return lastTheta;
		if(hyp > 180) {
			if(skipFlag) {
				skipVectors(8);//rough estimate
				skipFlag = false;
			}
			return (float) Math.toDegrees(Math.atan2(dY, dX));
		} else if(hyp > 10) {
			float cons = 10;
			return (float) Math.toDegrees(Math.atan2(deltaY + dY/cons, deltaX + dX/cons));
		} else { 
			if(hyp < 40) {
				skipFlag = true;
			}
			return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
		}
	}
	public void registerMovement(float a, float b) {
		deltXcumu = deltXcumu + a;
		deltYcumu = deltYcumu + b;
		cumulativeX = cumulativeX + a;
		cumulativeY = cumulativeY + b;
		boolean x0 = deltaX == 0;
		boolean y0 = deltaY == 0;
		int offset = 10;
		if(deltaX > 0 && deltaY > 0) {
			if((deltXcumu > deltaX-offset||x0)  && (deltYcumu > deltaY-offset||y0)) {
				deltXcumu = deltXcumu - deltaX;
				deltYcumu = deltYcumu - deltaY;
				updateVectors();
			}
		} else if(deltaX > 0 && deltaY <= 0) {
			if(((deltXcumu) > (deltaX-offset)||x0)  && ((deltYcumu) < (deltaY+offset)||y0)) {
				deltXcumu = deltXcumu - deltaX;
				deltYcumu = deltYcumu - deltaY;
				updateVectors();
			}
		} else if(deltaX <= 0 && deltaY > 0) {
			if(((deltXcumu) < (deltaX+offset)||x0)  && ((deltYcumu) > (deltaY-offset)||y0)) {
				deltXcumu = deltXcumu - deltaX;
				deltYcumu = deltYcumu - deltaY;
				updateVectors();
			}
		} else if(deltaX <= 0 && deltaY <= 0) {
			if(((deltXcumu) < (deltaX-offset)||x0)  && ((deltYcumu) < (deltaY-offset)||y0)) {
				deltXcumu = deltXcumu - deltaX;
				deltYcumu = deltYcumu - deltaY;
				updateVectors();
			}
		}
		lastTheta = (float) Math.toDegrees(Math.atan2(a, b));
	}
}
