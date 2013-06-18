package com.mike.wordrank.api.events.word;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mike.wordrank.api.word.Word;

public class WordAddToConfigEvent extends Event {

	private Word word;
	private boolean success;
	private boolean cancel;
	private final HandlerList handler = new HandlerList();
	
	/**
	 * Called just before a word is added to the config, cancelling will stop the word from being added
	 * @param word
	 * @param success
	 */
	public WordAddToConfigEvent(Word word, boolean success) {
		this.word = word;
		this.success = success;
		this.cancel = false;
	}
	
	public HandlerList getHandlers() {
		return handler;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public boolean isCancelled() {
		return cancel;
	}
	
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
	/**
	 * Gets the word used in this event
	 * @return word used
	 */
	public Word getWord() {
		return word;
	}
	
	public void call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
}