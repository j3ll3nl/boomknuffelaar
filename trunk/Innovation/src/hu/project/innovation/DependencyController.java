package hu.project.innovation;

import hu.project.innovation.configuration.model.ConfigurationService;

public class DependencyController {
	private ConfigurationService configurationService = ConfigurationService.getInstance();
	
	public static void main(String[] args) {
		new DependencyController();
	}
	
	public DependencyController() {
		configurationService.setProjectPath(System.getProperty("user.dir"));
	}
}
