package nitrogene.util;

import nitrogene.collision.Vector;

public class Movement {
	private boolean[] toggle;
	private float[] diracceleration;
	private int downbound, leftbound, upbound, rightbound;
	
	public Movement(int upbound, int downbound, int leftbound, int rightbound){
		toggle = new boolean[5];
		diracceleration = new float[5];
		
		this.upbound = upbound;
		this.downbound = downbound;
		this.leftbound = leftbound;
		this.rightbound = rightbound;
	}
	
	public void Toggle(Direction direction){
		if(direction == Direction.UP){
			toggle[1] = !toggle[1];
		}
		if(direction == Direction.DOWN){
			toggle[2] = !toggle[2];
		}
		if(direction == Direction.LEFT){
			toggle[3] = !toggle[3];
		}
		if(direction == Direction.RIGHT){
			toggle[4] = !toggle[4];
		}
		
		//rules
		if(toggle[2] && toggle[1]){ 
			toggle[2] = false;
			toggle[1] = false;
		}
		if(toggle[3]  && toggle[4]){ 
			toggle[3] = false;
			toggle[4] = false;
		}
	}
	
	//linear acceleration: 0 to 5 in increments of 0.1

	public void Accelerate(Vector location, int delta){
		float DELTACON = delta/1000f;
		
		float time = this.getDx()/(0.1f*(delta/5f));
		float speed = (this.getDx() * 20 * DELTACON);
		float distance = (speed*time) + ((1/2) * (speed/time) * time * time);
		
		float ltime = this.getDy()/(0.1f*(delta/5f));
		float lspeed = (this.getDy() * 20 * DELTACON);
		float ldistance = (lspeed*ltime) + ((1/2) * (lspeed/ltime) * ltime * ltime);
		
		if(location.x > rightbound - distance){
			BringBack(Direction.RIGHT, delta);
		} else {
			if(toggle[4] && diracceleration[4] < 20f) diracceleration[4] += 0.05f*delta/5f;
			else if(!toggle[4] && diracceleration[4] > 0f) diracceleration[4] -= 0.05f*delta/5f;
			else if(!toggle[4]) diracceleration[4] = 0f;
		}
		
		if (location.x < leftbound + distance){
			BringBack(Direction.LEFT, delta);
		} else{
			if(toggle[3] && diracceleration[3] < 20f) diracceleration[3] += 0.05f*delta/5f;
			else if(!toggle[3] && diracceleration[3] > 0f) diracceleration[3] -= 0.05f*delta/5f;
			else if(!toggle[3]) diracceleration[3] = 0f;
		}
		
		if (location.y > downbound - ldistance){
			BringBack(Direction.DOWN, delta);
		} else {
			if(toggle[2] && diracceleration[2] < 20f) diracceleration[2] += 0.05f*delta/5f;
			else if(!toggle[2] && diracceleration[2] > 0f) diracceleration[2] -= 0.05f*delta/5f;
			else if(!toggle[2]) diracceleration[2] = 0f;
		}
		
		if (location.y < upbound + ldistance){
			BringBack(Direction.UP, delta);
		} else{
			if(toggle[1] && diracceleration[1] < 20f) diracceleration[1] += 0.05f*delta/5f;
			else if(!toggle[1] && diracceleration[1] > 0f) diracceleration[1] -= 0.05f*delta/5f;
			else if(!toggle[1]) diracceleration[1] = 0f;
		}
	}
	
	public void BringBack(Direction direction, int delta){
		switch(direction){
		case UP: toggle[1] = false;
			if(diracceleration[1] > 0f) diracceleration[1] -= 0.05f*delta/5f;
			else if(diracceleration[1] < 0f) diracceleration[1] = 0f;
			break;
		case DOWN: toggle[2] = false;
			if(diracceleration[2] > 0f) diracceleration[2] -= 0.05f*delta/5f;
			else if(diracceleration[2] < 0f) diracceleration[2] = 0f;
			break;
		case LEFT: toggle[3] = false;
			if(diracceleration[3] > 0f) diracceleration[3] -= 0.05f*delta/5f;
			else if(diracceleration[3] < 0f) diracceleration[3] = 0f;
			break;
		case RIGHT: toggle[4] = false;
			if(diracceleration[4] > 0f) diracceleration[4] -= 0.05f*delta/5f;
			else if(diracceleration[4] < 0f) diracceleration[4] = 0f;
			break;
		}
	}
	
	public void Break(int delta){
	for(int e = 1; e < 5; e++){
		if(toggle[e]) {
			toggle[e] = false;
		}
		if(diracceleration[e] > 0f){
			diracceleration[e] -= .1f*delta/5f;
		}
		if(diracceleration[e] <= 0f) diracceleration[e] = 0f;
	}
	}
	
	public float getDx(){
		return diracceleration[4] - diracceleration[3];           
	}
	
	
	public float getDy(){
		return diracceleration[2] - diracceleration[1];
	}
	
	public boolean getToggle(Direction direction)
	{
		if(direction == Direction.UP){
			return toggle[1];
		}
		if(direction == Direction.DOWN){
			return toggle[2];
		}
		if(direction == Direction.LEFT){
			return toggle[3];
		}
		if(direction == Direction.RIGHT){
			return toggle[4];
		}
		return false;
	}
}
