package hu.project.innovation.configuration.model.rules;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;

import java.io.File;
import java.util.List;

import net.sourceforge.pmd.AbstractJavaRule;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

import org.jaxen.JaxenException;

public abstract class AbstractRuleType extends AbstractJavaRule {
	public final static String back_call = "BackCallRule";
	public final static String skip_call = "SkipLayerRule";
	public final static String interface_only = "InterfacesOnlyRule";

	private final String[] notCheckedPackages = new String[] { "java", "javax", "sun", "org.xml", "net.sourceforge.pmd" };

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
	@SuppressWarnings("unchecked")
	protected String getClassName(SimpleNode node) {
		try {
			List<ASTClassOrInterfaceDeclaration> classes = node.findChildNodesWithXPath("//ClassOrInterfaceDeclaration");
			return classes.get(0).getImage();
		} catch (Exception e) {
			return "";
		}
	}

	protected String getCanonicalName(SimpleNode node) {
		return this.getPackageName(node) + "." + this.getClassName(node);
	}

	protected boolean hasLayerRule(String callingClass, String calledClass) {
		this.initArchitecture();

		if (isPackageChecked(calledClass)) {

			// Get the fromLayer using the current checked class
			Layer fromLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(callingClass);

			// Get the toLayer using the package name from the called class
			Layer toLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(calledClass);

			// Log.i(this,"from: " + this.getPackageName(node) + " to: " + calledName);
			// Log.i(this,"from: " + fromLayer + " to: " + toLayer);
			if (fromLayer != null && toLayer != null) {
				// Check if the rule is applied for these two layers and this rule
				if (fromLayer.hasAppliedRule(this.getClass().getSimpleName(), toLayer)) {
					return true;
				}
			}
		}
		return false;
	}

	protected String getViolationMessage(Layer fromLayer, Layer toLayer) {
		return fromLayer.getLayerName() + " calls " + toLayer.getLayerName() + ": " + this.getMessage();
	}

	protected void initArchitecture() {
		if (!ConfigurationService.getInstance().hasArchitectureDefinition()) {
			try {
				ConfigurationService.getInstance().importConfiguration(new File("./xml/architecture.xml"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Check if a package needs to be checked. We have defined a number of packages wich do not need checking. These include java and javax packages for example.
	 * 
	 * @param packageName
	 * @return
	 */
	protected boolean isPackageChecked(String packageName) {
		for (String packageStart : this.notCheckedPackages) {
			if (packageName.startsWith(packageStart)) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return getName();
	}
}
