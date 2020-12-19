package com.nc.med.mapper;

import java.util.Set;

public class ReportBar {
	private Set<BarChartModel> weeklyBarChart;
	private Set<BarChartModel> monthlyBarChart;

	public ReportBar(Set<BarChartModel> weeklyBarChart, Set<BarChartModel> monthlyBarChart) {
		super();
		this.weeklyBarChart = weeklyBarChart;
		this.monthlyBarChart = monthlyBarChart;
	}

	public Set<BarChartModel> getWeeklyBarChart() {
		return weeklyBarChart;
	}

	public void setWeeklyBarChart(Set<BarChartModel> weeklyBarChart) {
		this.weeklyBarChart = weeklyBarChart;
	}

	public Set<BarChartModel> getMonthlyBarChart() {
		return monthlyBarChart;
	}

	public void setMonthlyBarChart(Set<BarChartModel> monthlyBarChart) {
		this.monthlyBarChart = monthlyBarChart;
	}
}
