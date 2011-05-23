package hu.project.innovation.configuration.model.dependencies;

import hu.project.innovation.configuration.model.DependencyService;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class DependencyParseHandler extends DefaultHandler {
	private DependencyService dependencyService;
	boolean dependencies, groupId, artifactId, version, type, scope, isAllowedByDefault = false;
	private String _groupId, _artifactId, _version, _type, _scope = null;

	/**
	 * 
	 * @param dependencyService
	 * @param isAllowedByDefault
	 */
	public DependencyParseHandler(DependencyService dependencyService, boolean isAllowedByDefault) {
		this.dependencyService = dependencyService;
		this.isAllowedByDefault = isAllowedByDefault;
	}

	public void startElement(String uri, String qName, String localName, Attributes attrs) {
		if (localName.equalsIgnoreCase("dependencies"))
			dependencies = true;
		else if (localName.equalsIgnoreCase("groupId"))
			groupId = (dependencies) ? true : false;
		else if (localName.equalsIgnoreCase("artifactId"))
			artifactId = (dependencies) ? true : false;
		else if (localName.equalsIgnoreCase("version"))
			version = (dependencies) ? true : false;
		else if (localName.equalsIgnoreCase("type"))
			type = (dependencies) ? true : false;
		else if (localName.equalsIgnoreCase("scope"))
			scope = (dependencies) ? true : false;
	}

	public void endElement(String uri, String qName, String localName) {
		if (localName.equalsIgnoreCase("dependency")) {
			if (!isAllowedByDefault)
				dependencyService.addDependency(_groupId, _artifactId, _version, _type, _scope);
			else
				dependencyService.addAllowedDependency(_groupId, _artifactId, _version, _type, _scope);
		}
	}

	public void characters(char[] ch, int start, int length) {
		if (dependencies) {
			if (groupId) {
				_groupId = new String(ch, start, length);
				groupId = false;
			} else if (artifactId) {
				_artifactId = new String(ch, start, length);
				artifactId = false;
			} else if (version) {
				_version = new String(ch, start, length);
				version = false;
			} else if (type) {
				_type = new String(ch, start, length);
				type = false;
			} else if (scope) {
				_scope = new String(ch, start, length);
				scope = false;
			}
		}
	}

	public DependencyParseHandler(DependencyService dependencyService) {
		this.dependencyService = dependencyService;
	}

	public DependencyService getDependencyService() {
		return dependencyService;
	}

}
