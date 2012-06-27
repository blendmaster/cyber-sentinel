package sate.cybersentinel.message.xml;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import sate.cybersentinel.message.Location;
import sate.cybersentinel.message.Message;
import sate.cybersentinel.message.MutableMessage;

/**
 * DOM-Based Reader for messages exported from OpenSim
 * 
 * @author Jared Hance
 */
public class MessageXmlReader {
	private DocumentBuilder builder;
	
	public MessageXmlReader()
	{
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		try {
			this.builder = f.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public Message readMessage(String xml) {
		MutableMessage message = new MutableMessage();
		
		try {
			Document doc = builder.parse(xml);
			Element root = doc.getDocumentElement();
			
			NodeList children = root.getChildNodes();
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if(child.getNodeType() == Node.ELEMENT_NODE) {
					String name = child.getNodeName();
					switch(name) {
					case "channel":
						message.setChannel(Integer.parseInt(getElementText(child)));
						break;
					case "contents":
						message.setContents(getElementText(child));
						break;
					case "receiver":
						message.setReceiver(getElementText(child));
						break;
					case "sender":
						message.setSender(getElementText(child));
						break;
					case "time":
						message.setTime(new Date(Long.parseLong(getElementText(child))));
						break;
					case "location":
						message.setLocation(parseLocation(child));
						break;
					}
				}
			}
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	private String getElementText(Node node) {
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeType() == Node.TEXT_NODE) {
				return child.getTextContent();
			}
		}
	
		throw new MessageXmlParsingException("Element " + node.getNodeName() + " has no Text child node");
	}
	
	private Location parseLocation(Node node) {
		double x = 0, y = 0, z = 0;
		String region = "";
		boolean foundRegion = false, foundX = false, foundY = false, foundZ = false;
		
		NodeList children = node.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			if(child.getNodeType() == Node.ELEMENT_NODE) {
				switch(child.getNodeName()) {
				case "region":
					foundRegion = true;
					region = getElementText(child);
					break;
				case "x":
					foundX = true;
					x = Double.parseDouble(getElementText(child));
					break;
				case "y":
					foundY = true;
					y = Double.parseDouble(getElementText(child));
					break;
				case "z":
					foundZ = true;
					z = Double.parseDouble(getElementText(child));
					break;
				}
			}
		}
		
		if(foundRegion && foundX && foundY && foundZ) {
			return new Location(region, x, y, z);
		}
		else {
			throw new MessageXmlParsingException("Missing a coordinate of location");
		}
	}
}
