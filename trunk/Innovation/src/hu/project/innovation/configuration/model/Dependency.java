package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.Dependency.SoftwareComponent;
import java.util.HashMap;

public class Dependency extends HashMap<String, SoftwareComponent> {

	private static final long serialVersionUID = 1L;

	protected class SoftwareComponent {
		private String groupId;
		private String artifactId;
		private String version;
		private String scope;
		public SoftwareComponent() {}
		public SoftwareComponent(String groupId, String artifactId, String version, String scope) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
			this.scope = scope;
		}

		public String getGroupId() { return this.groupId; }
		public String getArtifactId() { return this.artifactId; }
		public String getVersion() { return this.version; }
		public String getScope() { return this.scope; }
	}
	
	public Dependency() {}
	public void addComponent(String groupId, String artifactId, String version, String scope) {
		super.put(groupId, new SoftwareComponent(groupId, artifactId, version, scope));
	}
}
