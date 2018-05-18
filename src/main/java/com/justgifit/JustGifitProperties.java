package com.justgifit;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="com.justgifit")
public class JustGifitProperties {
	/**
	 * Location to save temporary file
	 */
	private File gifLocation;
	
	/**
	 * Indicates whether optimize should be done while autoconfiguration
	 */
	private Boolean optimize; 
	
	public File getGifLocation() {
		return gifLocation;
	}

	public void setGifLocation(File gifLocation) {
		this.gifLocation = gifLocation;
	}

	public Boolean getOptimize() {
		return optimize;
	}

	public void setOptimize(Boolean optimize) {
		this.optimize = optimize;
	}
	
}
