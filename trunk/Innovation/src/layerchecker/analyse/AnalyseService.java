package layerchecker.analyse;

import java.util.ArrayList;

import main.Logger;

public class AnalyseService {
	
	private static AnalyseService instance;
	private ArrayList<AbstractDefaultRule> rules;
	
	public AnalyseService() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}
	
	public void startAnalyse() {
		
	}
	
	public static AnalyseService getInstance() {
		if(instance == null) {
			instance = new AnalyseService();
		}
		return instance;
	}

}
