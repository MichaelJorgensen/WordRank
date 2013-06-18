package com.mike.wordrank.api.events.word;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.mike.wordrank.api.word.Word;

public class WordUseEvent extends Event {

	private Word word;
	private Player player;
	private boolean cancelled;
	private final HandlerList handler = new HandlerList();
	
	/**
	 * Called just before a word is used, if cancelled, the word will not be used
	 * @param word
	 * @param player
	 */
	public WordUseEvent(Word word, Player player) {
		this.word = word;
		this.player = player;
		this.cancelled = false;
	}

	public HandlerList getHandlers() {
		return handler;
	}
	
	/**
	 * Gets the word used in this event
	 * @return word
	 */
	public Word getWord() {
		return word;
	}
	
	/**
	 * Gets the player that used the word in this event
	 * @return player that caused this event
	 */
	public Player getPlayer() {
		return player;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public void call() {
		Bukkit.getServer().getPluginManager().callEvent(this);
	}
}