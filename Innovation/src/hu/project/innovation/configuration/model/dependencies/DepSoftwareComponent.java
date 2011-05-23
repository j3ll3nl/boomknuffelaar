package hu.project.innovation.configuration.model.dependencies;

public class DepSoftwareComponent {
	/** pom.xml attributes */
	private String groupId, artifactId, version, type, scope;

	public DepSoftwareComponent() {
	}

	public DepSoftwareComponent(String groupId, String artifactId, String version, String type, String scope) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.type = type;
		this.scope = scope;
	}

	/** getter/setters */
	public String getGroupId() {
		return this.groupId;
	}

	public String getArtifactId() {
		return this.artifactId;
	}

	public String getVersion() {
		return this.version;
	}

	public String getType() {
		return this.type;
	}

	public String getScope() {
		return this.scope;
	}
}