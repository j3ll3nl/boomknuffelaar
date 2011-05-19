package hu.project.innovation.configuration.controller;

import hu.project.innovation.configuration.model.Layer;

import java.awt.event.ActionListener;
import java.util.Observable;

public abstract class PopUpController extends Observable implements ActionListener {
	public static final String ACTION_NEW = "NEW";
	public static final String ACTION_EDIT = "EDIT";

	private String action = PopUpController.ACTION_NEW;
	private Layer layer;

	public abstract void initUi();

	public abstract void save();

	public abstract void addExceptionRow();

	public abstract void removeExceptionRow();

	/**
	 * Use this function to notify the definitioncontroller that there is a change
	 */
	protected void pokeObservers() {
		setChanged();
		notifyObservers();
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

	protected Layer getLayer() {
		return layer;
	}

	public void setAction(String action) {
		if (action.equals(PopUpController.ACTION_EDIT) || action.equals(PopUpController.ACTION_NEW)) {
			this.action = action;
		}
	}

	protected String getAction() {
		return action;
	}

}
