package hu.project.innovation.configuration.model;

//import hu.project.innovation.utils.Log;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.ast.SimpleNode;

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
				Layer fromLayer = ConfigurationService.getInstance().getLayerNameBySoftwareUnitName(this.getPackageName(node));

				// Get the toLayer using the package name from the called class
				Layer toLayer = ConfigurationService.getInstance().getLayerNameBySoftwareUnitName(calledPackageName);

//				 Log.i(this,"from: " + this.getPackageName(node) + " to: " + calledPackageName);
//				 Log.i(this,"from: " + fromLayer + " to: " + toLayer);

				// Check if the rule is applied for these two layers and this rule
				if (ConfigurationService.getInstance().isRuleApplied(fromLayer, toLayer, this.getClass().getSimpleName())) {
					// Add a violation if the rule is applied
					this.checkViolationType(data, node);
				}
			}
		}
		// Run the super function
		return super.visit(node, data);
	}

	protected void checkViolationType(Object data, SimpleNode node) {
		ASTClassOrInterfaceType checkedNode = null;

		// Check if we have a classOrInterface node
		if (node instanceof ASTClassOrInterfaceType) {
			checkedNode = (ASTClassOrInterfaceType) node;
		} else {
			return;
		}
		// Variables to check on
		String toClass = checkedNode.getType().getSimpleName();
		String toClassType = (checkedNode.getType().isInterface()) ? "Interface" : "Class";
		// String toIsSuperclass = (node.getType().isMemberClass())?"Memberclass":"Superclass";

		String msg = this.getMessage();

		// Checks with message
		msg = toClassType.equals("Class") ? "The call to " + toClass + " violates " + this.getName() : msg;

		this.addViolationWithMessage(data, checkedNode, this.getName() + ": " + msg);
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
