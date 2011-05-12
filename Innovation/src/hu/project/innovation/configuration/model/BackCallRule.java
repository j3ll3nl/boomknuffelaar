package hu.project.innovation.configuration.model;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

public class BackCallRule extends AbstractRuleType {

	private final String[] notCheckedPackages = new String[] { "java", "javax", "sun", "org.xml" };

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
			String calledName = node.getType().getCanonicalName();
			
			this.checkViolation(calledName, data, node, node.getType().isInterface());
		}
		// Run the super function
		return super.visit(node, data);
	}
	
	public Object visit(ASTMethodDeclaration node, Object data) {
		try {
			Class<?> c = node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class).getType();
			// Package and class name:
			String calledName = c.getCanonicalName();
			// Plus method name
			calledName += "."+node.getMethodName();
			
			checkViolation(calledName, data, node, c.isInterface());
		} catch(Exception e) {}		
		
		return super.visit(node, data);
	}
	
	public void checkViolation(String calledName, Object data, SimpleNode node) {
		this.checkViolation(calledName, data, node, false);
	}
	
	public void checkViolation(String calledName, Object data, SimpleNode node, boolean isInterface) {
		if (isPackageChecked(calledName)) {
	
			// Get the fromLayer using the current checked class
			Layer fromLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(this.getPackageName(node));
	
			// Get the toLayer using the package name from the called class
			Layer toLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(calledName);
			
//			Log.i(this,"from: " + this.getPackageName(node) + " to: " + calledName);
//			Log.i(this,"from: " + fromLayer + " to: " + toLayer);
			if(fromLayer == null || toLayer == null) return;
			
			// Check if the rule is applied for these two layers and this rule
			if (fromLayer.hasAppliedRule(this.getClass().getSimpleName(), toLayer)) {
				this.addViolation(data, node);
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
}
