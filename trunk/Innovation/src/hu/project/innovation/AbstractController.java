package hu.project.innovation;

import java.awt.event.ActionListener;

public abstract class AbstractController implements ActionListener {
	
	public AbstractController() {
		Logger.getInstance().log(this.getClass().getSimpleName());
	}
	
	public abstract void initGUI();

}
