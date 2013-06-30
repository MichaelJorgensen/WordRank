package com.mike.wordrank.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.WordRankTypes.RedeemType;
import com.mike.wordrank.api.word.GroupWord;

public class PlayerListen implements Listener {

	private WordRank plugin;
	
	public PlayerListen(WordRank plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerChat(AsyncPlayerChatEvent event) {
		if (plugin.getRedeemType() != RedeemType.Chat || event.isCancelled()) return;
		
		if (event.getPlayer().hasPermission("WordRank.say")) {
			GroupWord w = plugin.getGroupWordManager().getWord(event.getMessage());
			event.setCancelled(plugin.groupWordUse(w, event.getPlayer()));
		}
	}
}