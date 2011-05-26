package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.dependencies.DepSoftwareComponent;
import hu.project.innovation.configuration.model.dependencies.Dependencies;
import hu.project.innovation.configuration.model.dependencies.Dependency;

public final class DependencyService {
	private Dependencies dependencies;
	private Dependencies allowedDependencies;

	public DependencyService() {
		// (allowed) dependencies
		this.dependencies = new Dependency();
		this.allowedDependencies = new Dependency();
	}
	
	// custom dependencies

	/**
	 * Add a dependency
	 */
	public void addDependency(String groupId, String artifactId, String version, String type, String scope) {
		this.dependencies.addDepSoftwareComponent(groupId, artifactId, version, type, scope);
	}

	/**
	 * Remove a dependency
	 * 
	 * @param groupId the component name to remove
	 */
	public void removeDependency(String artifactId) {
		dependencies.removeDepSoftwareComponent(artifactId);
	}

	/**
	 * return a dependency (if exists)
	 * 
	 * @param groupId the component to return
	 */
	public DepSoftwareComponent getDependency(String artifactId) {
		return dependencies.getDepSoftwareComponent(artifactId);
	}

	/**
	 * @return all dependencies
	 */
	public DepSoftwareComponent[] getDependencies() {
		return dependencies.getDepSoftwareComponents();
	}

	/** @return the searched dependency (if found) */
	public boolean searchDepSoftwareComponent(String keyword) {
		return dependencies.searchDepSoftwareComponent(keyword);
	}
	
	public void removeAllDependencies() {
		dependencies.removeDependencies();
	}

	// Allowed dependencies

	/**
	 * Add a allowed dependency
	 */
	public void addAllowedDependency(String groupId, String artifactId, String version, String type, String scope) {
		this.allowedDependencies.addDepSoftwareComponent(groupId, artifactId, version, type, scope);
	}

	/**
	 * Remove a allowed dependency
	 * 
	 * @param groupId the component name to remove
	 */
	public void removeAllowedDependency(String artifactId) {
		allowedDependencies.removeDepSoftwareComponent(artifactId);
	}

	/**
	 * return a allowed dependency (if exists)
	 * 
	 * @param groupId the component to return
	 */
	public DepSoftwareComponent getAllowedDependency(String artifactId) {
		return allowedDependencies.getDepSoftwareComponent(artifactId);
	}

	/**
	 * @return all allowed dependencies
	 */
	public DepSoftwareComponent[] getAllowedDependencies() {
		return allowedDependencies.getDepSoftwareComponents();
	}

	/** @return the searched dependency (if found) */
	public boolean searchAllowedDepSoftwareComponent(String keyword) {
		return allowedDependencies.searchDepSoftwareComponent(keyword);
	}
	
	public void removeAllAllowedDependencies() {
		allowedDependencies.removeDependencies();
	}
}
