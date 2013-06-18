package com.mike.wordrank.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.api.events.word.WordAddToConfigEvent;
import com.mike.wordrank.api.events.word.WordUseEvent;

public class DebugListen implements Listener {

	public DebugListen(WordRank plugin) {
		
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onWordUse(WordUseEvent event) {
		WordRank.debug("Word use event called, Player: " + event.getPlayer().getName() + ", Word: " + event.getWord().getName() + ", Uses left: " + event.getWord().getUses() + ", Cancelled: " + event.isCancelled());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onWordAddToConfig(WordAddToConfigEvent event) {
		WordRank.debug("Word add to config event called, Word: " + event.getWord().getName() + ", Success: " + event.getSuccess() + ", Cancelled: " + event.isCancelled());
	}
}