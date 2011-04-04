package layerchecker.controller;

import java.awt.event.ActionListener;

import main.Logger;

public abstract class AbstractController implements ActionListener {
	
	public AbstractController() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}
	
	public abstract void initGUI();

}
