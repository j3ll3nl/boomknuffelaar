package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;

public class SkipLayerRule extends BackCallRule {
	

	public void checkViolationType(Object data,ASTClassOrInterfaceType node,AbstractRuleType ast){
		// Rule specific information
		String RuleName = "Skip-call Rule";
		
		//Variables to check on
		String type = node.getType().getName();
		
		String msg = "There is no violationtype defined for "+ RuleName+".";
		
		//Checks with message
		//msg =(type.equals(type))? "" : "";
		
		this.addViolationWithMessage(data, node, msg);
	}
}
