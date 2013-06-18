package com.mike.wordrank.api;

import org.bukkit.configuration.file.FileConfiguration;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.WordRankTypes.GroupType;
import com.mike.wordrank.api.config.WordConfig;
import com.mike.wordrank.api.events.word.WordAddToConfigEvent;
import com.mike.wordrank.api.word.GroupWord;
import com.mike.wordrank.api.word.Word;

public class GroupWordManager extends WordManager {

	private WordConfig config;
	private FileConfiguration cfg;
	
	public GroupWordManager(WordRank plugin) {
		super(plugin);
		this.config = getWordConfig();
		this.cfg = getConfiguration();
	}
	
	/**
	 * Returns the group of the word as seen in the config
	 * @param word
	 * @return String name of the group
	 */
	public String getGroup(GroupWord word) {
		return cfg.getString("words." + word.getName() + ".group");
	}
	
	/**
	 * Returns the group of the word as seen in the config
	 * @param name
	 * @return String name of the group
	 */
	public String getGroup(String name) {
		return cfg.getString("words." + name + ".group");
	}
	
	/**
	 * Changes the config to match the group set by the given variable
	 * @param word
	 */
	public void updateGroup(GroupWord word) {
		config.setSave("words." + word.getName() + ".group", word.getGroup());
	}
	
	/**
	 * Changes the config to match the group given for the name given
	 * @param name
	 * @param group
	 */
	public void updateGroup(String name, String group) {
		config.setSave("words." + name + ".group", group);
	}
	
	/**
	 * Gets the type of group word the given group word is according to words.yml
	 * Returns GroupType.Unknown if the config is improper, defaults to GroupType.Set
	 * @param word
	 * @return group type
	 */
	public GroupType getType(GroupWord word) {
		return GroupType.getFrom(cfg.getString("words." + word + ".type", "Set"));
	}
	
	/**
	 * Gets the type of group word the given group word is according to words.yml
	 * Returns GroupType. Unknown if the config is improper, defaults to GroupType.Set
	 * @param word
	 * @return group type
	 */
	public GroupType getType(String word) {
		return GroupType.getFrom(cfg.getString("words." + word + ".type", "Set"));
	}
	
	public void updateType(GroupWord word) {
		config.setSave("words." + word.getName() + ".type", word.getType().toString());
	}
	
	public int getUses(GroupWord word) {
		return cfg.getInt("words." + word.getName() + ".uses", -1);
	}
	
	public int getUses(String name) {
		return cfg.getInt("words." + name + ".uses", -1);
	}
	public void updateUses(GroupWord word) {
		config.setSave("words." + word.getName() + ".uses", word.getUses());
	}
	
	public GroupWord getWord(String name) {
		if (!wordExists(name)) return null;
		String p = "words." + name;
		return new GroupWord(name, cfg.getString(p + ".group"), GroupType.getFrom(cfg.getString(p + ".type")), cfg.getInt(p + ".uses"));
	}
	
	public void removeWordFromConfig(String name) {
		config.setSave("words." + name, null);
	}
	
	public boolean addWordToConfig(Word word) {
		WordAddToConfigEvent event = new WordAddToConfigEvent(word, true);
		if (event.isCancelled()) return event.getSuccess();
		
		if (word instanceof GroupWord) {
			config.setSave("words." + word.getName() + ".group", ((GroupWord) word).getGroup());
			config.setSave("words." + word.getName() + ".type", ((GroupWord) word).getType().toString());
			config.setSave("words." + word.getName() + ".uses", word.getUses());
			return true;
		} else {
			WordRank.sendErr("WordRank was instructed to add an unsupported Word Type to the config");
			WordRank.sendErr("If you are attempting to create a 3rd party word, please cancel the event 'WordAddToConfigEvent'");
			return false;
		}
	}
}