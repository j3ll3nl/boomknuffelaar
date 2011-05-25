package hu.project.innovation.configuration.controller.xml;

import hu.project.innovation.configuration.model.ConfigurationService;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Controller {
	protected ConfigurationService service = ConfigurationService.getInstance();

	protected String setting = "setting";
	protected String project_path = "project";
	protected String output_path = "output";
	protected String output_type = "output_type";

	protected String archtiecture = "architecture";
	protected String architecture_name = "name";
	protected String architecture_description = "description";

	protected String layer = "layer";
	protected String layer_id = "id";
	protected String layer_name = "name";
	protected String layer_description = "description";
	protected String layer_interfaceAccesOnly = "interfaceAccessOnly";

	protected String softwareunit = "softwareUnit";
	protected String softwareunit_name = "name";
	protected String softwareunit_type = "type";

	protected String exception = "exception";
	protected String exception_name = "name";
	protected String exception_type = "type";

	protected String appliedrule = "appliedRule";
	protected String appliedrule_ruleType = "ruleType";
	protected String appliedrule_tolayer = "toLayer";

	/**
	 * Method for getting the given value from the given element.
	 * 
	 * @param element The element Object
	 * @param value The value
	 * @return The value or null
	 */
	public String getValue(Element element, String value) {
		NodeList list = element.getElementsByTagName(value);

		Element fstNmElmnt = (Element) list.item(0);
		NodeList fstNm = fstNmElmnt.getChildNodes();

		return ((Node) fstNm.item(0)).getNodeValue();
	}

}
