package com.mike.wordrank.api.events;

import org.bukkit.entity.Player;

import com.mike.wordrank.api.events.word.WordAddToConfigEvent;
import com.mike.wordrank.api.events.word.WordRemoveFromConfigEvent;
import com.mike.wordrank.api.events.word.WordUseEvent;
import com.mike.wordrank.api.word.Word;

public class EventHandle {

	/**
	 * Calls the WordAddToConfigEvent, should be called just before the word is actually added to the config
	 * @param word
	 * @return the new called event
	 */
	public static WordAddToConfigEvent callWordAddToConfigEvent(Word word) {
		WordAddToConfigEvent event = new WordAddToConfigEvent(word);
		event.call();
		return event;
	}
	
	/**
	 * Calls the WordRemoveFromConfigEvent, should be called just before the word is actually removed
	 * @param word
	 * @return the new called event
	 */
	public static WordRemoveFromConfigEvent callWordRemoveFromConfigEvent(Word word) {
		WordRemoveFromConfigEvent event = new WordRemoveFromConfigEvent(word);
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