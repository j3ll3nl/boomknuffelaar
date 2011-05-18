package hu.project.innovation.configuration.model.rules;

import hu.project.innovation.configuration.model.ConfigurationService;
import hu.project.innovation.configuration.model.Layer;

import java.lang.reflect.Modifier;

import net.sourceforge.pmd.ast.SimpleNode;

public class InterfacesOnlyRule extends BackCallRule {
	
	protected void checkViolation(Class<?> toClass, Object data, SimpleNode node) {
		String calledName = toClass.getCanonicalName();
		
		if (isPackageChecked(calledName)) {
			// Get the fromLayer using the current checked class
			Layer fromLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(this.getClassName(node));

			// Get the toLayer using the package name from the called class
			Layer toLayer = ConfigurationService.getInstance().getLayerBySoftwareUnitName(calledName);
			
			if(fromLayer == null || toLayer == null) {
				return;
			}

			if (fromLayer.getId() != toLayer.getId() 
					&& toLayer.isInterfaceAccessOnly() 
					&& !toClass.isInterface()) 
			{
				// Add a violation if the rule is applied
				this.addViolationWithMessage(data, node, this.getViolationMessage(fromLayer, toLayer));
			}
			if(toLayer.isInterfaceAccessOnly() && Modifier.isPublic(toClass.getModifiers())) {
//				this.addViolationWithMessage(data, node, this.getViolationMessage(fromLayer, toLayer)+" And this call is public.");
			}
		}
	}
}
