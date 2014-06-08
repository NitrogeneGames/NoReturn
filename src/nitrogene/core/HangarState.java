package nitrogene.core;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.AppData;
import nitrogene.util.Button;
import nitrogene.util.BuyButton;
import nitrogene.util.EnumHull;
import nitrogene.util.Tab;
import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HangarState extends BasicGameState{

	private int width, height, repx, repy;
	private Image backgroundimg, observatory;
	private int scalefactor;
	private static int money;
	private int tabpagenumber, maxpagenumber;
	private int obserx, obsery;
	
	Image coinsImage;
	java.awt.Font mainFont;
    org.newdawn.slick.UnicodeFont uniFont;
	
	//tabs
	private ArrayList<Tab> tablist;
	private ArrayList<BuyButton> buttonlist, buttonlist2;
	private ArrayList<EnumWeapon> weapons;
	private Tab weapontab;
	
	//buttons for purchasing systems
	private BuyButton basiclaserbutton, splitlaserbutton, splitlaser2button, pulsarbutton, pulsar2button, pdibutton, mininglaserbutton, veloxlaserbutton, velox2laserbutton, immineolaserbutton, immineo2laserbutton,
	demolitionlaserbutton, precisionlaserbutton, basicbeambutton, microbeambutton, valentulusbeambutton;
	private Button startbutton, uppage, downpage;
	
	public HangarState(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//change this for different amount of starting money:
		money = 500;
		tabpagenumber = 1;
		maxpagenumber = 0;
		tablist = new ArrayList<Tab>();
		buttonlist2 = new ArrayList<BuyButton>();
		buttonlist = new ArrayList<BuyButton>();
		weapons = new ArrayList<EnumWeapon>();
		
		try {
			mainFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,org.newdawn.slick.util.ResourceLoader.getResourceAsStream("fonts/acknowtt.ttf"));
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        mainFont = mainFont.deriveFont(java.awt.Font.PLAIN, 40.f);
        uniFont = new org.newdawn.slick.UnicodeFont(mainFont);
        uniFont.addAsciiGlyphs();
        org.newdawn.slick.font.effects.ColorEffect a = new org.newdawn.slick.font.effects.ColorEffect();
        a.setColor(Color.white);
        uniFont.getEffects().add(a);
        uniFont.loadGlyphs();
		
		backgroundimg = new Image("res/hangar/background.png");
		observatory = new Image("res/hangar/observatory1.png");
		observatory.setFilter(Image.FILTER_NEAREST);
		
		this.repx = (int) Math.ceil(width/100.0);
		this.repy = (int) Math.ceil(height/100.0);
		
		this.scalefactor = (int) Math.floor(height/128);
		obserx = (width/2)-(observatory.getWidth()*scalefactor/2);
		obsery = (height/2)-(observatory.getHeight()*scalefactor/2);
		
		Image normalimgtab = new Image("res/hangar/tab2.png");
		Image pressedimgtab = new Image("res/hangar/tabhighlighted2.png");
		Image normalimgbuybutton = new Image("res/hangar/button2.png");
		Image pressedimgbuybutton = new Image("res/hangar/buttonhighlighted2.png");
		Image standardbutton = new Image("res/button/logomenubutton2.png");
		coinsImage = new Image("res/hangar/twocoins.png");
		coinsImage.setFilter(Image.FILTER_NEAREST);
		
		//Image buybuttonnormal = new Image("res/hangar/")
		
		try {
			weapontab = new Tab("", obserx+(11*scalefactor), obsery+(48*scalefactor), 15*scalefactor, 8*scalefactor, normalimgtab, null, pressedimgtab, null);
			tablist.add(weapontab);
			maxpagenumber=2;
			
			basiclaserbutton = new BuyButton("Basic Laser", 20, obserx+(14*scalefactor), obsery+(59*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			splitlaserbutton = new BuyButton("Split Laser", 50, obserx+(14*scalefactor), obsery+(67*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			splitlaser2button = new BuyButton("Split Laser Mk.II", 80, obserx+(14*scalefactor), obsery+(75*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			pulsarbutton = new BuyButton("Pulsar", 80, obserx+(14*scalefactor), obsery+(83*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			pulsar2button = new BuyButton("Pulsar Mk.II", 150, obserx+(14*scalefactor), obsery+(91*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			veloxlaserbutton = new BuyButton("Velox Laser", 250, obserx+(14*scalefactor), obsery+(99*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			velox2laserbutton = new BuyButton("Velox Laser Mk.II", 350, obserx+(14*scalefactor), obsery+(107*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			
			immineolaserbutton = new BuyButton("Immineo Laser", 200, obserx+(14*scalefactor), obsery+(59*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			immineo2laserbutton = new BuyButton("Immineo Laser Mk.II", 300, obserx+(14*scalefactor), obsery+(67*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			demolitionlaserbutton = new BuyButton("Demolition Laser", 300, obserx+(14*scalefactor), obsery+(75*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			precisionlaserbutton = new BuyButton("Precision Laser", 100, obserx+(14*scalefactor), obsery+(83*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			pdibutton = new BuyButton("Point Defense Interceptor", 40, obserx+(14*scalefactor), obsery+(91*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);
			mininglaserbutton = new BuyButton("Mining Laser", 100, obserx+(14*scalefactor), obsery+(99*scalefactor), 100*scalefactor, 7*scalefactor, normalimgbuybutton, pressedimgbuybutton, null);

			buttonlist.add(basiclaserbutton);
			buttonlist.add(splitlaserbutton);
			buttonlist.add(splitlaser2button);
			buttonlist.add(pulsarbutton);
			buttonlist.add(pulsar2button);
			buttonlist.add(veloxlaserbutton);
			buttonlist.add(velox2laserbutton);
			buttonlist2.add(pdibutton);
			buttonlist2.add(mininglaserbutton);
			buttonlist2.add(immineolaserbutton);
			buttonlist2.add(immineo2laserbutton);
			buttonlist2.add(demolitionlaserbutton);
			buttonlist2.add(precisionlaserbutton);
			
			startbutton = new Button("Start", obserx+(129*scalefactor), obsery+(116*scalefactor), 30*scalefactor, 12*scalefactor, new Image("res/hangar/startbuttonhangar.png"), null, null, null, 40);
			downpage = new Button("", obserx+(97*scalefactor), obsery+(35*scalefactor), 10*scalefactor, 9*scalefactor, new Image("res/hangar/minusbutton.png"), null, null, null);
			uppage = new Button("", obserx+(108*scalefactor), obsery+(35*scalefactor), 10*scalefactor, 9*scalefactor, new Image("res/hangar/plusbutton.png"), null, null, null);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		weapontab.update(container);
		startbutton.update(container);
		uppage.update(container);
		downpage.update(container);
		
		if(startbutton.isClicked()){
			GlobalInformation.addCraftData(new CraftData(weapons, "Ship Test", EnumHull.NORMAL));
			AppData.saveShipFile();
			if(weapons.isEmpty()){
				//System.out.println("CHECKPOINT 1 FAILURE");
			}
			game.enterState(1);
		}
		if(uppage.isClicked() && tabpagenumber < maxpagenumber && weapontab.buttonDown()){
			tabpagenumber++;
		}
		if (downpage.isClicked() && tabpagenumber > 1 && weapontab.buttonDown()){
			tabpagenumber--;
		}
		
		if(weapontab.getButtonDown() && tabpagenumber == 1){
			basiclaserbutton.update(container, weapons, EnumWeapon.BASIC);
			splitlaserbutton.update(container, weapons, EnumWeapon.SPLIT);
			splitlaser2button.update(container, weapons, EnumWeapon.SPLIT2);
			pulsarbutton.update(container, weapons, EnumWeapon.PULSAR);
			pulsar2button.update(container, weapons, EnumWeapon.PULSAR2);
			veloxlaserbutton.update(container, weapons, EnumWeapon.VELOX);
			velox2laserbutton.update(container, weapons, EnumWeapon.VELOX2);
		}
		
		if(weapontab.getButtonDown() && tabpagenumber == 2){
			immineolaserbutton.update(container, weapons, EnumWeapon.IMMINEO);
			immineo2laserbutton.update(container, weapons, EnumWeapon.IMMINEO2);
			demolitionlaserbutton.update(container, weapons, EnumWeapon.DEMOLITION);
			precisionlaserbutton.update(container, weapons, EnumWeapon.PRECISION);
			pdibutton.update(container, weapons, EnumWeapon.PDI);
			mininglaserbutton.update(container, weapons, EnumWeapon.MINING);
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//Render repeating background
		for(int x = 0; x < repx; x++){
			for (int y = 0; y < repy; y++){
				backgroundimg.draw(x*100,y*100);
			}
		}
		
		observatory.draw(obserx, obsery, scalefactor);
		
		weapontab.render(g);
		startbutton.render(g);
		uppage.render(g);
		downpage.render(g);
		
		if(weapontab.getButtonDown()){
			if(tabpagenumber ==1){
				for(Button b : buttonlist){
					b.render(g, scalefactor);
				}
			}else if(tabpagenumber == 2){
				for(Button b: buttonlist2){
					b.render(g, scalefactor);
				}
			}
		}
		
		g.setFont(uniFont);
		if(money < 1000){
			g.drawString(Integer.toString(money), obserx+(13*scalefactor), obsery+(36*scalefactor));
		} else if(money > 1000){
			g.drawString(Integer.toString(money), obserx+(10*scalefactor), obsery+(36*scalefactor));
		}
        coinsImage.draw(obserx+(24*scalefactor), obsery+(37*scalefactor), scalefactor/3);
	}
	
	@Override
	public int getID() {
		return 3;
	}
	
	public static void changeMoney(int m){
		money+=m;
	}
	
	public static int getMoney(){
		return money;
	}
	
}
