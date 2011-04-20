package hu.project.innovation.configuration.model;

import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;

public class BackCallRule extends AbstractRuleType {

	@Override
	public void checkViolation() {
		
	}
	
	public Object visit(ASTClassOrInterfaceType node, Object data) {
		String packageName = this.getPackageName(node);

        String className = "";
        if (node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class) == null) {
            className = "";
        } else {
            className = node.getScope().getEnclosingClassScope().getClassName() == null ? "" : node.getScope().getEnclosingClassScope().getClassName();
        }		
		if(node.getType()!=null) {
			
			String[] typePieces = node.getType().toString().split(" ");
			String type = typePieces[0];
			String name = typePieces[1];	
			
			if(!name.startsWith("java")) {
				String[] namePieces = name.split("(.)[A-Z]");
				String packageCalledName = namePieces[0];
				
				System.out.println();
				System.out.println(className + " calls:");
				System.out.println(name);
				
				String fromLayer = ConfigurationService.getInstance()
					.getLayerNameBySoftwareUnitName(packageName);
				
				String toLayer = ConfigurationService.getInstance()
					.getLayerNameBySoftwareUnitName(packageCalledName);
				
				System.out.println(className+" in layer "+ fromLayer);
				
				System.out.println(name+" in layer "+toLayer);
				if(ConfigurationService.getInstance().isRuleApplied(
						fromLayer, toLayer, this.getClass().getSimpleName())){
					this.addViolation(data, node);
				}
			}
						
//			ConfigurationService.getInstance()
//				.getLayerNameBySoftwareUnitName(packageName);
			
//			ConfigurationService.getInstance()
//				.getLayerNameBySoftwareUnitName(packageName);
		}
        
		
        
        return super.visit(node,data);
    }
}
