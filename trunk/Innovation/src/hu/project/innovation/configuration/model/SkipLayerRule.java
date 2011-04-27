package hu.project.innovation.configuration.model;

import hu.project.innovation.utils.Log;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceType;

public class SkipLayerRule extends BackCallRule {
	

	public void checkViolationType(Object data,ASTClassOrInterfaceType node,AbstractRuleType ast){
		// Rule specific information
		String RuleName = "Skip-call Rule";
		
		//Variables to check on
		String toClass = node.getType().getSimpleName();
		String toClassType = (node.getType().isInterface())?"Interface":"Class";
		String toIsSuperclass = (node.getType().isMemberClass())?"Memberclass":"Superclass";
		Log.i(this, "type ->"+toClassType);
		String msg = "There is no violationtype defined for "+ RuleName+".";
		
		//Checks with message
		msg =(toClassType.equals("Class"))? "The call to "+toClass+" violates "+RuleName : msg;
		
		this.addViolationWithMessage(data, node, msg);
	}
}
