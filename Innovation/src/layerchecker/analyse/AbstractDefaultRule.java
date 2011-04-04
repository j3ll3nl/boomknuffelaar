package layerchecker.analyse;

import main.Logger;

public abstract class AbstractDefaultRule {
	
	private boolean active;
	private String name;
	
	public AbstractDefaultRule() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}

	public void active() {
		this.active = true;
	}
	
	public void deactivate() {
		this.active = false;
	}

	public boolean isActive() {
		return active;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
