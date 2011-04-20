package hu.project.innovation.configuration.model;

import java.util.HashMap;
import java.util.List;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.ast.ASTBlock;
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
	
	private HashMap<String, Integer> packageCalled = new HashMap<String, Integer>();
	
	public Object visit(ASTIfStatement node, Object data) {
		this.getPackageName(node);
        SimpleNode firstStmt = (SimpleNode)node.jjtGetChild(1);
        if (!hasBlockAsFirstChild(firstStmt)) {
//            addViolation(data, node);
        }
        return super.visit(node,data);
    }
    private boolean hasBlockAsFirstChild(SimpleNode node) {
        return (node.jjtGetNumChildren() != 0 && (node.jjtGetChild(0) instanceof ASTBlock));
    }
    
    @SuppressWarnings("unchecked")
	private String getPackageName(SimpleNode node) {
		try {			
			List<ASTPackageDeclaration> packages = 
				node.findChildNodesWithXPath("//PackageDeclaration");
			String packageName = packages.get(0).getPackageNameImage();
			
			if(packageCalled.get(packageName) == null) {
//				System.out.println("first");
				packageCalled.put(packageName, 1);
			}
			else {
//				System.out.println(packageCalled.get(packageName));
				packageCalled.put(packageName, packageCalled.get(packageName)+1);
			}
			int i = packageCalled.get(packageName);
			
			System.out.println(packageName + ": "+ i);
		} catch (JaxenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public void end(RuleContext ctx) {
    	for(String s : this.packageCalled.keySet()) {
    		System.out.println(s);
    		System.out.println(s + ": " + this.packageCalled.get(s));
    	}
	}
}