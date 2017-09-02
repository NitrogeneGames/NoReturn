package nitrogene.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import nitrogene.gui.AnimationImage;
import nitrogene.util.AppData;

public class LoadingState extends BasicGameState{
	String lastLoaded = "";
	/** The music that will be played on load completion */
	/** The sound that will be played on load completion */
	/** The image that will be shown on load completion */
	/** The font that will be rendered on load completion */
	/** The next resource to load */
	private DeferredResource nextResource;
	/** True if we've loaded all the resources and started rendereing */
	private float totalResources, resourcesLoaded;

	//PLACE IMAGE DECLARATIONS HERE!
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		LoadingList.setDeferredLoading(true);
		//SOUNDS
		Sound basicTestLaser = new Sound("res/sound/laser1final.ogg");
		AssetManager.get().put("laserSound1", basicTestLaser);
		Sound hit = new Sound("res/sound/Explosionfinal.ogg");
		AssetManager.get().put("explosionSound", hit);
		
		Image explosion = new Image("res/explanim.png");
		AssetManager.get().put("explosion", explosion);
		
		Image backgroundimg = new Image("res/menubackground4.png");
		AssetManager.get().put("menubackgroundimg", backgroundimg);
		
		Image defaultbuttonnormalimage = new Image("res/button/logomenubutton21.png");
		AssetManager.get().put("defaultbuttonnormal", defaultbuttonnormalimage);
		Image defaultbuttonhoverimage = new Image("res/button/logomenubutton2hover.png");
		AssetManager.get().put("defaultbuttonhover", defaultbuttonhoverimage);
		Image defaultbuttonpressedimage = new Image("res/button/logomenubutton2hover.png");
		AssetManager.get().put("defaultbuttonpressed", defaultbuttonpressedimage);
		
		Image startButtonImage = new Image("res/hangar/startbuttonhangar.png");
		AssetManager.get().put("startbutton", startButtonImage);
		Image menuButtonImage = new Image("res/hangar/menubutton.png");
		AssetManager.get().put("menubutton", menuButtonImage);
		Image minusButtonImage = new Image("res/hangar/minusbutton.png");
		AssetManager.get().put("minusbutton", minusButtonImage);
		Image plusButtonImage = new Image("res/hangar/plusbutton.png");
		AssetManager.get().put("plusbutton", plusButtonImage);
		Image shipButtonImage = new Image("res/hangar/button2.png");
		AssetManager.get().put("shipbutton", shipButtonImage);
		Image shipButtonImageHighlighted = new Image("res/hangar/buttonhighlighted2.png");
		AssetManager.get().put("shipbuttonhighlighted", shipButtonImageHighlighted);
		
		Image normalImageTab = new Image("res/hangar/tab2.png");
		AssetManager.get().put("normaltab", normalImageTab);
		Image pressedImageTab = new Image("res/hangar/tabhighlighted2.png");
		AssetManager.get().put("pressedtab", pressedImageTab);
		Image normalImageBuyButton = new Image("res/hangar/button2.png");
		AssetManager.get().put("normalbuybutton", normalImageBuyButton);
		Image pressedImageBuyButton = new Image("res/hangar/buttonhighlighted2.png");
		AssetManager.get().put("pressedbuybutton", pressedImageBuyButton);
		Image coinsImage = new Image("res/hangar/twocoins.png");
		AssetManager.get().put("coins", coinsImage);
		Image observatoryBackground = new Image("res/hangar/observatory1.png");
		AssetManager.get().put("observatory", observatoryBackground);
		Image hangarBackground = new Image("res/hangar/background.png");
		AssetManager.get().put("hangarbackground", hangarBackground);
		
		Image optionsBackground = new Image("res/button/optionsbackground.png");
		AssetManager.get().put("optionsbackground", optionsBackground);
		Image sliderImage = new Image("res/button/sliderbutton1.png");
		AssetManager.get().put("slider", sliderImage);
		
		Image shipSelectionImage = new Image("res/hangar/shipselectionfinal.png");
		AssetManager.get().put("shipselection", shipSelectionImage);
		
		Image craftImage = new Image("res/klaarship6.png");
		AssetManager.get().put("craftimage", craftImage);
		Image sunImage = new Image("res/sun_1.png");
		AssetManager.get().put("sun1", sunImage);
		Image volcanicplanetImage = new Image("res/volcanicplanet2.png");
		AssetManager.get().put("volcanicplanet", volcanicplanetImage);
		Image rockplanetImage = new Image("res/rockyplanet.png");
		AssetManager.get().put("rockyplanet", rockplanetImage);
		Image pauseMenu = new Image("res/button/pauseback.png");
		AssetManager.get().put("pausemenu", pauseMenu);
		
		Image slaserImage = new Image("res/LaserV2ro.png");
		AssetManager.get().put("proj1", slaserImage);
		Image slaserBolt = new Image("res/pulsar_bolt_fixed.png");
		AssetManager.get().put("proj2", slaserBolt);
		
		Image GUIimage = new Image("res/GUIportrait.png");
		AssetManager.get().put("GUI", GUIimage);
		Image twopixelstar = new Image("res/star2.png");
		AssetManager.get().put("twopixelstar", twopixelstar);
		Image asteroidImage1 = new Image("res/asteroid1.png");
		AssetManager.get().put("asteroid", asteroidImage1);
		
		Image pauseExitDownImage = new Image("res/button/pauseexitdown.png");
		AssetManager.get().put("pauseexitdown", pauseExitDownImage);
		Image pauseExitUpImage = new Image("res/button/pauseexitup.png");
		AssetManager.get().put("pauseexitup", pauseExitUpImage);
		Image pauseHangarDownImage = new Image("res/button/pausehangardown.png");
		AssetManager.get().put("pausehangardown", pauseHangarDownImage);
		Image pauseHangarUpImage = new Image("res/button/pausehangarup.png");
		AssetManager.get().put("pausehangarup", pauseHangarUpImage);
		Image pauseMenuDownImage = new Image("res/button/pausemenudown.png");
		AssetManager.get().put("pausemenudown", pauseMenuDownImage);
		Image pauseMenuUpImage = new Image("res/button/pausemenuup.png");
		AssetManager.get().put("pausemenuup", pauseMenuUpImage);
		Image pauseOptionsDownImage = new Image("res/button/pauseoptionsdown.png");
		AssetManager.get().put("pauseoptionsdown", pauseOptionsDownImage);
		Image pauseOptionsUpImage = new Image("res/button/pauseoptionsup.png");
		AssetManager.get().put("pauseoptionsup", pauseOptionsUpImage);
		Image pauseRestartDownImage = new Image("res/button/pauserestartdown.png");
		AssetManager.get().put("pauserestartdown", pauseRestartDownImage);
		Image pauseRestartUpImage = new Image("res/button/pauserestartup.png");
		AssetManager.get().put("pauserestartup", pauseRestartUpImage);
		Image pauseResumeUpImage = new Image("res/button/pauseresumeup.png");
		AssetManager.get().put("pauseresumeup", pauseResumeUpImage);
		Image pauseResumeDownImage = new Image("res/button/pauseresumedown.png");
		AssetManager.get().put("pauseresumedown", pauseResumeDownImage);
		
		//UI
		Image statusReadyImage = new Image("res/gui/status_ready.png");
		AssetManager.get().put("statusReady", statusReadyImage);
		Image statusFiringImage = new Image("res/gui/status_firing.png");
		AssetManager.get().put("statusFiring", statusFiringImage);
		Image statusDamagedImage = new Image("res/gui/status_damaged.png");
		AssetManager.get().put("statusDamaged", statusDamagedImage);
		Image statusNeedPowerImage = new Image("res/gui/status_needpower.png");
		AssetManager.get().put("statusNeedPower", statusNeedPowerImage);
		Image statusDestroyedImage = new Image("res/gui/status_destroyed.png");
		AssetManager.get().put("statusDestroyed", statusDestroyedImage);
		Image heartImage = new Image("res/gui/heart.png");
		AssetManager.get().put("heart", heartImage);
		Image boltImage = new Image("res/gui/lightningbolt.png");
		AssetManager.get().put("bolt", boltImage);
		Image shieldIconImage = new Image("res/gui/shieldicon2.png");
		AssetManager.get().put("shieldicon", shieldIconImage);
		Image shieldFillImage = new Image("res/gui/shieldbar2_fill.png");
		AssetManager.get().put("shieldfill", shieldFillImage);
		Image shieldBackgroundImage = new Image("res/gui/shieldbar2.png");
		AssetManager.get().put("shieldbackground", shieldBackgroundImage);
		Image hullIconImage = new Image("res/gui/hullicon.png");
		AssetManager.get().put("hullicon", hullIconImage);
		Image hullFillImage = new Image("res/gui/hullbar2_fill.png");
		AssetManager.get().put("hullfill", hullFillImage);
		Image hullBackgroundImage = new Image("res/gui/hullbar2.png");
		AssetManager.get().put("hullbackground", hullBackgroundImage);
		
		//systems
		Image shieldSystemIcon = new Image("res/icon/shieldsystem.png");
		AssetManager.get().put("shieldsystemicon", shieldSystemIcon);
		Image engineSystemIcon = new Image("res/icon/enginesystem.png");
		AssetManager.get().put("enginesystemicon", engineSystemIcon);
		Image coreSystemIcon = new Image("res/icon/coresystem.png");
		AssetManager.get().put("coresystemicon", coreSystemIcon);
		Image oxygenSystemIcon = new Image("res/icon/oxygensystem.png");
		AssetManager.get().put("oxygensystemicon", oxygenSystemIcon);
		
		Image standardLaserImage = new Image("res/Laser1.png");
		AssetManager.get().put("laser1", standardLaserImage);
		Image standardLaserGreenImage = new Image("res/Laser1Green.png");
		AssetManager.get().put("laser1Green", standardLaserGreenImage);
		Image laser2Image = new Image("res/Laser2.png");
		AssetManager.get().put("laser2", laser2Image);
		Image laser2ImageGreen = new Image("res/Laser2Green.png");
		AssetManager.get().put("laser2Green", laser2ImageGreen);
		Image klaarPulsarImage = new Image("res/Laser3.png");
		AssetManager.get().put("laser3", klaarPulsarImage);
		Image klaarPulsarImageGreen = new Image("res/Laser3Green.png");
		AssetManager.get().put("laser3Green", klaarPulsarImageGreen);
		
		Image wep1 = new Image("res/wep1.png");
		AssetManager.get().put("wep1", wep1);
		Image wep1green = new Image("res/wep1Green.png");
		AssetManager.get().put("wep1Green", wep1green);
		Image wep2 = new Image("res/wep2.png");
		AssetManager.get().put("wep2", wep2);	
		Image wep2green = new Image("res/wep2Green.png");
		AssetManager.get().put("wep2Green", wep2green);
		
		Image ion1 = new Image("res/lazer.png");
		AssetManager.get().put("ion1", ion1);
		Image ion1Background = new Image("res/lazer_zap_animation.png");
		AssetManager.get().put("ion1Background", ion1Background);
		AnimationImage ion1Green = new AnimationImage(100, "ion1Background", 1, 4, 17, 24, true, 0, 0);
		AssetManager.get().put("ion1Green", ion1Green);
		
		Image antibio = new Image("res/antibio.png");
		AssetManager.get().put("antibio", antibio);
		Image antibioBackground = new Image("res/antibio_animation.png");
		AssetManager.get().put("antibioBackground", antibioBackground);
		AnimationImage antibioAnimation = new AnimationImage(50, "antibioBackground", 1, 2, 16, 30, true, 0, 0);
		AssetManager.get().put("antibioAnimation", antibioAnimation);
		
		GlobalInformation.loadFonts();
		
		Image ironitem = new Image("res/item/Iron.png");
		AssetManager.get().put("ironitem", ironitem);
		Image titaniumitem = new Image("res/item/TitaniumModule.png");
		AssetManager.get().put("titaniumitem", titaniumitem);
		Image ammoitem = new Image("res/item/Ammo.png");
		AssetManager.get().put("ammoitem", ammoitem);	
		Music millionaireMusic = new Music("res/sound/millionaire.ogg");
		AssetManager.get().put("millionaire", millionaireMusic);

		
		Resources.log(LoadingList.get().getRemainingResources());
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game){
		container.setAlwaysRender(true);
		totalResources = LoadingList.get().getTotalResources();
		resourcesLoaded = 0;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Resources.updateGraphics(container);
		if(LoadingList.get().getRemainingResources() > 0){
			nextResource = LoadingList.get().getNext();
			try {
				lastLoaded = nextResource.getDescription();
				nextResource.load();
				Resources.log(nextResource.getDescription());
				if(nextResource.getClass() == Image.class){
					Set<Entry<String, Object>> entires = AssetManager.get().entrySet();
					for(Entry<String, Object> ent : entires){
						Image entimage = (Image)ent.getValue();
						if(entimage.getResourceReference() == nextResource.getDescription()){
							entimage.setFilter(Image.FILTER_NEAREST);
							AssetManager.get().put(ent.getKey(), entimage);
							break;
						}
					}
				}
				resourcesLoaded++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			game.enterState(1);
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//container.setFullscreen(true);
		float width = container.getWidth();
		float height = container.getHeight();
		if(nextResource!=null){
			g.drawString("Loaded: "+lastLoaded, 100, 100);
			Resources.log("Loaded: "+lastLoaded);
			float temp = resourcesLoaded/totalResources;
			g.drawString(Math.round(temp*100) + "%", 105, 120);
			//renderLoadingBar(g, temp, width, height);
		} else{
		g.drawString("LOADING COMPLETE", 200, 200);
		}
	}
	private void renderLoadingBar(Graphics g, float progress, float width, float height){
		float barWidth = width/3;
		float barHeight = height/20;
		g.setColor(Color.white);
		g.drawRect(width/2-barWidth/2, height/2-barHeight/2, barWidth, barHeight);
		g.setColor(Color.blue);
		g.fillRect(width/2-barWidth/2, height/2-barHeight/2, (int)(progress*barWidth)-1, barHeight-1);
		g.setColor(Color.white);
	}	
	public int getID() {
		return 0;
	}

}
