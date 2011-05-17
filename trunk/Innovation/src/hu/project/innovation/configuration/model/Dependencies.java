package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.Dependencies.Dependency.SoftwareComponent;
import java.util.HashMap;

public interface Dependencies {
	
	/**
	 * Add a dependency / software component
	 */
	public void addSoftwareComponent(String groupId, String artifactId, String version, String scope);
	
	/**
	 * @param groupId the id for the dependency to remove
	 * @return if the remove was successful (or not)
	 */
	public String removeSoftwareComponent(String groupId);
	
	/**
	 * @param groupId
	 * @return the dependency
	 */
	public SoftwareComponent getSoftwareComponent(String groupId);
	
	/**
	 * @return all dependencies / software components
	 */
	public SoftwareComponent[] getSoftwareComponents();
	
	public class Dependency extends HashMap<String, SoftwareComponent> implements Dependencies {
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

			/**
			 * getter/setters
			 */
			public String getGroupId() { return this.groupId; }
			public String getArtifactId() { return this.artifactId; }
			public String getVersion() { return this.version; }
			public String getScope() { return this.scope; }
		}
		
		public Dependency() {}
		
		/**
		 * add a dependency
		 */
		public void addSoftwareComponent(String groupId, String artifactId, String version, String scope) {
			super.put(groupId, new SoftwareComponent(groupId, artifactId, version, scope));
		}
		
		/**
		 * @param groupId the dependency to remove
		 */
		public String removeSoftwareComponent(String groupId) {
			if(super.containsKey(groupId)) {
				super.remove(groupId);
				return "Dependency: " + groupId + " removed.";
			} else
				return "Dependency: " + groupId + " doesn't exist.";
		}
		
		/**
		 * @param groupId the identifier for the dependency to return
		 * @return a single dependency
		 */
		public SoftwareComponent getSoftwareComponent(String groupId) {
			if(super.containsKey(groupId)) return super.get(groupId);
			else {
				System.err.println("Dependency: " + groupId + " doesnt' exist.");
				return null;
			}
		}
		
		/**
		 * @return all dependencies
		 */
		public SoftwareComponent[] getSoftwareComponents() {
			if(super.size() > 0) {
				SoftwareComponent[] components = new SoftwareComponent[super.size()];
				int i = 0;
				for(SoftwareComponent component : super.values()) {
					components[i] = component;
					i++;
				}
				return components;
			} else {
				System.err.println("No dependencies exist.");
				return null;
			}
		}
	}
}
