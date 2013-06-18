package com.mike.wordrank.api.events;

import org.bukkit.entity.Player;

import com.mike.wordrank.api.events.word.WordAddToConfigEvent;
import com.mike.wordrank.api.events.word.WordUseEvent;
import com.mike.wordrank.api.word.Word;

public class EventHandle {

	/**
	 * Calls the WordAddToConfigEvent, should be called just before the word is actually added to the config
	 * @param word
	 * @param success, Whether of not the word was successfully added to config (for custom adding to configs)
	 * @return the new called event
	 */
	public static WordAddToConfigEvent callWordAddToConfigEvent(Word word, boolean success) {
		WordAddToConfigEvent event = new WordAddToConfigEvent(word, success);
		event.call();
		return event;
	}
	
	/**
	 * Calls the WordUseEvent, this should be called just before the word is actually used
	 * @param word
	 * @param player
	 * @return the new called event
	 */
	public static WordUseEvent callWordUseEvent(Word word, Player player) {
		WordUseEvent event = new WordUseEvent(word, player);
		event.call();
		return event;
	}
}