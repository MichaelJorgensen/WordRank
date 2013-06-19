package com.mike.wordrank.api.events.word;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mike.wordrank.api.word.Word;

public class WordAddToConfigEvent extends Event {

	private Word word;
	private boolean success;
	private boolean cancel;
	private static final HandlerList handler = new HandlerList();
	
	/**
	 * Called just before a word is added to the config, cancelling will stop the word from being added
	 * @param word
	 * @param success
	 */
	public WordAddToConfigEvent(Word word) {
		this.word = word;
		this.success = true;
		this.cancel = false;
	}
	
	public HandlerList getHandlers() {
		return handler;
	}
	
	public static HandlerList getHandlerList() {
		return handler;
	}
	
	/**
	 * Returns whether the addition to the config was successful or not
	 * @return success
	 */
	public boolean getSuccess() {
		return success;
	}
	
	/**
	 * Sets whether or not the word was added to the config
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * Returns whether or not the event has been cancelled
	 * @return cancelled
	 */
	public boolean isCancelled() {
		return cancel;
	}
	
	/**
	 * Sets the event cancelled to the specified boolean
	 * @param cancelled
	 */
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