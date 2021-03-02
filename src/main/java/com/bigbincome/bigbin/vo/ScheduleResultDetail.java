package com.bigbincome.bigbin.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleResultDetail {

	private String personId;

	private String personName;

	private String picture;

	private String startTime;

	private String endTime;

	private String taskId;

	private String taskName;

	private String taskColor;

	private Integer workDay;

	private Integer restDay;

	private String shiftId;

	private String shiftName;
}
