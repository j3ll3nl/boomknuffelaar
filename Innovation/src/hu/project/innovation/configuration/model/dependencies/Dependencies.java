package hu.project.innovation.configuration.model.dependencies;

public interface Dependencies {

	/** Add a dependency / software component */
	public void addDepSoftwareComponent(String groupId, String artifactId, String version, String type, String scope);

	/**
	 * @param groupId the id for the dependency to remove
	 * @return if the remove was successful (or not)
	 */
	public String removeDepSoftwareComponent(String artifactId);

	/**
	 * @param groupId
	 * @return the dependency
	 */
	public DepSoftwareComponent getDepSoftwareComponent(String artifactId);

	/** @return all dependencies / software components */
	public DepSoftwareComponent[] getDepSoftwareComponents();

	/** @return the searched dependency (if found) */
	public boolean searchDepSoftwareComponent(String keyword);
	
	/** remove all dependencies **/
	public void removeDependencies();
}
