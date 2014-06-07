package nitrogene.util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 








import nitrogene.core.CraftData;
import nitrogene.core.GlobalInformation;
import nitrogene.weapon.EnumWeapon;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AppData {
	public static String userDataRoot = System.getenv("APPDATA");
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
		if(doc.getDocumentElement().getNodeName() == "shipslist") {
			System.out.println("ERROR: CORRUPTRED SHIP FILE CP 1");
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
			 System.out.println("ERROR: CORRUPTED SHIP FILE CP 2");
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
