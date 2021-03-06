package com.mike.wordrank.api.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.mike.wordrank.Language.Languages;
import com.mike.wordrank.WordRank;
import com.mike.wordrank.WordRankTypes.RedeemType;

public class MainConfig implements Config {

	private FileConfiguration config;
	
	public MainConfig(WordRank plugin) {
		this.config = plugin.getConfig();
		config.options().copyDefaults(true);
		plugin.saveConfig();
	}
	
	public Object getValue(String path) {
		return config.get(path);
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public boolean getDebug() {
		return config.getBoolean("debug_mode", false);
	}
	
	public RedeemType getRedeemType() {
		return RedeemType.getFrom(config.getString("redeem_type", "Chat"));
	}
	
	public Languages getLanguage() {
		return Languages.getLanguage(config.getString("language", "EN_US"));
	}
}