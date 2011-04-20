package hu.project.innovation.configuration.model;

import java.util.List;

import org.jaxen.JaxenException;

import hu.project.innovation.Logger;
import hu.project.innovation.XMLable;

import net.sourceforge.pmd.AbstractJavaRule;
import net.sourceforge.pmd.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

public abstract class AbstractRuleType 
	extends AbstractJavaRule implements XMLable {

	private String name, description;
	private int priority;

	protected AbstractRuleType() {
		Logger.getInstance().log(this.getClass().getSimpleName());
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
	
	private String formattedName() {
		return this.name.replaceAll("(.)([A-Z])", "$1_$2").toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	protected String getPackageName(SimpleNode node) {
		try {			
			List<ASTPackageDeclaration> packages = 
				node.findChildNodesWithXPath("//PackageDeclaration");
			String packageName = packages.get(0).getPackageNameImage();
			
			return packageName;
		} catch (JaxenException e) {
			e.printStackTrace();
		}    	
    	return null;
    }

	public abstract void checkViolation();
	
	public String toXML() {
		String xml = "";
		
		xml += "<rule name=\""+this.formattedName()+"\" ";
		xml += "message=\""+this.name+"\" ";
		xml += "class=\""+this.getClass().getPackage()+this.name+"\">";
		xml += "<description>"+this.description+"</description>";
		xml += "<priority>"+this.priority+"</priority>";
		xml += "</rule>";
		
		return xml;
	}
}
