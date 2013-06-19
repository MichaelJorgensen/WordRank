package com.mike.wordrank.api.events.word;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mike.wordrank.api.word.Word;

public class WordRemoveFromConfigEvent extends Event {

	private Word word;
	private boolean success;
	private boolean cancelled;
	
	private static final HandlerList handler = new HandlerList();
	
	/**
	 * Called just before a word is removed from the config
	 * Cancelling this event will stop the word from being removed
	 * @param word to be removed
	 */
	public WordRemoveFromConfigEvent(Word word) {
		this.word = word;
		this.success = true;
		this.cancelled = false;
	}
	
	public HandlerList getHandlers() {
		return handler;
	}
	
	public static HandlerList getHandlerList() {
		return handler;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * Gets the word used in this event
	 * @return word used
	 */
	public Word getWord() {
		return word;
	}
	
	/**
	 * Returns whether or not the event has been cancelled
	 * @return cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Sets the event cancelled to the specified boolean
	 * @param cancelled
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public void call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
}