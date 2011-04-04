package layerchecker.definition;

import main.Logger;

public class RuleException {

	private String ruleName;
	private boolean permission;
	private Layer toLayer;
	
	public RuleException(String name, boolean permission) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		ruleName = name;
		this.permission = permission;
	}
	
	public RuleException(String name, boolean permission, Layer layer) {
		Logger.getInstance().log(this.getClass().getSimpleName());
		ruleName = name;
		this.permission = permission;
		toLayer = layer;
	}
	
	
	
}
