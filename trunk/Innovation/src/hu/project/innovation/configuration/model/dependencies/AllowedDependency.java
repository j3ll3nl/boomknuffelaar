package hu.project.innovation.configuration.model.dependencies;


public class AllowedDependency extends Dependency {
	private static final long serialVersionUID = 1L;

	public AllowedDependency() {}
	public AllowedDependency(String groupId, String artifactId, String version, String type, String scope) {
		super.addDepSoftwareComponent(groupId, artifactId, version, type, scope);
	}
}
