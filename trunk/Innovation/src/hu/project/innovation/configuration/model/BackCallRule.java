package hu.project.innovation.configuration.model;

import java.util.HashMap;
import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTBlock;
import net.sourceforge.pmd.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.ast.ASTIfStatement;
import net.sourceforge.pmd.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.ast.SimpleNode;

import org.jaxen.JaxenException;

public class BackCallRule extends AbstractRuleType {

	public BackCallRule() {
	}

	@Override
	public void checkViolation() {
		
	}
	
	private static HashMap<String, Integer> packageCalled = new HashMap<String, Integer>();
	
	public Object visit(ASTIfStatement node, Object data) {
		String packageName = this.getPackageName(node);
		
		
        SimpleNode firstStmt = (SimpleNode)node.jjtGetChild(1);
        if (!hasBlockAsFirstChild(firstStmt)) {
            addViolation(data, node);
        }
        String className = "";
        if (node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class) == null) {
            className = "";
        } else {
            className = node.getScope().getEnclosingClassScope().getClassName() == null ? "" : node.getScope().getEnclosingClassScope().getClassName();
        }
		System.out.println(className+" in layer "+
				ConfigurationService.getInstance()
				.getLayerNameBySoftwareUnitName(packageName));
		
		
        
        return super.visit(node,data);
    }
	
    private boolean hasBlockAsFirstChild(SimpleNode node) {
        return (node.jjtGetNumChildren() != 0 && (node.jjtGetChild(0) instanceof ASTBlock));
    }
}
