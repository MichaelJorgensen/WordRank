package com.mike.wordrank.api.config;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.WordRankTypes.RedeemType;

public class MainConfig implements Config {

	private FileConfiguration config;
	
	public MainConfig(WordRank plugin) {
		this.config = plugin.getConfig();
		config.options().copyDefaults(true);
		if (!(new File("plugins/CouponCodes/config.yml").exists()))
			plugin.saveDefaultConfig();
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
}