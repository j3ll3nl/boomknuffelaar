package hu.project.innovation.configuration.controller.xml;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ImportController extends Controller {

	public ImportController() {
		super();
	}

	public void read(File file) throws Exception {
		ConfigurationService service = ConfigurationService.getInstance();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();

		String rootElement = doc.getDocumentElement().getNodeName();
		if (rootElement.trim().equals("configuration")) {
			System.out.println("Root element: " + rootElement);
			NodeList architectureNode = doc.getElementsByTagName("architecture");
			for (int a = 0; a < architectureNode.getLength(); a++) {
				Element architectureElement = (Element) architectureNode.item(a);
				NodeList architectureName = architectureElement.getElementsByTagName("name");
				NodeList architectureDesc = architectureElement.getElementsByTagName("description");

				service.newConfiguration(getValue(architectureName), getValue(architectureDesc));

				// Doorlopen van de inhoud van Layers
				NodeList layersNode = doc.getElementsByTagName("layers");
				for (int b = 0; b < layersNode.getLength(); b++) {
					Element layerElement = (Element) layersNode.item(b);

					NodeList layerId = layerElement.getElementsByTagName("id");
					NodeList layerName = layerElement.getElementsByTagName("name");
					NodeList layerDescription = layerElement.getElementsByTagName("description");
					NodeList layerInterface = layerElement.getElementsByTagName("interfaceAccessOnly");

					Layer layer = service.newLayer(getValue(layerName), getValue(layerDescription));
					layer.setId(Integer.parseInt(getValue(layerId)));
					// layer.setInterfaceAccesOnly(Integer.parseInt(getValue(layerInterface)));

					NodeList softwareUnitsNode = doc.getElementsByTagName("softwareUnits");
					for (int c = 0; c < softwareUnitsNode.getLength(); c++) {

						NodeList appliedRulesNode = doc.getElementsByTagName("appliedRules");
						for (int d = 0; d < appliedRulesNode.getLength(); d++) {

						}
					}
				}
			}
		} else {
			throw new Exception("Corrupt XML format");
		}
	}

	public String getValue(NodeList list) {
		Element fstNmElmnt = (Element) list.item(0);
		NodeList fstNm = fstNmElmnt.getChildNodes();

		return ((Node) fstNm.item(0)).getNodeValue();
	}

}
