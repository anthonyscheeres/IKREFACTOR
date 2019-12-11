package main.java.nl.iipsen2server.models;

import java.util.List;

class ProjectCollection {
	private List<ExperimentModel2> projects;

	public ProjectCollection(List<ExperimentModel2> projects) {
		super();
		this.projects = projects;
	}

	public List<ExperimentModel2> getProjects() {
		return projects;
	}

	public void setProjects(List<ExperimentModel2> projects) {
		this.projects = projects;
	}
	
}
