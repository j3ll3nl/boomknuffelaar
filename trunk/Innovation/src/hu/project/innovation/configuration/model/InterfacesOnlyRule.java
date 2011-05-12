package hu.project.innovation.configuration.model;

import net.sourceforge.pmd.ast.SimpleNode;

public class InterfacesOnlyRule extends BackCallRule {
	
	public void checkViolation(String calledName, Object data, SimpleNode node, boolean isInterface) {
		if (isPackageChecked(calledName)) {
			// Get the fromLayer using the current checked class
			Layer fromLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(this.getPackageName(node));

			// Get the toLayer using the package name from the called class
			Layer toLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(calledName);
			
			if(fromLayer == null || toLayer == null) {
				return;
			}

			if (fromLayer.getId() != toLayer.getId() 
					&& toLayer.isInterfaceAccessOnly() 
					&& !isInterface) 
			{
				// Add a violation if the rule is applied
				this.addViolation(data, node);
			}
		}
	}
}
