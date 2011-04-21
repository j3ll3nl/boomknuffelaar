package hu.project.innovation.configuration.model;

import hu.project.innovation.Log;
import hu.project.innovation.XMLable;

import java.util.List;

import net.sourceforge.pmd.AbstractJavaRule;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

import org.jaxen.JaxenException;

public abstract class AbstractRuleType extends AbstractJavaRule implements XMLable {

	private String name, description;
	private int priority;

	protected AbstractRuleType() {
		Log.i(this,"AbstractRuleType()");		
		this.setName(this.getClass().getSimpleName());
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * The name formatted for xml. It changes AnExampleClass to an_example_class
	 * 
	 * @return
	 */
	private String formattedName() {
		return this.getName().replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
	}
	
	/**
	 * Get the packagename from a <code>SimpleNode</code>. 
	 * 
	 * @param node
	 * @return Returns the packagename of the node or an empty <code>String</code>
	 * if the package could not be retrieved
	 */
	@SuppressWarnings("unchecked")
	protected String getPackageName(SimpleNode node) {
		try {
			List<ASTPackageDeclaration> packages = node.findChildNodesWithXPath("//PackageDeclaration");
			return packages.get(0).getPackageNameImage();
		} catch (JaxenException e) {
			return "";
		}
	}
	
	/**
	 * Get the classname from a <code>SimpleNode</code>
	 * 
	 * @param node
	 * @return The classname of this node or an empty <code>String</code> if 
	 * the classname could not be found
	 */
	protected String getClassName(SimpleNode node) {
		if (node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class) == null) {
			return "";
		} else {
			return node.getScope().getEnclosingClassScope().getClassName() == null ? "" : node.getScope().getEnclosingClassScope().getClassName();
		}
	}

	public String toXML() {
		String xml = "";

		xml += "<rule name=\"" + this.formattedName() + "\" ";
		xml += "message=\"" + this.name + "\" ";
		xml += "class=\"" + this.getClass().getPackage() + this.name + "\">";
		xml += "<description>" + this.description + "</description>";
		xml += "<priority>" + this.priority + "</priority>";
		xml += "</rule>";

		return xml;
	}
}
