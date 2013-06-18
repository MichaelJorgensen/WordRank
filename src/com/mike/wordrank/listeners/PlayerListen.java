package com.mike.wordrank.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.mike.wordrank.WordRank;
import com.mike.wordrank.WordRankTypes.RedeemType;
import com.mike.wordrank.api.GroupWordManager;
import com.mike.wordrank.api.word.GroupWord;

public class PlayerListen implements Listener {

	private WordRank plugin;
	
	public PlayerListen(WordRank plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void playerChat(AsyncPlayerChatEvent event) {
		if (plugin.getRedeemType() != RedeemType.Chat || event.isCancelled()) return;
		String word = event.getMessage();
		Player player = event.getPlayer();
		
		if (player.hasPermission("WordRank.say")) {
			GroupWordManager m = plugin.getGroupWordManager();
			GroupWord w = new GroupWord(word, m.getGroup(word), m.getType(word), m.getUses(word));
			event.setCancelled(plugin.groupWordUse(w, player));
		}
	}
}