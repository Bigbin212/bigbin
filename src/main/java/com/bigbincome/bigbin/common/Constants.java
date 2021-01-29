package com.bigbincome.bigbin.common;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String ML_RESULT_KEY = "result";

	public static final String ID = "id";

	public static final String STATUS = "status";

	public static final String METADATA = "metadata";

	public static final String PREDICT = "predict";

	public static final String RESULT = "result";

	public static final String VERSION = "version";

	public static final String CONFIG = "config";

	public static final String TASK_ID = "taskId";

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String CODE = "code";

	public static final String MESSAGE = "message";

	public static final String DETAILS = "details";

	public static final String PAYLOAD = "content";

	public static final String START_DATE = "startDate";

	public static final String END_DATE = "endDate";

	public static final String HTTP = "http";

	public static final String HTTPS = "https";

	public static final String BEARER_TOKEN_REGION = "Bearer ";

	public static class TaskStatus {
		public static final String CREATED = "CREATED";
		public static final String SUBMITTED = "SUBMITTED";
		public static final String IN_QUEUE = "IN_QUEUE";
		public static final String RUNNING = "RUNNING";
		public static final String PROCESSED = "PROCESSED";
		public static final String FAILED = "FAILED";
	}

	public static class Metadata {
		public static final String SOURCE = "source";
		public static final String SERVICE = "service";

		public static final String CLIENT = "client";
		public static final String TYPE = "type";
		public static final String STORE = "store";
		public static final String FILE_NAME = "fileName";

		public static final String TARGET = "target";
		public static final String TIME_RANGE = "timeRange";
		public static final String TIME_SLOT = "timeSlot";

		public static final Integer DEFAULT_TIME_SLOT = 30;
	}

	public static enum Source {
		GIO, GAC_INTEGRATION_SCHEDULER, GAC_INTEGRATION_AUTO_ROSTER, GAC_INTEGRATION_DEMAND_ROSTER;

		private static final Map<String, Source> mappings = new HashMap<>(16);

		static {
			for (Source s : values()) {
				mappings.put(s.name(), s);
			}
		}

		@Nullable
		public static Source of(@Nullable String value) {
			return (value != null ? mappings.get(value) : null);
		}

		public boolean matches(String value) {
			return (this == of(value));
		}
	}
}
