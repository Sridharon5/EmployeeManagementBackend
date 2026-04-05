package com.my.dto;

import java.util.List;

public class UserDashboardDto {

	private long openTaskCount;
	private long dueWithinSevenDaysCount;
	private List<TaskResponseDto> recentlyClosedTasks;

	public long getOpenTaskCount() {
		return openTaskCount;
	}

	public void setOpenTaskCount(long openTaskCount) {
		this.openTaskCount = openTaskCount;
	}

	public long getDueWithinSevenDaysCount() {
		return dueWithinSevenDaysCount;
	}

	public void setDueWithinSevenDaysCount(long dueWithinSevenDaysCount) {
		this.dueWithinSevenDaysCount = dueWithinSevenDaysCount;
	}

	public List<TaskResponseDto> getRecentlyClosedTasks() {
		return recentlyClosedTasks;
	}

	public void setRecentlyClosedTasks(List<TaskResponseDto> recentlyClosedTasks) {
		this.recentlyClosedTasks = recentlyClosedTasks;
	}
}
