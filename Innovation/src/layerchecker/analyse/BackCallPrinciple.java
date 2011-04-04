package layerchecker.analyse;

import main.Logger;

public class BackCallPrinciple extends AbstractDefaultRule {

	@Override
	public void checkViolation() {
		if(this.isActive()) {
			Logger.getInstance().log("Active: " + this.getClass().getSimpleName());
		} else {
			Logger.getInstance().log("Not active: " + this.getClass().getSimpleName());
		}
	}

	public void visit() {
			this.checkViolation();
	}	
}
