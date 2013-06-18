package com.mike.wordrank.api;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.api.config.WordConfig;

public class WordManager {

	private WordConfig wordConfig;
	private FileConfiguration config;
	
	public WordManager(WordRank plugin) {
		this.wordConfig = plugin.getWordConfig();
		this.config = wordConfig.getConfig();
	}
	
	/**
	 * Gets the configuration
	 * @return FileConfiguration of words.yml
	 */
	public FileConfiguration getConfiguration() {
		return config;
	}
	
	public WordConfig getWordConfig() {
		return wordConfig;
	}
	
	/**
	 * Returns true if the given word exists
	 * @param name of the word
	 * @return true if the word exists
	 */
	public boolean wordExists(String word) {
		return config.contains("words." + word);
	}
	
	/**
	 * Gets all of the words in the words.yml
	 * @return list of words
	 */
	public Set<String> getWords() {
		ConfigurationSection list = config.getConfigurationSection("words");
		if (list == null) return null;
		if (list.getKeys(false).size() == 0) return null;
		return list.getKeys(false);
	}
}