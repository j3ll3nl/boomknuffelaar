package layerchecker.definition;

public class RuleException {

	private String ruleName;
	private boolean permission;
	private Layer toLayer;
	
	public RuleException(String name, boolean permission, Layer layer) {
		ruleName = name;
		this.permission = permission;
		toLayer = layer;
	}
	
}
