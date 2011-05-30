package hu.project.innovation.configuration.model.rules;

import net.sourceforge.pmd.ast.*;

public class BackCallRule extends AbstractRuleType {

	public Object visit(ASTImportDeclaration node, Object data) {
		if(this.hasLayerRule(this.getCanonicalName(node),  node.getImportedName())) {
			this.addViolation(data, node);
		}
		return super.visit(node, data);
	}

	public Object visit(ASTType node, Object data) {
		if(node.getType() != null) {
			if(this.hasLayerRule(this.getCanonicalName(node), node.getType().getCanonicalName())) {
				this.addViolation(data, node);
			}
		}
		return super.visit(node, data);
	}
	
	public Object visit(ASTName node, Object data) {
		if(node.getType() != null) {
			if(this.hasLayerRule(this.getCanonicalName(node), node.getType().getCanonicalName())) {
				this.addViolation(data, node);
			}
		}
		return super.visit(node, data);
	}
}
