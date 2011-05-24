package hu.project.innovation.configuration.model.rules;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.ast.SimpleNode;

public class BackCallRule extends AbstractRuleType {

	private final String[] notCheckedPackages = new String[] { "java", "javax", "sun", "org.xml", "net.sourceforge.pmd" };

	/**
	 * Use class and interface types to find a BackRule violation
	 * 
	 * @see hu.project.innovation.configuration.model.ConfigurationService
	 * @see net.sourceforge.pmd.AbstractJavaRule#visit(net.sourceforge.pmd.ast.ASTClassOrInterfaceType, java.lang.Object)
	 */
	public Object visit(ASTClassOrInterfaceType node, Object data) {
		// Only check if we have a type
		if (node.getType() != null) {
			this.checkViolation(node.getType(), data, node);
		}
		// Run the super function
		return super.visit(node, data);
	}

	public Object visit(ASTPrimaryExpression node, Object data) {
		if(node.getType() != null) {
			this.checkViolation(node.getType(), data, node);
		}		
		return super.visit(node, data);
	}

	protected void checkViolation(Class<?> toClass, Object data, SimpleNode node) {
		this.initArchitecture();

		String calledName = toClass.getCanonicalName();

		if (isPackageChecked(calledName)) {

			// Get the fromLayer using the current checked class
			Layer fromLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(this.getClassName(node));

			// Get the toLayer using the package name from the called class
			Layer toLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(calledName);

			// Log.i(this,"from: " + this.getPackageName(node) + " to: " + calledName);
			// Log.i(this,"from: " + fromLayer + " to: " + toLayer);
			if (fromLayer != null && toLayer != null) {
				// Check if the rule is applied for these two layers and this rule
				if (fromLayer.hasAppliedRule(this.getClass().getSimpleName(), toLayer)) {
					this.addViolationWithMessage(data, node, this.getViolationMessage(fromLayer, toLayer));
				}
			}
		}
	}

	protected String getViolationMessage(Layer fromLayer, Layer toLayer) {
		return fromLayer.getName() + " calls " + toLayer.getName() + ": " + this.getMessage();
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
}
