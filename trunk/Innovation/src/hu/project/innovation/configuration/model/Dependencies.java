package hu.project.innovation.configuration.model;

import hu.project.innovation.configuration.model.Dependencies.Dependency.DepSoftwareComponent;

import java.util.HashMap;

public interface Dependencies {
	
	/** Add a dependency / software component */
	public void addDepSoftwareComponent(String groupId, String artifactId, String version, String scope);
	
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
	
	public class Dependency extends HashMap<String, DepSoftwareComponent> implements Dependencies {
		private static final long serialVersionUID = 1L;

		public class DepSoftwareComponent {
			/** pom.xml attributes */
			private String groupId, artifactId, version, scope;
			
			public DepSoftwareComponent() {}
			public DepSoftwareComponent(String groupId, String artifactId, String version, String scope) {
				this.groupId = groupId;
				this.artifactId = artifactId;
				this.version = version;
				this.scope = scope;
			}

			/** getter/setters */
			public String getGroupId() { return this.groupId; }
			public String getArtifactId() { return this.artifactId; }
			public String getVersion() { return this.version; }
			public String getScope() { return this.scope; }
		}
		
		public Dependency() {}
		
		/** add a dependency */
		public void addDepSoftwareComponent(String groupId, String artifactId, String version, String scope) {
			super.put(artifactId, new DepSoftwareComponent(groupId, artifactId, version, scope));
		}
		
		/** @param groupId the dependency to remove */
		public String removeDepSoftwareComponent(String artifactId) {
			if(super.containsKey(artifactId)) {
				super.remove(artifactId);
				return "Dependency: " + artifactId + " removed.";
			} else
				return "Dependency: " + artifactId + " doesn't exist.";
		}
		
		/**
		 * @param groupId the identifier for the dependency to return
		 * @return a single dependency
		 */
		public DepSoftwareComponent getDepSoftwareComponent(String artifactId) {
			if(super.containsKey(artifactId)) return super.get(artifactId);
			else {
				System.err.println("Dependency: " + artifactId + " doesnt' exist.");
				return null;
			}
		}
		
		/** @return all dependencies */
		public DepSoftwareComponent[] getDepSoftwareComponents() {
			if(super.size() > 0) {
				DepSoftwareComponent[] components = new DepSoftwareComponent[super.size()];
				int i = 0;
				for(DepSoftwareComponent component : super.values()) {
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
	public class AllowedDependency extends Dependency {
		private static final long serialVersionUID = 1L;

		public AllowedDependency() {}
		public AllowedDependency(String groupId, String artifactId, String version, String scope) {
			super.addDepSoftwareComponent(groupId, artifactId, version, scope);
		}
	}
}
