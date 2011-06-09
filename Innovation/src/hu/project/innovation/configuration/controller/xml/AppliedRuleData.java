package hu.project.innovation.configuration.controller.xml;

/**
 * 
 * @author Stefan Kemp
 * 
 * A class to temporarily hold AppliedRule info
 * 
 */
public class AppliedRuleData {

	private int layer_id_to;
	private long appliedRule_id;

	public AppliedRuleData(int layer_id_to, long appliedRule_id) {
		this.layer_id_to = layer_id_to;
		this.appliedRule_id = appliedRule_id;

	}

	public int getLayer_id_to() {
		return layer_id_to;
	}

	public long getAppliedRule_id() {
		return appliedRule_id;
	}

	public void setLayer_id_to(int layer_id_to) {
		this.layer_id_to = layer_id_to;
	}

	public void setAppliedRule_id(long appliedRule_id) {
		this.appliedRule_id = appliedRule_id;
	}

}
