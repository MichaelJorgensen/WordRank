package com.mike.wordrank.api.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface Config {
	
	public Object getValue(String path);
	
	public FileConfiguration getConfig();
}