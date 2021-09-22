package com.gorun.demo.dto;

public class StatisticData {
	
	private String[] names;
	private int[] goals;
	private int[] assists;
	
	public StatisticData() {
		
	}
	
	public StatisticData(String[] names, int[] goals, int[] assists) {
		super();
		this.names = names;
		this.goals = goals;
		this.assists = assists;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public int[] getGoals() {
		return goals;
	}

	public void setGoals(int[] goals) {
		this.goals = goals;
	}

	public int[] getAssists() {
		return assists;
	}

	public void setAssists(int[] assists) {
		this.assists = assists;
	}
	
	

}
