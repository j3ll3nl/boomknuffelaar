package hu.project.innovation.configuration.model;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;

public class InterfacesOnlyRule extends BackCallRule {
	
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
				if (fromLayer.getId() != toLayer.getId() && toLayer.isInterfaceAccessOnly() && !node.getType().isInterface()) {
					// Add a violation if the rule is applied
					this.checkViolationType(data, node);
				}
			}
		}
		// Run the super function
		return super.visit(node, data);
	}
}
