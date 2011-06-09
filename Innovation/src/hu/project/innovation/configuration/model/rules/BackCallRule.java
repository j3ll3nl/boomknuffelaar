package hu.project.innovation.configuration.model.rules;

import net.sourceforge.pmd.ast.ASTImportDeclaration;
import net.sourceforge.pmd.ast.ASTName;
import net.sourceforge.pmd.ast.ASTType;

public class BackCallRule extends AbstractRuleType {

	public Object visit(ASTImportDeclaration node, Object data) {
		try {
			if (this.hasLayerRule(this.getCanonicalName(node), node.getImportedName())) {
				this.addViolation(data, node);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return super.visit(node, data);
	}

	public Object visit(ASTType node, Object data) {
		try {
			if (node.getType() != null) {
				if (this.hasLayerRule(this.getCanonicalName(node), node.getType().getCanonicalName())) {
					this.addViolation(data, node);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return super.visit(node, data);
	}

	public Object visit(ASTName node, Object data) {
		try {
			if (node.getType() != null) {
				if (this.hasLayerRule(this.getCanonicalName(node), node.getType().getCanonicalName())) {
					this.addViolation(data, node);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return super.visit(node, data);
	}
}
