package com.justgifit;

import javax.inject.Inject;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class JustGifitHealthIndicator implements HealthIndicator {

	@Inject
	private JustGifitProperties properties;

	@Override
	public Health health() {
		if (properties.getGifLocation().canWrite()) {
			return Health.down().build();
		}

		return Health.up().build();
	}
}
