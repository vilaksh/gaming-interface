package utilityObjects;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils 
{
	private static boolean _verifyNode(Node node)
	{
		if(node.getNodeType() != Node.ELEMENT_NODE)
		{
			Logger.LogError("Xml document passed was not paresed properly");
			Logger.LogError("Some error occured during parsing of " +
					node.getNodeName() + " tag");
			return false;					
		}
		return true;
	}
	
	public static Element ParseXml(File inputFile)
	{
		Element ret = null;
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(inputFile);
	
			doc.getDocumentElement().normalize();			
			ret = doc.getDocumentElement();
		}
		catch(Exception e)
		{
			Logger.LogError(e.getMessage());
		}
		
		return ret;
	}
	
	public static String GetNodeValue(Node node, String tag)
	{
		String value = null;
		
		Element element = (Element) node;
		NodeList nodeList = element.getElementsByTagName(tag);
		
		if(nodeList.getLength() != 1)
		{
			Logger.LogError("Please verify if the xml file has missed " +
					"out/contains multiple entries with tag " + tag);
		}
		
		Node tagNode = nodeList.item(0);
		if(!_verifyNode(tagNode))			
			return null;			
		
		element = (Element) tagNode;
		value = ((Node) element.getChildNodes().item(0)).getNodeValue();
		
		return value;
	}
}
