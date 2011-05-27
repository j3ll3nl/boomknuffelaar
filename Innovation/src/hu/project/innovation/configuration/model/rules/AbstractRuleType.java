package hu.project.innovation.configuration.model.rules;

import hu.project.innovation.configuration.model.ConfigurationService;

import java.io.File;
import java.util.List;

import net.sourceforge.pmd.AbstractJavaRule;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

import org.jaxen.JaxenException;

public abstract class AbstractRuleType extends AbstractJavaRule {
	public static String back_call = "BackCallRule";
	public static String skip_call = "SkipLayerRule";
	public static String interface_only = "InterfacesOnlyRule";
	

	protected AbstractRuleType() {
		this.setName(this.getClass().getSimpleName());
	}

	/**
	 * Get the packagename from a <code>SimpleNode</code>.
	 * 
	 * @param node
	 * @return Returns the packagename of the node or an empty <code>String</code> if the package could not be retrieved
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
	 * @return The classname of this node or an empty <code>String</code> if the classname could not be found
	 */
	protected String getClassName(SimpleNode node) {
		if (node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class) == null) {
			return "";
		} else {
			ASTClassOrInterfaceDeclaration c = node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class);
			if (c.getType() != null) {
				return c.getType().getCanonicalName();
			} else {
				return "";
			}
		}
	}

	protected void initArchitecture() {
		if (!ConfigurationService.getInstance().hasArchitectureDefinition()) {
			try {
				ConfigurationService.getInstance().importConfiguration(new File("architecture_definition.xml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String toString() {
		return getName();
	}
}
