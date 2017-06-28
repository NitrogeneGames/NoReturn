package nitrogene.core;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.gui.Hotbar;
import nitrogene.gui.HullBar;
import nitrogene.gui.Minimap;
import nitrogene.gui.ShieldBar;
import nitrogene.inventory.DroppedItem;
import nitrogene.inventory.Item;
import nitrogene.npc.NPCship;
import nitrogene.npc.Relation;
import nitrogene.npc.TaskFire;
import nitrogene.npc.TaskFollow;
import nitrogene.npc.TaskMoveTo;
import nitrogene.objecttree.PhysicalObject;
import nitrogene.util.AnimationManager;
import nitrogene.util.Direction;
import nitrogene.util.Explosion;
import nitrogene.util.PauseButton;
import nitrogene.util.Stars;
import nitrogene.util.TickSystem;
import nitrogene.util.ZoomEnum;
import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.LaserProjectile;
import nitrogene.world.ArenaMap;
import nitrogene.world.Asteroid;
import nitrogene.world.Planet;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class GameState extends BasicGameState{
	Graphics backup;
	Craft craft;
	NPCship enemy;
	public Hotbar guihotbar;
	private ShieldBar shieldbar;
	private HullBar hullbar;
	PauseButton resume, restart, hangar, menu, options, exit;
	ArenaMap map;
	Stars stars;
	SpriteSheet spriteex;
	private int mousewheelposition;
	private int mapwidth, mapheight;
	private Minimap minimap;
	private int offsetX, offsetY;
	private final int SCR_width, SCR_height;
	private int zoomwidth, zoomheight;
	private float pausemenux, pausemenuy;
	private float camX, camY;
	private boolean isRotated = false;
	private short selected = 0;
	public boolean guielementsloaded = false;
	public static boolean debugMode = false;

	public static boolean PAUSED = false;
	public static boolean drawPauseMenu = true;

	
	public GameState(int width, int height) {

		this.SCR_width = width;
		this.SCR_height = height;
	}
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		System.out.println("ininitngi");
    	//ADDRESS PROBLEM
    	
    	//componentlist = new ArrayList<GuiComponent>();
			//componentlist.add(new GuiComponent("masterwarn", 5, 653, new Image("res/gui/masterwarn_on.png"), new Image("res/gui/masterwarn_off.png"),1f));
			//componentlist.add(new GuiComponent("evacswitch", 58, 653, new Image("res/gui/toggleswitchon.png"), new Image("res/gui/toggleswitchoff.png"),0.5f));
    	
    	/*
    	shockwave = new ParticleSystem(shockimage,1500);
    	File shockfile = new File("res/test_emitter.xml");

    	try {
			ConfigurableEmitter emitter = ParticleIO.loadEmitter(shockfile);
			emitter.setPosition(500,500);

			shockwave.addEmitter(emitter);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	shockwave.setBlendingMode(ParticleSystem.BLEND_ADDITIVE);
		part = shockwave.getNewParticle(shockwave.getEmitter(0), 3000f);
		*/
		//buttons
		pausemenux = (SCR_width/2) - 52;
		pausemenuy = (SCR_height/2) - 102.5f;
		try {
			resume = new PauseButton(pausemenux + 6, pausemenuy + 6, ((Image) AssetManager.get().get("pauseresumeup")).copy(), ((Image) AssetManager.get().get("pauseresumedown")).copy());
			menu = new PauseButton(pausemenux + 6, pausemenuy + 105, ((Image) AssetManager.get().get("pausemenuup")).copy(), ((Image) AssetManager.get().get("pausemenudown")).copy());
			restart = new PauseButton(pausemenux + 6, pausemenuy + 39, ((Image) AssetManager.get().get("pauserestartup")).copy(), ((Image) AssetManager.get().get("pauserestartdown")).copy());
			hangar = new PauseButton(pausemenux + 6, pausemenuy + 72, ((Image) AssetManager.get().get("pausehangarup")).copy(), ((Image) AssetManager.get().get("pausehangardown")).copy());
			options = new PauseButton(pausemenux + 6, pausemenuy + 138, ((Image) AssetManager.get().get("pauseoptionsup")).copy(), ((Image) AssetManager.get().get("pauseoptionsdown")).copy());
			exit = new PauseButton(pausemenux + 6, pausemenuy + 171, ((Image) AssetManager.get().get("pauseexitup")).copy(), ((Image) AssetManager.get().get("pauseexitdown")).copy());
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.PAUSED = true;
		/*
		craftImage = new Image("res/klaarship6.png");
		enemyImage = new Image("res/klaarship6.png");
		sun = new Image("res/sun_1.png");
		pausemenu = new Image("res/button/pauseback.png");
		shockimage = new Image("res/shockwave_particle.png");
		statis = new Image("res/klaarship4.png");
		slaserimage = new Image("res/LaserV2ro.png");
		GUI = new Image("res/GUIportrait.png");
		pauseexitdown = new Image("res/button/pauseexitdown.png");
		pauseexitup = new Image("res/button/pauseexitup.png");
		pausehangardown = new Image("res/button/pausehangardown.png");
		pausehangarup = new Image("res/button/pausehangarup.png");
		pausemenudown = new Image("res/button/pausemenudown.png");
		pausemenuup = new Image("res/button/pausemenuup.png");
		pauseoptionsdown = new Image("res/button/pauseoptionsdown.png");
		pauseoptionsup = new Image("res/button/pauseoptionsup.png");
		pauserestartdown = new Image("res/button/pauserestartdown.png");
		pauserestartup = new Image("res/button/pauserestartup.png");
		pauseresumedown = new Image("res/button/pauseresumedown.png");
		pauseresumeup = new Image("res/button/pauseresumeup.png");
		*/
	}
	
	@Override
	   public void enter(GameContainer container, StateBasedGame game)
	         throws SlickException {
	      super.enter(container, game);
			CursorSystem.init();

			mousewheelposition = 0;
			//set largest zoom for generation
			Zoom.setZoom(ZoomEnum.MAP);
			Zoom.setZoomWindow(SCR_width, SCR_height);

			//other variables
					mapwidth = 5000;
					mapheight = 2000;
					offsetY = SCR_height/2;
			    	offsetX = SCR_width/2;
			    	camX = 0;
			    	camY = 0;

			//load all resources here
			
			
			map = new ArenaMap(5,offsetX,offsetY,mapwidth,mapheight,craft);
			
			craft = new Craft(1200, 1200);
			map.addCraft(craft);
			enemy = new NPCship(00, 00, Relation.HOSTILE);
			map.addCraft(enemy);
	    	
	    	minimap = new Minimap(300, 121, SCR_width, SCR_height, mapwidth, mapheight, craft);
			int varx = (int)(Zoom.getZoomWidth()-this.SCR_width);
			int vary = (int)(Zoom.getZoomWidth()-this.SCR_height);
	    	stars = new Stars(2,mapwidth+(2*varx),mapheight+(2*vary), -1*(varx), -1*(vary), 510);
	      /*
	      for(LaserLauncher l : craft.laserlist){
	    	  TickSystem.removeTimer(TickSystem.getTimer(l));
	      }
	      */
	      if(!craft.isLoaded){
	    	  craft.load(((Image) AssetManager.get().get("craftimage")).copy(), 1, map);
	    	  craft.isLoaded = true;
	      }
	      if(!enemy.isLoaded){
	    	  enemy.load(((Image) AssetManager.get().get("craftimage")).copy(), 1, map);
	    	  enemy.isLoaded = true;
	      }
	      map.generate(map.getOffsetX(), map.getOffsetY(), mapwidth, mapheight, craft);
	      if(!this.guielementsloaded){
	    	shieldbar = new ShieldBar(1.4f);
	      	hullbar = new HullBar(1.4f);
	      	guihotbar = new Hotbar(craft);
	      	this.guielementsloaded = true;
	      }
	      enemy.addCraftTarget(craft);
	      craft.loadWeapons(GlobalInformation.getStartingWeapons());
	      enemy.loadWeapons(GlobalInformation.getStartingWeapons());
	      enemy.addTask(new TaskFollow(enemy, craft));
	      enemy.addTask(new TaskFire(enemy, craft, 1));
	      map.asteroidResume();
	      this.PAUSED = false;
	   }
	
	@SuppressWarnings("static-access")
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException{
		super.leave(container, game);
    	container.getGraphics().setColor(Color.white);
		this.PAUSED = true;
	}
	public void hostUpdate(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
	}
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(!container.hasFocus()){
			PAUSED = true;
		}
		
		Zoom.setZoomWindow(SCR_width, SCR_height);
		if(mousewheelposition == -1){
			Zoom.setZoom(ZoomEnum.NORMAL);
		} else if(mousewheelposition == 0){
		    Zoom.setZoom(ZoomEnum.LARGE);
		} else if (mousewheelposition == 1){
			Zoom.setZoom(ZoomEnum.MAP);
		} else{
			System.out.println("ERROR! in beginning of update method, scroll zoom controls!");
		}
		
		Input input = container.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			PAUSED = !PAUSED;
		}
		if(input.isKeyPressed(Input.KEY_X)){
			container.exit();
		}
		if(!PAUSED){
		if(TickSystem.isPaused()) {
			TickSystem.gameResume();
			map.asteroidResume();
		}
		AnimationManager.updateAnimation(delta);
		CursorSystem.update(container);
    	minimap.update();
    	
    	
    	//Input Controllers
    	if(!craft.isDestroyed()) {
	    	if(input.isKeyDown(Input.KEY_RSHIFT) || input.isKeyDown(Input.KEY_LSHIFT)){
				craft.getMovement().Break(delta);
				craft.getMovement().changeAccelerator(Direction.FORWARD, false);
				craft.getMovement().changeAccelerator(Direction.BACKWARD, false);
				craft.getMovement().changeAccelerator(Direction.UPPERANGLE, false);
				craft.getMovement().changeAccelerator(Direction.UNDERANGLE, false);
			} else{
				if(input.isKeyDown(Input.KEY_W)){
					if(!craft.getMovement().getToggle(Direction.FORWARD)){
					craft.getMovement().changeAccelerator(Direction.FORWARD, true);}
				} else{
					if(craft.getMovement().getToggle(Direction.FORWARD)){
					craft.getMovement().changeAccelerator(Direction.FORWARD, false);}
				}
				if(input.isKeyDown(Input.KEY_S)){
					if(!craft.getMovement().getToggle(Direction.BACKWARD)){
					craft.getMovement().changeAccelerator(Direction.BACKWARD, true);}
				} else{
					if(craft.getMovement().getToggle(Direction.BACKWARD)){
					craft.getMovement().changeAccelerator(Direction.BACKWARD, false);}
				}
				if(input.isKeyDown(Input.KEY_A)){
					if(!craft.getMovement().getToggle(Direction.UPPERANGLE)){
					craft.getMovement().changeAccelerator(Direction.UPPERANGLE, true);}
				} else{
					if(craft.getMovement().getToggle(Direction.UPPERANGLE)){
					craft.getMovement().changeAccelerator(Direction.UPPERANGLE, false);}
				}
				if(input.isKeyDown(Input.KEY_D)){
					if(!craft.getMovement().getToggle(Direction.UNDERANGLE)){
					craft.getMovement().changeAccelerator(Direction.UNDERANGLE, true);}
				} else{
					if(craft.getMovement().getToggle(Direction.UNDERANGLE)){
					craft.getMovement().changeAccelerator(Direction.UNDERANGLE, false);}
				}
			}
    	}
		if(input.isKeyPressed(Input.KEY_T)){//debug mode
			GlobalInformation.testMode = !GlobalInformation.testMode;
			debugMode = !debugMode;
		}
		if(input.isKeyPressed(Input.KEY_I)){//open inventory
			//show inventory gui TODO
		}
    	
    	if(craft.laserlist.size() > 0 && input.isKeyPressed(Input.KEY_1)){
    		selected = 0;
    	} else if(craft.laserlist.size() > 1 && input.isKeyPressed(Input.KEY_2)){
    		selected = 1;
    	} else if(craft.laserlist.size() > 2 && input.isKeyPressed(Input.KEY_3)){
    		selected = 2;
    	} else if(craft.laserlist.size() > 3 && input.isKeyPressed(Input.KEY_4)){
    		selected = 3;
    	} else if(craft.laserlist.size() > 4 && input.isKeyPressed(Input.KEY_5)){
    		selected = 4;
    	} else if(craft.laserlist.size() > 5 && input.isKeyPressed(Input.KEY_6)){
    		selected = 5;
    	} else if(craft.laserlist.size() == 0){
    		selected = -1;
    	}
    	
		for(int n = 0; n < map.getObjList().size(); n++){
			PhysicalObject obj = map.getObjList().get(n);
			if(obj.getClass() == Craft.class){
				obj.update(delta,camX,camY);
				for(int m = 0; m<craft.laserlist.size(); m++) {
					LaserLauncher laserlauncher = craft.laserlist.get(m);
					 laserlauncher.update(craft.getX(), craft.getY(),delta);
					
					 for(int i = 0;i<laserlauncher.slaserlist.size();i++){
						LaserProjectile laser = laserlauncher.slaserlist.get(i);
						Line path = laser.move(10,delta);
						for(int e = 0; e < map.getPlanets().size(); e++){
							Planet mesh = map.getPlanets().get(e);
							mesh.getShake().update(delta);
							if(mesh.isColliding(laser) || mesh.isColliding(path)){
								AnimationManager.addAnimation(new Explosion(laser.getX()+laser.getImage().getWidth()/2, laser.getY()+laser.getImage().getHeight()/2, 2.5f, 100));
								//mesh.getShake().shakeObject(3, 1000);
								laserlauncher.slaserlist.remove(laser);
								mesh.damage(laser.getPlanetDamage(), map);
							}
							}
						for(int r = 0; r < map.getCrafts().size(); r++){
							Craft craft = map.getCrafts().get(r);
							if((craft.isColliding(laser) || craft.isColliding(path)) && !craft.equals(obj)){
								AnimationManager.addAnimation(new Explosion(laser.getX()+laser.getImage().getWidth()/2, laser.getY()+laser.getImage().getHeight()/2, 2.5f, 100));
								laserlauncher.slaserlist.remove(laser);
								craft.damageHull(laser.getDamage());
							}
						}
						//map.setPlanets(planetlist);
						/*
						for(PhysicalObject temp : map.getObjList()){
							if(!temp.equals(obj) && temp.isColliding(laser)){
								AnimationManager.addAnimation(new Explosion(laser.getCenterX(), laser.getCenterY(), 2.5f, 100));
								laserlauncher.slaserlist.remove(laser);
							}
						}
						*/
						if (laser.getX() > mapwidth - 20 || laser.getY() > mapheight - 30 || laser.getX() < 0 || laser.getY() < 0){
							laserlauncher.slaserlist.remove(laser);
						}
						laser = null;
					}
					laserlauncher = null;
				}
				} else if (obj.getClass() == NPCship.class){
				NPCship temp = (NPCship) obj;
				temp.update(delta,camX,camY);
				for(int m = 0; m<enemy.laserlist.size(); m++) {
					LaserLauncher laserlauncher = enemy.laserlist.get(m);
					laserlauncher.update(enemy.getX(), enemy.getY(),delta);
					
					for(int i = 0;i<laserlauncher.slaserlist.size();i++){
						LaserProjectile laser = laserlauncher.slaserlist.get(i);
						Line path = laser.move(10,delta);
						for(int e = 0; e < map.getPlanets().size(); e++){
							Planet mesh = map.getPlanets().get(e);
							mesh.getShake().update(delta);
							if(mesh.isColliding(laser) || mesh.isColliding(path)){
								AnimationManager.addAnimation(new Explosion(laser.getX()+laser.getImage().getWidth()/2, laser.getY()+laser.getImage().getHeight()/2, 2.5f, 100));
								//mesh.getShake().shakeObject(3, 1000);
								laserlauncher.slaserlist.remove(laser);
								mesh.damage(laser.getPlanetDamage(), map);
							}
							}
						for(int r = 0; r < map.getCrafts().size(); r++){
							Craft craft = map.getCrafts().get(r);
							if((craft.isColliding(laser) || craft.isColliding(path)) && !craft.equals(obj)){
								AnimationManager.addAnimation(new Explosion(laser.getX()+laser.getImage().getWidth()/2, laser.getY()+laser.getImage().getHeight()/2, 2.5f, 100));
								laserlauncher.slaserlist.remove(laser);
								craft.damageHull(laser.getDamage());
							}
						}
					}
					laserlauncher = null;
				} 
			} else if (obj.getClass() == Planet.class){
				for(Planet mesh : map.getPlanets()){
					if(craft.isColliding(mesh)){
						//craft.setHull(0d);
					}
				}
			} else{
				System.out.println("ERROR! Fix update in GameState");
			}

		}
		
		for(int f = 0; f < map.getAsteroids().size(); f++){
			Asteroid as = map.getAsteroids().get(f);
			as.update(delta);
		}
		
		for(int d = 0; d < map.getDroppedItem().size(); d++){
			DroppedItem di = map.getDroppedItem().get(d);
			di.update(delta,camX,camY);
			if(this.craft.isColliding(di)){
				for(Item e : di.getItemsInDrop()){
					e.changeParent(craft.getInventory());
				}
				craft.addToInventory(di.getItemsInDrop());
				di.destroy(map);
			}
		}
		
		camX = (float) ((craft.getX()+(craft.getImage().getWidth()/2))*Zoom.getZoom().scale) - (SCR_width/2);	 
		camY = (float) ((craft.getY()+(craft.getImage().getHeight()/2))*Zoom.getZoom().scale) - (SCR_height/2);
		
		//for(GuiComponent component : componentlist){
		//	component.move(camX, camY);
		//	component.update(container);
		//}
			/*for(Craft c : map.getCrafts()) {
				if(c.isDestroyed()) {
					AnimationManager.addAnimation(new Explosion(c.getRealCenterX(), c.getRealCenterY(), 3f, 100));
					map.getCrafts().remove(c);
				}
			}*/
		}
		else{
		//pause menu here
			//Button Controllers
			if(!TickSystem.isPaused()) {
				TickSystem.gamePause();
				map.asteroidPause();
			}
			resume.update(container);
	    	restart.update(container);
	    	menu.update(container);
	    	exit.update(container);
	    	options.update(container);
	    	hangar.update(container);
	    	
	    	if(resume.isClicked()){
	    		PAUSED = false;
	    	}
	    	if(menu.isClicked()) game.enterState(1);
	    	if(exit.isClicked()) container.exit();
		}
		}
		
		
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//System.out.println(enemy.getX()  + " " + enemy.getY());
		g.translate(-camX, -camY);
		g.setBackground(Color.black);
		g.scale((float)Zoom.getZoom().scale,(float)Zoom.getZoom().scale);
		
		//Change the third paramater to control the camera's view rotation
		//g.rotate(camX + this.SCR_width/2, camY + this.SCR_height/2, -90);
		
		g.setColor(Color.red);
		//g.drawRect(0, 0, mapwidth, mapheight);
		g.setColor(Color.yellow);
		//g.drawRect(0,0, mapwidth-5, mapheight-5);
		stars.render(Zoom.scale(camX),Zoom.scale(camY));
		
		if(GlobalInformation.testMode) g.draw(craft.getBoundbox());
		if(GlobalInformation.testMode) g.draw(enemy.getBoundbox());
		for(int i = 0; i < map.getCrafts().size(); i++) {
			Craft c = map.getCrafts().get(i);
			if(!c.isDestroyed()) c.getImage().draw(c.getX(), c.getY());
			if(!c.isDestroyed()) c.renderSystems();
		}
		int n = 0;
		for(int e = 0; e < map.getAsteroids().size(); e++){
			Asteroid as = map.getAsteroids().get(e);
			//culling
			//free constant
			int fr = -100;
			/*
			if(as.getX()>Zoom.getZoomWidth()/2+(craft.getX()+174)+fr||
					as.getX()+(as.getImage().getWidth()*as.getScale())<craft.getX()+174-(Zoom.getZoomWidth()/2)+fr||
					as.getY()>Zoom.getZoomHeight()/2+(craft.getY()+88)-fr||
					as.getY()+(as.getImage().getHeight()*as.getScale())<craft.getY()+88-(Zoom.getZoomHeight()/2)+fr){
				as=null;
				n++;
				continue;
			}
			//ASTEROID CULLING : BROKEN
			*/
			as.getImage().draw(as.getX(),as.getY(),as.getScale());
			//as.getImage().setCenterOfRotation(as.getRealCenterX(), as.getRealCenterY());
			as.getImage().setRotation(as.getRotation());
			if(GlobalInformation.testMode){
				g.draw(as.getBoundbox());
			}
			as = null;
		}
		//if(GlobalInformation.testMode)System.out.println("Asteroid amount culling:"+n+ "   :   "+ map.getAsteroids().size());
		for(int i = 0; i < map.getPlanets().size(); i ++){
			Planet mesh = map.getPlanets().get(i);
			//image culling
			
			if(mesh.getX()>Zoom.getZoomWidth()/2+(craft.getX()+174)||
					mesh.getX()+(mesh.getImage().getWidth()*mesh.getScale())<craft.getX()+174-(Zoom.getZoomWidth()/2)||
					mesh.getY()>Zoom.getZoomHeight()/2+(craft.getY()+88)||
					mesh.getY()+(mesh.getImage().getHeight()*mesh.getScale())<craft.getY()+88-(Zoom.getZoomHeight()/2)){
				mesh = null;
				continue;
			}
			
			/*
			 * if(mesh.getX()-(mesh.getImage().getWidth()*mesh.getScale())>Zoom.getZoomWidth()+camX||
					mesh.getX()+(mesh.getImage().getWidth()*mesh.getScale())<camX||
					mesh.getY()-(mesh.getImage().getHeight()*mesh.getScale())>Zoom.getZoomHeight()+camY||
					mesh.getY()+(mesh.getImage().getHeight()*mesh.getScale())<camY){
				mesh = null;
				continue;
			}
			 */
			//label at top with health
			if(mesh.getHp() < mesh.getMaxHp()){
				if(mesh.getHp() <= 200) g.setColor(Color.red);
				else if(mesh.getHp() <= 500) g.setColor(Color.orange);
				else g.setColor(Color.green);
				float gg = mesh.getHp();
				float ff = mesh.getMaxHp();
				g.fillRect(mesh.getX(), mesh.getY() - 20, (gg/ff) * (mesh.getShape().getWidth()), 20);
			}
			//drawing planet
			if(GlobalInformation.testMode)g.draw(mesh.getBoundbox());
			mesh.getImage().draw(mesh.getX()+mesh.getShake().getDx(),mesh.getY()+mesh.getShake().getDy(),mesh.getScale());
			if(debugMode) {
				g.draw(mesh.getBoundbox());  //DRAW BOUNDBOX DEBUG
			}
			mesh = null;
		}
		
		for(DroppedItem mesh : map.getDroppedItem()){
			mesh.getImage().draw(mesh.getX(), mesh.getY());
		}
		
		AnimationManager.renderAnimation();
		for(Craft c : map.getCrafts()) {
			if(!c.isDestroyed()) {
				for(LaserLauncher cannon : c.laserlist){
					cannon.render(g,camX,camY);
				}
				if(debugMode) {
					g.draw(craft.getBoundbox());  //DRAW BOUNDBOX DEBUG
				}
			}
		}
		
		//Type inverse of third paramater here to counteract (for GUI components)
		//g.rotate(camX + this.SCR_width/2, camY + this.SCR_height/2, 90);
		
		g.scale((float)Zoom.getZoom().inverse,(float)Zoom.getZoom().inverse);
		shieldbar.render(g, camX, camY, (craft.shield.getHp()/craft.shield.getMaxHp()));
		hullbar.render(g, camX, camY, (float)(craft.getHull()/craft.getMaxHull()));
		Image GUI = ((Image) AssetManager.get().get("GUI")).copy();
		GUI.draw(camX,camY);
		guihotbar.loadWeapons(g,craft,camX,camY,selected);
		minimap.render(g,camX,camY,map.getPlanets(),map.getCrafts(),map.getAsteroids());
		//for(GuiComponent component : componentlist){
		//	component.render(g);
		//}
		if (PAUSED && drawPauseMenu) {
	        Color trans = new Color(0f,0f,0f,0.5f);
	        g.setColor(trans);
	        g.fillRect(camX,camY, SCR_width, SCR_height);
	        
	        Image pausemenu = ((Image) AssetManager.get().get("pausemenu")).copy();
	        pausemenu.setFilter(Image.FILTER_NEAREST);
	        pausemenu.draw(pausemenux+camX,pausemenuy+camY);
	        resume.render(g,camX,camY);
	        restart.render(g,camX,camY);
	        options.render(g,camX,camY);
	        hangar.render(g,camX,camY);
	        menu.render(g,camX,camY);
	        exit.render(g,camX,camY);
		}
	}

	public void mousePressed(int button, int x, int y){
		x=Zoom.scale(x);
		y=Zoom.scale(y);
		if(!PAUSED && selected > -1) {
			if(button == 1) {
				craft.laserlist.get(selected).setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), false);
			} else if (button == 0){
				craft.laserlist.get(selected).setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), true);
			}
		}
	}
	
	public void mouseWheelMoved(int change){
		if(change <= -120 && mousewheelposition < 1){
			mousewheelposition += 1;
		} else if(change >= 120 && mousewheelposition > -1){
			mousewheelposition -= 1;
		}
	}

	public Object getTargetObject(float f, float g) {
		/*
		for(int e = 0;e<boxmeshlist.size();e++){
			AABB box = new AABB(1f,1f);
			box.update(new Vector(f,g));
			if(CollisionLibrary.testBoxBox(boxmeshlist.get(e).boundbox, box)){
				return this.boxmeshlist.get(e);
			}
		}
		*/
		for(Planet pl : map.getPlanets()){
			if(pl.isContaining(f,g)){
				return pl;
			}
		}
		return null;
		
	}
	


	@Override
	public int getID() {
		return 2;
	}

}