package layerchecker.analyse;

public class BackCallPrinciple extends AbstractDefaultRule {

	@Override
	public void checkViolation() {
		// TODO Auto-generated method stub
		
	}

	public void visit() {
		if(this.isActive()) {
			this.checkViolation();
		}
	}
	
}
