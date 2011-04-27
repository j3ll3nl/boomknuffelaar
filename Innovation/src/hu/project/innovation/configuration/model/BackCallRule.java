package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;

public class BackCallRule extends AbstractRuleType {

	protected final String[] notCheckedPackages = new String[] { "java", "javax", "sun", "org.xml" };

	/**
	 * Use class and interface types to find a BackRule violation
	 * 
	 * @see hu.project.innovation.configuration.model.ConfigurationService
	 * @see net.sourceforge.pmd.AbstractJavaRule#visit(net.sourceforge.pmd.ast.ASTClassOrInterfaceType, java.lang.Object)
	 */
	public Object visit(ASTClassOrInterfaceType node, Object data) {
		// Only check if we have a type
		if (node.getType() != null) {
			// Get the called package name
			String calledPackageName = node.getType().getPackage().getName();

			// Only check certain packages
			if (isPackageChecked(calledPackageName)) {

				// Get the fromLayer using the current checked class
				String fromLayer = ConfigurationService.getInstance().getLayerNameBySoftwareUnitName(this.getPackageName(node));

				// Get the toLayer using the package name from the called class
				String toLayer = ConfigurationService.getInstance().getLayerNameBySoftwareUnitName(calledPackageName);

				// Log.i(this,"from: " + this.getPackageName(node) + " to: " + calledPackageName);
				// Log.i(this,"from: " + fromLayer + " to: " + toLayer);

				// Check if the rule is applied for these two layers and this rule
				if (ConfigurationService.getInstance().isRuleApplied(fromLayer, toLayer, this.getClass().getSimpleName())) {
					// Add a violation if the rule is applied
					this.checkViolationType(data, node,this);
				}
			}
		}
		// Run the super function
		return super.visit(node, data);
	}
	
	/**
	 * Checks the violation type on given information.
	 * 
	 * @param data
	 * @param node
	 * @param ast
	 */
	public void checkViolationType(Object data,ASTClassOrInterfaceType node,AbstractRuleType ast){
		// Rule specific information
		String RuleName = "Back-call Rule";
		
		//Variables to check on
		String type = node.getType().getName();
		
		String msg = "There is no violationtype defined for "+ RuleName+".";
		
		//Checks with message
		//msg =(type.equals(type))? "" : "";
		
		this.addViolationWithMessage(data, node, msg);
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
