package nitrogene.core;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import nitrogene.gui.AnimationManager;
import nitrogene.gui.AnimationImage;
import nitrogene.gui.Hotbar;
import nitrogene.gui.HullBar;
import nitrogene.gui.HybridDisplay;
import nitrogene.gui.Minimap;
import nitrogene.gui.ShieldBar;
import nitrogene.inventory.DroppedItem;
import nitrogene.inventory.Item;
import nitrogene.npc.MovementTask;
import nitrogene.npc.NPCship;
import nitrogene.npc.Relation;
import nitrogene.npc.Task;
import nitrogene.npc.TaskFire;
import nitrogene.npc.TaskFollow;
import nitrogene.npc.TaskMoveTo;
import nitrogene.util.Button;
import nitrogene.util.Direction;
import nitrogene.util.PauseButton;
import nitrogene.util.Stars;
import nitrogene.util.TickSystem;
import nitrogene.util.ZoomEnum;
import nitrogene.weapon.EnumWeapon;
import nitrogene.weapon.LaserLauncher;
import nitrogene.weapon.PhysicalProjcetile;
import nitrogene.world.World;
import nitrogene.world.Asteroid;
import nitrogene.world.Planet;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;



public class GameState extends BasicGameState{
	public static Rectangle screenBox;
	public static int level = 1;
	public static boolean superHardDifficulty = false;
	private boolean doStars = false;
	private boolean debugRender = true;
	public static float currentZoom = 1.0f;
	Graphics backup;
	Craft craft;
	public Hotbar guihotbar;
	private ShieldBar shieldbar;
	private HullBar hullbar;
	PauseButton resume, restart, hangar, menu, options, exit;
	Button nextLevel;
	public static World map;
	Stars stars;
	SpriteSheet spriteex;
	private boolean planetCollisions = false;
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
	//Image gameRender;
	//Graphics g;
	
	public GameState(int width, int height) {

		this.SCR_width = width;
		this.SCR_height = height;
		/*try {
			//gameRender = new Image(SCR_width, SCR_height);
			//g = gameRender.getGraphics();
		} catch (SlickException e) {
			e.printStackTrace();
		}*/
		
	}
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		Resources.log("Initializing Gamestate...");
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
			int width = 184;
			int height = 40;
			nextLevel = new Button("Next Level", SCR_width/2-width/2, SCR_height/2-height/2 + 30, width, height);
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

			//set largest zoom for generation: 0.5f maximum (minimum is 1.0f)
			Zoom.setZoom(currentZoom);
			Zoom.setZoomWindow(SCR_width, SCR_height);
			//other variables
					mapwidth = 10000;
					mapheight = 4000;
					offsetY = SCR_height/2;
			    	offsetX = SCR_width/2;
			    	camX = 0;
			    	camY = 0;
			map = new World(10,offsetX,offsetY,mapwidth,mapheight);
			//load all resources here
			craft = new Craft(map, 2000, 1200, "humanship", 1);

			map.loadCraft(craft);
			map.loadCraft(new NPCship(map, 00, 00, Relation.HOSTILE, "craftimage", 1));	
			map.loadCraft(new NPCship(map, 4000, 00, Relation.HOSTILE, "craftimage", 1));
			//map.addCraft(new NPCship(4000, 1600, Relation.HOSTILE, "craftimage", 1));
			if(level > 3) {
					
				map.loadCraft(new NPCship(map, 00, 1600, Relation.HOSTILE, "craftimage", 1));
				map.loadCraft(new NPCship(map, 00, 1000, Relation.HOSTILE, "craftimage", 1));
			}
	    	minimap = new Minimap(300, 121, SCR_width, SCR_height, mapwidth, mapheight, craft);
			int varx = (int)(this.SCR_width);
			int vary = (int)(this.SCR_height);
			if (doStars) {
				stars = new Stars(2,mapwidth+(2*varx),mapheight+(2*vary), -1*(varx), -1*(vary), 100);
			}
	      /*
	      for(LaserLauncher l : craft.laserlist){
	    	  TickSystem.removeTimer(TickSystem.getTimer(l));
	      }
	      */
	      for(Craft c : map.getCrafts()) {
	    	  if(!c.isLoaded) {
	    		  c.load(c.img, c.getScale(), map);
	    		  c.isLoaded = true;
	    	  }
	    	  if(c.getClass() != NPCship.class) {
	    		  c.loadWeapons(GlobalInformation.getStartingWeapons());
	    	  } else {
	    		  NPCship n = (NPCship) c;
	    		  
	    		  ArrayList<EnumWeapon> enemyWeapons = new ArrayList<EnumWeapon>();
	    		  enemyWeapons.add(EnumWeapon.BASIC);
	    		  for(int i = 0; i < level%4 && i < 4; i++){
	    			  
	    			  enemyWeapons.add(EnumWeapon.BASIC);
	    		  }
	    		  if(level == 7) {
	    			  enemyWeapons.add(EnumWeapon.IMMINEO2);
	    		  }
	    		  c.loadWeapons(enemyWeapons);
	    		  //n.addTask(new TaskMoveTo(n, 2000, 1000, 10));
	    		  
	    		  n.addTask(new TaskFollow(n, craft, 500));
			      for(int i = 0; i < enemyWeapons.size(); i++) {
			    	  n.addTask(new TaskFire(n, craft, i));
			      }
		      }
	      }
		  map.generate(map.getOffsetX(), map.getOffsetY(), mapwidth, mapheight, craft);
	      if(!this.guielementsloaded){
	    	shieldbar = new ShieldBar(1.4f);
	      	hullbar = new HullBar(1.4f);
	      	guihotbar = new Hotbar(craft);
	      	this.guielementsloaded = true;
	      }
	      map.asteroidResume();
	      this.PAUSED = false;
	      map.generateSectors();
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
		if(craft.getSprite()!=null){
			camX = (float) ((craft.getX()+(craft.getSprite().getImage().getWidth()/2))*Zoom.getZoom()) - (SCR_width/2);	 
			camY = (float) ((craft.getY()+(craft.getSprite().getImage().getHeight()/2))*Zoom.getZoom()) - (SCR_height/2);
		}
		
		int lasercount = 0;
		//System.out.println(Zoom.getZoom());
		if (delta > 200) {
			delta = 200;
		}
		Resources.updateGraphics(container);
		if(!container.hasFocus()){
			PAUSED = true;
		}
		
		Zoom.setZoomWindow(SCR_width, SCR_height);
		screenBox = new Rectangle(camX/Zoom.getZoom(), camY/Zoom.getZoom(), SCR_width/Zoom.getZoom(), SCR_height/Zoom.getZoom());
		Input input = container.getInput();
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			PAUSED = !PAUSED;
		}
		if(input.isKeyPressed(Input.KEY_X)){
			container.exit();
		}
		nextLevel.update(container);
		if(!PAUSED){
			if(getEnemyCount() == 0) {
				if(nextLevel.isClicked()) {
					level++;
					game.enterState(2);
				}
			} else {
				if(TickSystem.isPaused()) {
					TickSystem.gameResume();
					map.asteroidResume();
				}
				AnimationManager.updateAnimation(delta);
				CursorSystem.update(container);
				TickSystem.update(delta);
				//map.updateSectors(map.getCrafts());
		    	minimap.update();
		    	//guihotbar.update(craft);
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
					GameObject obj = map.getObjList().get(n);
					if(obj.getClass() == Craft.class){
						obj.update(delta,camX,camY);
						for(int m = 0; m<craft.laserlist.size(); m++) {
							LaserLauncher laserlauncher = craft.laserlist.get(m);
							 laserlauncher.update(craft.getX(), craft.getY(),delta);
							
							 for(int i = 0;i<laserlauncher.slaserlist.size();i++){
								PhysicalProjcetile laser = laserlauncher.slaserlist.get(i);
								Line path = laser.move(10,delta);
								//collide(laser, laserlauncher, map.checkCollision(laser));
								if(debugRender) {
									//TODO LAGGY
									for(int e = 0; e < map.getPlanets().size(); e++){
										Planet mesh = map.getPlanets().get(e);
										if(mesh.isColliding(laser) || mesh.isColliding(path)){
											float rotation = (float) Math.toRadians(laser.getSprite().getImage().getRotation());
											float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
											float xN = (float) (hyp*Math.cos(rotation))/2;
											float yN = (float) (hyp*-Math.sin(rotation))/2;
											AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
											laserlauncher.slaserlist.remove(laser);
											mesh.damage(laser.getPlanetDamage(), map);
										}
										}
									for(int r = 0; r < map.getCrafts().size(); r++){
										Craft craft = map.getCrafts().get(r);
										if((craft.isColliding(laser) || craft.isColliding(path)) && !craft.equals(obj)){
											float rotation = (float) Math.toRadians(laser.getSprite().getImage().getRotation());
											float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
											float xN = (float) (hyp*Math.cos(rotation))/2;
											float yN = (float) (hyp*-Math.sin(rotation))/2;
											AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
											laserlauncher.slaserlist.remove(laser);
											craft.damageHull(laser.getDamage());
										}
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
						for(int m = 0; m<temp.laserlist.size(); m++) {
							LaserLauncher laserlauncher = temp.laserlist.get(m);
							laserlauncher.update(temp.getX(), temp.getY(),delta);
							
							for(int i = 0;i<laserlauncher.slaserlist.size();i++){
								lasercount++;
								PhysicalProjcetile laser = laserlauncher.slaserlist.get(i);
								Line path = laser.move(10,delta);
								//collide(laser, laserlauncher, map.checkCollision(laser));
								if(debugRender) {
									//SAME TODO AS ABOE
									for(int e = 0; e < map.getPlanets().size(); e++){
										Planet mesh = map.getPlanets().get(e);
										if(mesh.isColliding(laser) || mesh.isColliding(path)){
											float rotation = (float) Math.toRadians(laser.getSprite().getImage().getRotation());
											float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
											float xN = (float) (hyp*Math.cos(rotation))/2;
											float yN = (float) (hyp*-Math.sin(rotation))/2;
											AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
											laserlauncher.slaserlist.remove(laser);
											mesh.damage(laser.getPlanetDamage(), map);
										}
										}
									for(int r = 0; r < map.getCrafts().size(); r++){
										Craft craft = map.getCrafts().get(r);
										if((craft.isColliding(laser) || craft.isColliding(path)) && !craft.equals(obj)){
											float rotation = (float) Math.toRadians(laser.getSprite().getImage().getRotation());
											float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
											float xN = (float) (hyp*Math.cos(rotation))/2;
											float yN = (float) (hyp*-Math.sin(rotation))/2;
											AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
											laserlauncher.slaserlist.remove(laser);
											craft.damageHull(laser.getDamage());
										}
									}
								}
							}
							laserlauncher = null;
						} 
					} else if (obj.getClass() == Planet.class || obj.getClass() == Asteroid.class){
						if(planetCollisions) {
							for(Craft c : map.getCrafts()) {
								if(c.isColliding(obj)){
									c.setHull(0d);
								}
							}
						}
					
					} else{
						Resources.log("ERROR! physical object " + obj.getClass() + " is unidentified in gamestate update");
					}
		
				}
				
				//TODO ~ 100 FPS
				for(int f = 0; f < map.getAsteroids().size(); f++){
					Asteroid as = map.getAsteroids().get(f);
					as.update(delta);
				}
				/*for(int f = 0; f < map.getAsteroids().size(); f++){
					Asteroid as = map.getAsteroids().get(f);
					as.move(7, delta);
				}*/
				
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
	 		
		} else {
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
		    	if(restart.isClicked()) game.enterState(2);
		    	if(options.isClicked()) game.enterState(4);
		    	if(hangar.isClicked()) game.enterState(3);
		    	if(menu.isClicked()) game.enterState(1);
		    	if(exit.isClicked()) container.exit();
			}
		}
	public void collide(PhysicalProjcetile laser, LaserLauncher laserlauncher, GameObject mesh) {
		if (mesh == null) return;
		if(mesh.getClass() == Planet.class){
			float rotation = (float) Math.toRadians(laser.getSprite().getImage().getRotation());
			float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
			float xN = (float) (hyp*Math.cos(rotation))/2;
			float yN = (float) (hyp*-Math.sin(rotation))/2;			
			laserlauncher.slaserlist.remove(laser);
			try {
				AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
				((Planet) mesh).damage(laser.getPlanetDamage(), map);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		if(mesh.getClass() == Craft.class){
			if(((Craft) mesh).equals(laserlauncher.parent)) return;
			float rotation = (float) -Math.toRadians(laser.getSprite().getImage().getRotation());
			float hyp = (float) (Math.pow(laser.getSprite().getImage().getWidth()^2+laser.getSprite().getImage().getHeight()^2,.5)*laser.getScale());
			float xN = (float) (hyp*Math.cos(rotation))/2;
			float yN = (float) (hyp*-Math.sin(rotation))/2;
			try {
				AnimationManager.createExplosion(laser.getX()+xN, laser.getY()+yN, 2.5f, 100);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			laserlauncher.slaserlist.remove(laser);
			craft.damageHull(laser.getDamage());
		}
	}
		
	@SuppressWarnings("deprecation")
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//ORDER OF RENDERING IS THE ORDER IT IS CALLED TO BE DRAWN
		//Stars and Background
		//Order for Physical Objects:
		// 1) Planets, 2) Asteroids, 3) Craft, 4) Enemy Craft, 5) Craft Systems/Lasers, 6) Enemy Systems/Lasers
		// 7) LaserProjectiles		
		//System.out.println(enemy.getX()  + " " + enemy.getY());
		//camX = (float) ((craft.getX()+(craft.getSprite().getImage().getWidth()/2))*Zoom.getZoom()) - (SCR_width/2);	 
		//camY = (float) ((craft.getY()+(craft.getSprite().getImage().getHeight()/2))*Zoom.getZoom()) - (SCR_height/2);
		g.translate(-camX, -camY);
		g.setBackground(Color.black);
		g.scale((float)Zoom.getZoom(),(float)Zoom.getZoom());
		
		
		//Change the third paramater to control the camera's view rotation
		//g.rotate(camX + this.SCR_width/2, camY + this.SCR_height/2, -90);
		if(debugMode) {
			g.setColor(Color.red);
			g.draw(screenBox);
		}
		//g.drawRect(0, 0, mapwidth, mapheight);
		g.setColor(Color.yellow);
		//g.drawRect(0,0, mapwidth-5, mapheight-5);
		//stars.render(g, Zoom.scale(camX),Zoom.scale(camY));
		if (doStars) {
			//TODO, laggy on entry
			stars.render(g);
		}
		//if(GlobalInformation.testMode)Resources.log("Asteroid amount culling:"+n+ "   :   "+ map.getAsteroids().size());
		for(int i = 0; i < map.getPlanets().size(); i ++){
			Planet mesh = map.getPlanets().get(i);
			if(mesh.isOnScreen()) {
				//label at top with health
				if(mesh.getHp() < mesh.getMaxHp()){
					if(mesh.getHp() <= 200) g.setColor(Color.red);
					else if(mesh.getHp() <= 500) g.setColor(Color.orange);
					else g.setColor(Color.green);
					float gg = mesh.getHp();
					float ff = mesh.getMaxHp();
					float boxWidth = (gg/ff) * (mesh.getShape().getWidth());
					g.fillRect(mesh.getX() + mesh.getShape().getWidth()/2 - boxWidth/2, 
							mesh.getY() - 30, boxWidth, 20);
				}
				//drawing planet
				if(GlobalInformation.testMode)g.draw(mesh.getBoundbox());
				mesh.getSprite().draw(mesh.getX()+mesh.getShake().getDx(),mesh.getY()+mesh.getShake().getDy(),mesh.getScale());
				if(debugMode) {
					g.draw(mesh.getBoundbox());  //DRAW BOUNDBOX DEBUG
				}
				mesh = null;
			}
		}
		/*for(int e = 0; e < map.getAsteroids().size(); e++){
			Asteroid as = map.getAsteroids().get(e);
			int fr = -100;			
			as.getSprite().draw(as.getX(),as.getY(),as.getScale());
			as.getSprite().setRotation(as.getRotation());
			if(GlobalInformation.testMode){
				g.draw(as.getBoundbox());
			}
			as = null;
			//TODO lots of FPS
		}*/
		for(int e = 0; e < map.getAsteroids().size(); e++){
			Asteroid as = map.getAsteroids().get(e);
			if(as.isOnScreen()) {
				as.getSprite().draw(as.getX(),as.getY(),as.getScale());
				as.getSprite().setRotation(as.getRotation());			
			}
		}
		//if(GlobalInformation.testMode) g.draw(craft.getBoundbox());
		//if(GlobalInformation.testMode) g.draw(enemy.getBoundbox());
		for(int i = 0; i < map.getCrafts().size(); i++) {
			Craft c = map.getCrafts().get(i);
			if(debugMode) {
				g.draw(c.getBoundbox());
			}
			if(!c.isDestroyed()) c.getSprite().draw(c.getX(), c.getY());
			if(!c.isDestroyed()) c.renderSystems();
			for(LaserLauncher cannon : c.laserlist){
				cannon.render(g,camX,camY);
			}
		}
		int n = 0;


		
		for(DroppedItem mesh : map.getDroppedItem()){
			mesh.getSprite().draw(mesh.getX(), mesh.getY());
		}
		
		AnimationManager.renderAnimation();

		//Type inverse of third paramater here to counteract (for GUI components)
		//g.rotate(camX + this.SCR_width/2, camY + this.SCR_height/2, 90);
		//g.pushTransform();
		minimap.render(g,map);		
		shieldbar.render(g, (craft.shieldPercent));
		hullbar.render(g, (float)(craft.hullPercent));
		Image GUI = ((Image) AssetManager.get().get("GUI")).copy();
		GUI.draw();
		guihotbar.render(g,craft,selected);
		

		//for(GuiComponent component : componentlist){
		//	component.render(g);
		//}
		String levelText = "Level " + level;
		float scale = 2;
		g.scale(scale,  scale);
		g.setColor(Color.white);
		g.drawString(levelText, ((SCR_width-220)/scale), 
				((SCR_height-150)/scale));
		g.scale(1/scale,  1/scale);
		if (getEnemyCount() == 0) {
			Color trans = new Color(0f,0f,0f,0.8f);
	        g.setColor(trans);
	        g.fillRect(0,0, SCR_width, SCR_height);
			String vicText = "VICTORY!";
			g.setColor(Color.white);
			scale = 2;
			Image tempnormal = ((Image) AssetManager.get().get("defaultbuttonnormal")).copy();
			Image temppressed = ((Image) AssetManager.get().get("defaultbuttonpressed")).copy();
			Image temphover = ((Image) AssetManager.get().get("defaultbuttonhover")).copy();
			if (level != 7) {
				nextLevel.render(g, tempnormal, temppressed, temphover);
			}
			g.scale(scale,  scale);
			g.drawString(vicText, ((SCR_width/2)/scale-g.getFont().getWidth(vicText)/2), 
					((SCR_height/2-20)/scale-g.getFont().getHeight(vicText)/2));
			g.scale(1/scale,  1/scale);
			
		}
		if (PAUSED && drawPauseMenu) {
	        Color trans = new Color(0f,0f,0f,0.5f);
	        g.setColor(trans);
	        g.fillRect(0,0, SCR_width, SCR_height);
	        
	        Image pausemenu = ((Image) AssetManager.get().get("pausemenu")).copy();
	        pausemenu.setFilter(Image.FILTER_NEAREST);
	        pausemenu.draw(pausemenux,pausemenuy);
	        /*resume.render(g,camX,camY);
	        restart.render(g,camX,camY);
	        options.render(g,camX,camY);
	        hangar.render(g,camX,camY);
	        menu.render(g,camX,camY);
	        exit.render(g,camX,camY);*/
	        resume.render(g);
	        restart.render(g);
	        options.render(g);
	        hangar.render(g);
	        menu.render(g);
	        exit.render(g);
		}
	}

	public void mousePressed(int button, int x, int y){	
		x=Zoom.scale(x);
		y=Zoom.scale(y);
		
		
		
		if(!PAUSED && selected > -1) {
			if(Resources.appInstance.getInput().isKeyDown(Input.KEY_RCONTROL) || 
					Resources.appInstance.getInput().isKeyDown(Input.KEY_LCONTROL)){
				for(LaserLauncher laser : craft.laserlist) {
					if(button == 1) {
						laser.setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), false);
					} else if (button == 0){
						laser.setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), true);
					}
				}
			} else {
				if(button == 1) {
					craft.laserlist.get(selected).setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), false);
				} else if (button == 0){
					craft.laserlist.get(selected).setFire(x,y,Zoom.scale(camX),Zoom.scale(camY), true);
				}
			}
		}
	}
	
	public void mouseWheelMoved(int change){
		if(change <= -120 && Zoom.getZoom() > 0.5f){
			//Zoom out
			Zoom.setZoom(Zoom.getZoom()-0.08f);
		} else if(change >= 120 && Zoom.getZoom() < 1.0f){
			//Zoom in
			Zoom.setZoom(Zoom.getZoom()+0.08f);
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
	public int getEnemyCount() {
		int count = 0;
		for(Craft c : map.getCrafts()) {
			if (c.getClass() == NPCship.class) {
				count++;
			}
		}
		return count;
	}


	@Override
	public int getID() {
		return 2;
	}

}