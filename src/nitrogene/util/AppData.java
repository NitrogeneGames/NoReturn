package nitrogene.util;

import java.awt.Canvas;
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nitrogene.core.CraftData;
import nitrogene.core.GameState;
import nitrogene.core.GlobalInformation;
import nitrogene.core.Resources;
import nitrogene.weapon.EnumWeapon;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppData {
	public static String userDataRoot = System.getenv("APPDATA");
	public static void loadOptions() {
		try {
			File fXmlFile = new File(userDataRoot + "\\NoReturn\\options.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			if(doc.getDocumentElement().getNodeName() != "optionlist") {
				Resources.log("ERROR: CORRUPTRED OPTIONS FILE CP 1");
				return;
			}
			GlobalInformation.musiclevel = Float.parseFloat(doc.getDocumentElement().getAttribute("music"));
			GlobalInformation.sfxlevel = Float.parseFloat(doc.getDocumentElement().getAttribute("sfx"));
			GlobalInformation.alarmlevel = Float.parseFloat(doc.getDocumentElement().getAttribute("alarm"));
		} catch (Exception e) {
			Resources.log("ERROR: CORRUPTED OPTIONS FILE CP 2");
			e.printStackTrace();
		}
	}
	public static void loadRez() {
		try {
			File fXmlFile = new File(userDataRoot + "\\NoReturn\\rez.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			if(doc.getDocumentElement().getNodeName() != "rez") {
				Resources.log("ERROR: CORRUPTRED REZ FILE CP 1");
				return;
			}
			int width = (int)Float.parseFloat(doc.getDocumentElement().getAttribute("width"));
			int height = (int)Float.parseFloat(doc.getDocumentElement().getAttribute("height"));
			float zoom = Float.parseFloat(doc.getDocumentElement().getAttribute("zoom"));
			Display.setDisplayModeAndFullscreen(new DisplayMode(width, height));
			GameState.currentZoom = zoom;
		} catch (Exception e) {
			Resources.log("ERROR: REZ FILE CP 2");
			e.printStackTrace();
		}
	}
	public static void log(String l) throws IOException {
		File file = new File(userDataRoot + "\\NoReturn\\log.txt");	  
		file.createNewFile();
		
		FileWriter writer = new FileWriter(file);
		writer.write(l);
		writer.close();
	}
	public static void saveRez() {
		   try {
			   
			   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			   Document doc = docBuilder.newDocument();
			   
			   Element rootElement = doc.createElement("rez");
			   doc.appendChild(rootElement);
			   Attr s4 = doc.createAttribute("width");
			   s4.setValue("" + Display.getWidth());
			   Attr s5 = doc.createAttribute("height");
			   s5.setValue("" + Display.getHeight());
			   Attr s6 = doc.createAttribute("zoom");
			   s6.setValue("" + GameState.currentZoom);
			   rootElement.setAttributeNode(s4);
			   rootElement.setAttributeNode(s5);
			   rootElement.setAttributeNode(s6);
			   TransformerFactory transformerFactory = TransformerFactory.newInstance();
			   Transformer transformer = transformerFactory.newTransformer();
			   DOMSource source = new DOMSource(doc);
			   StreamResult result = new StreamResult(new File(userDataRoot + "\\NoReturn\\rez.xml"));
			   transformer.transform(source, result);
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	 	}
	public static void saveOptions() {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("optionlist");
			doc.appendChild(rootElement);
			Attr s1 = doc.createAttribute("music");
			s1.setValue("" + GlobalInformation.musiclevel);
			Attr s2 = doc.createAttribute("sfx");
			s2.setValue("" + GlobalInformation.sfxlevel);
			Attr s3 = doc.createAttribute("alarm");
			s3.setValue("" + GlobalInformation.alarmlevel);
			rootElement.setAttributeNode(s1);
			rootElement.setAttributeNode(s2);
			rootElement.setAttributeNode(s3);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(userDataRoot + "\\NoReturn\\options.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void runInit() {
		File folder = new File(userDataRoot + "\\NoReturn");
		if(!folder.exists()) { //Checks if folder exists
			folder.mkdir(); //Creates root folder
		}
  
		if(new File(userDataRoot + "\\NoReturn\\ships.xml").exists()) {
			loadShipFile();
		} else {
			GlobalInformation.shipsLoaded = false;
		}
  
  
		if(new File(userDataRoot + "\\NoReturn\\options.xml").exists()) {
			loadOptions();
		}
		if(new File(userDataRoot + "\\NoReturn\\rez.xml").exists()) {
			loadRez();
		}
  

	}
	public void runSave() {
		saveShipFile();
	}
	public static void loadShipFile() {
		try {
			ArrayList<CraftData> lships = new ArrayList<CraftData>();
			File fXmlFile = new File(userDataRoot + "\\NoReturn\\ships.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			if(doc.getDocumentElement().getNodeName() != "shiplist") {
				Resources.log("ERROR: CORRUPTRED SHIP FILE CP 1");
				return;
			}
			NodeList nList = doc.getElementsByTagName("ship");
			for(int i = 0; i < nList.getLength(); i++) {
				CraftData nNodeShip;
				String name = "";
				ArrayList<EnumWeapon> weps = new ArrayList<EnumWeapon>();
				Node nNode =  nList.item(i);
				//if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eNode = (Element) nNode;
				name = eNode.getAttribute("name");
				Element wl = (Element) eNode.getElementsByTagName("weaponlist").item(0);
				NodeList weapons = wl.getElementsByTagName("weapon");
				for(int j = 0; j < weapons.getLength(); j++) {
					String fname = weapons.item(j).getTextContent();
					EnumWeapon w = EnumWeapon.getWeaponFromFormalName(fname);
					if(w!=null) {
						weps.add(w);
					}
				}
				//}
				nNodeShip = new CraftData(weps, name, EnumHull.NORMAL);
				lships.add(nNodeShip);
			}
			GlobalInformation.setCraftDataOverride(lships);
		} catch (Exception e) {
			Resources.log("ERROR: CORRUPTED SHIP FILE CP 2");
			e.printStackTrace();
		}
	}
	public static void saveShipFile() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			Element rootElement = doc.createElement("shiplist");
			doc.appendChild(rootElement);
			for(int i = 0; i < GlobalInformation.getCraftData().size(); i++) {
				
				Element ship = doc.createElement("ship");
				rootElement.appendChild(ship);
  
				Element weapons = doc.createElement("weaponlist");
				ship.appendChild(weapons);
    
				for(int j = 0; j < GlobalInformation.getCraftData().get(i).weapons.size(); j++) {
					Element s = doc.createElement("weapon");
					s.appendChild(doc.createTextNode(GlobalInformation.getCraftData().get(i).weapons.get(j).formalname));
					weapons.appendChild(s);
				} 
    
				Attr sname = doc.createAttribute("name");
				sname.setValue(GlobalInformation.getCraftData().get(i).name);
				ship.setAttributeNode(sname);
   
			} 
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(userDataRoot + "\\NoReturn\\ships.xml"));
			transformer.transform(source, result);
  
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
 
}