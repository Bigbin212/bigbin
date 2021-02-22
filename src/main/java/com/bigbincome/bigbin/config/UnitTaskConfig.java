package com.bigbincome.bigbin.config;

import com.bigbincome.bigbin.common.util.JsonUtils;
import com.bigbincome.bigbin.vo.Tasks;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;

@Configuration
@Data
@NoArgsConstructor
public class UnitTaskConfig {
	@Autowired
	private ApplicationContext ctx;

	private JsonNode configs;

	private static final String CONFIG_FILE = "classpath:unit-task-config.json";

	private static final String CPH = "cph";

	private static final String RATE = "rate";

	@PostConstruct
	public void init() throws IOException {
		Resource resource = ctx.getResource(CONFIG_FILE);
		this.configs = JsonUtils.from(resource.getInputStream());
	}

	public Optional<JsonNode> get(String unitId) {
		return Optional.ofNullable(this.configs.get(unitId));
	}

	public Optional<Tasks> getTask(String unitId, String taskId) {
		return get(unitId).map(o -> o.get(taskId)).map(t->Tasks.builder().cph(t.get(CPH).asInt()).rate(t.get(RATE).asDouble()).build());
	}

}