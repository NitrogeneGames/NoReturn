package nitrogene.core;

import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.util.ArrayList;

import nitrogene.util.AppData;
import nitrogene.util.Button;
import nitrogene.util.BuyButton;
import nitrogene.util.EnumHull;
import nitrogene.util.Tab;
import nitrogene.weapon.EnumWeapon;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HangarState extends BasicGameState{

	private int width, height, repx, repy;
	private int scalefactor;
	private static int money;
	private int tabpagenumber, maxpagenumber;
	private int obserx, obsery;
	
	private Font font;
	
	//tabs
	private ArrayList<Tab> tablist;
	private ArrayList<BuyButton> buttonlist;
	private ArrayList<EnumWeapon> weapons;
	private Tab weapontab;
	private TextField textbox;
	
	//buttons for purchasing systems
	private Button startbutton, uppage, downpage;
	
	public HangarState(int width, int height){
		this.width = width;
		this.height = height;
		try {
			font = GlobalInformation.getMenuFont(40f);
		} catch (SlickException e) {

		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//change this for different amount of starting money:
		money = 500;
		tabpagenumber = 1;
		maxpagenumber = 0;
		tablist = new ArrayList<Tab>();
		//buttonlist2 = new ArrayList<BuyButton>();
		buttonlist = new ArrayList<BuyButton>();
		weapons = new ArrayList<EnumWeapon>();
		//textbox = new TextField(container, font, 100, 100,100, 100);
		this.repx = (int) Math.ceil(width/100.0);
		this.repy = (int) Math.ceil(height/100.0);
		
		this.scalefactor = (int) Math.floor(height/128);
		//Observatory 1 variables
		obserx=(width/2)-(165*scalefactor/2);
		obsery=(height/2)-(128*scalefactor/2);
		
		//Image buybuttonnormal = new Image("res/hangar/")
		
		try {
			weapontab = new Tab("", obserx+(11*scalefactor), obsery+(48*scalefactor), 15*scalefactor, 8*scalefactor, null);
			tablist.add(weapontab);
			maxpagenumber=2;
			textbox = new TextField(container, font, obserx+(14*scalefactor), obsery+(15*scalefactor),100*scalefactor, 7*scalefactor);
			textbox.setBackgroundColor(Color.transparent);
			textbox.setBorderColor(Color.transparent);
			textbox.setText("Ship Name");
			for(int j = 0; j<(EnumWeapon.values().length); j++) {
				EnumWeapon e = EnumWeapon.values()[j];
				int i = j;
				while (i > 6) {
					i = i-7;
				}
				BuyButton b = new BuyButton(e, obserx+(14*scalefactor), obsery+((8*i+59)*scalefactor), 100*scalefactor, 7*scalefactor, null);
				buttonlist.add(b);
			}
			
			
			
			startbutton = new Button("Start", obserx+(129*scalefactor), obsery+(116*scalefactor), 30*scalefactor, 12*scalefactor, null, 40);
			downpage = new Button("", obserx+(97*scalefactor), obsery+(35*scalefactor), 10*scalefactor, 9*scalefactor, null);
			uppage = new Button("", obserx+(108*scalefactor), obsery+(35*scalefactor), 10*scalefactor, 9*scalefactor, null);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Resources.updateGraphics(container);
		weapontab.update(container);
		startbutton.update(container);
		uppage.update(container);
		downpage.update(container);
		
		if(startbutton.isClicked()){
			GlobalInformation.addCraftData(new CraftData(weapons, textbox.getText(), EnumHull.NORMAL));
			AppData.saveShipFile();
			GlobalInformation.shipsLoaded = true;
			if(weapons.isEmpty()){
				System.out.println("No Weapons Found");
			}
			game.enterState(1);
		}
		if(uppage.isClicked() && tabpagenumber < maxpagenumber && weapontab.buttonDown()){
			tabpagenumber++;
		}
		if (downpage.isClicked() && tabpagenumber > 1 && weapontab.buttonDown()){
			tabpagenumber--;
		}
		
		if(weapontab.getButtonDown()){
			for(int i = (7*(tabpagenumber-1)); i < (tabpagenumber*7); i++) {
				if(i<buttonlist.size()) {
					buttonlist.get(i).update(container, weapons);
				}
			}	
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		//Render repeating background
		for(int x = 0; x < repx; x++){
			for (int y = 0; y < repy; y++){
				Image t = ((Image)AssetManager.get().get("hangarbackground")).copy();
				t.draw(x*100,y*100);
			}
		}
		
		Image observatory = ((Image)AssetManager.get().get("observatory")).copy();
		observatory.setFilter(Image.FILTER_NEAREST);
		observatory.draw(obserx, obsery, scalefactor);
		textbox.render(container, g);
		weapontab.render(g, ((Image)AssetManager.get().get("normaltab")).copy(), (Image)(AssetManager.get().get("pressedtab")));
		startbutton.render(g,((Image)AssetManager.get().get("startbutton")).copy(), null, null);
		uppage.render(g, ((Image)AssetManager.get().get("plusbutton")).copy(), null, null);
		downpage.render(g, ((Image)AssetManager.get().get("minusbutton")).copy(), null, null);
		
		if(weapontab.getButtonDown()){
			for(int i = (0+7*(tabpagenumber-1)); i < (tabpagenumber*7); i++) {
				if(i<buttonlist.size()) {
					buttonlist.get(i).render(g, scalefactor, ((Image)AssetManager.get().get("normalbuybutton")).copy(), ((Image)AssetManager.get().get("pressedbuybutton")).copy());			
				}
			}
		}
		
		g.setFont(this.font);
		if(money < 1000){
			g.drawString(Integer.toString(money), obserx+(13*scalefactor), obsery+(36*scalefactor));
		} else if(money > 1000){
			g.drawString(Integer.toString(money), obserx+(10*scalefactor), obsery+(36*scalefactor));
		}
		Image coinsImage = ((Image) AssetManager.get().get("coins")).copy();
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
