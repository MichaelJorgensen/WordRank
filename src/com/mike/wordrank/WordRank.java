package com.mike.wordrank;

import java.io.IOException;
import java.util.Set;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.mike.wordrank.WordRankTypes.GroupType;
import com.mike.wordrank.WordRankTypes.RedeemType;
import com.mike.wordrank.api.GroupWordManager;
import com.mike.wordrank.api.config.MainConfig;
import com.mike.wordrank.api.config.WordConfig;
import com.mike.wordrank.api.events.EventHandle;
import com.mike.wordrank.api.word.GroupWord;
import com.mike.wordrank.listeners.DebugListen;
import com.mike.wordrank.listeners.PlayerListen;

public class WordRank extends JavaPlugin {

	private Metrics metrics;
	private Server server;
	private Permission permission;
	private RedeemType rt;
	private GroupWordManager gm;
	private MainConfig mainConfig;
	private WordConfig wordConfig;
	
	private static boolean debug;
	
	public void onEnable() {
		server = getServer();
		mainConfig = new MainConfig(this);
		wordConfig = new WordConfig(this);
		debug = mainConfig.getDebug();
		rt = mainConfig.getRedeemType();
		gm = new GroupWordManager(this);
		
		debug("Redeem Type: " + rt.toString());
		debug("Variables setup");
		
		if (!isVaultEnabled() || !setupVault()) {
			sendErr("The required plugin 'Vault' is not enabled, installed, or working properly. This plugin is required for WordRank to operate!");
			sendErr("WordRank will now disable and will check for the plugin 'Vault' next time it's enabled.");
			server.getPluginManager().disablePlugin(this);
			return;
		}
		debug("Vault has been properly setup");
		if (rt.equals(RedeemType.Unknown)) {
			sendErr("Redeem type is unknown, WordRank will disable. Check config and make sure 'redeem_type' is set to 'Chat' or 'Command'");
			server.getPluginManager().disablePlugin(this);
			return;
		}
		
		server.getPluginManager().registerEvents(new PlayerListen(this), this);
		server.getPluginManager().registerEvents(new DebugListen(this), this);
		debug("Event listeners registered");
		debug("Setting up Metrics");
		
		try {
			metrics = new Metrics(this);
			debug("Adding custom data to metrics plotter");
			
			metrics.addCustomData(new Metrics.Plotter("Amount of Words") {
				
				@Override
				public int getValue() {
					if (gm.getWords() == null) return 0;
					return gm.getWords().size();
				}
			});
			
			metrics.addCustomData(new Metrics.Plotter("Amount of Groups") {
				
				@Override
				public int getValue() {
					return permission.getGroups().length;
				}
			});
			
			if (metrics.start()) debug("Metrics setup successfully");
		} catch (IOException e) {
			debug("Failed to submit stats");
		}
		
		send("has successfully enabled!");
	}
	
	public void onDisable() {
		wordConfig.save();
		
		server = null;
		permission = null;
		rt = null;
		gm = null;
		mainConfig = null;
		wordConfig = null;
		
		send("has disabled successfully!");
	}
	
	private boolean isVaultEnabled() {
		debug("Checking for Vault...");
		
		if (server.getPluginManager().getPlugin("Vault") != null) {
			debug("Vault found, version " + server.getPluginManager().getPlugin("Vault").getDescription().getVersion());
			return true;
		} else {
			debug("Vault not found");
			return false;
		}
	}
	
	private boolean setupVault() {
		RegisteredServiceProvider<Permission> permissionProvider = server.getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		
		if (permissionProvider != null)
			permission = permissionProvider.getProvider();
		
		return (permission != null);
	}
	
	public Permission getPerm() {
		return permission;
	}
	
	public RedeemType getRedeemType() {
		return rt;
	}
	
	public GroupWordManager getGroupWordManager() {
		return gm;
	}
	
	public MainConfig getMainConfig() {
		return mainConfig;
	}
	
	public WordConfig getWordConfig() {
		return wordConfig;
	}
	
	public static void debug(String debugMessage) {
		if (debug) {
			send("[Debug] " + debugMessage);
		}
	}
	
	public static void send(String message) {
		System.out.println("[WordRank] " + message);
	}
	
	public static void sendErr(String error) {
		System.err.println("[WordRank] [Error] " + error);
	}
	
	public boolean groupWordUse(GroupWord w, Player player) {
		String word = w.getName();
		if (!(gm.getWords() == null) && gm.getWords().contains(word)) {
			if (permission.playerInGroup(player, w.getGroup()) && permission.getPlayerGroups(player).length > 1 || !permission.playerInGroup(player, w.getGroup())) {
				if (w.getUses() == 0) {
					if (rt.equals(RedeemType.Command)) {
						player.sendMessage(ChatColor.RED + "This word is out of uses!");
						return false;
					}
				}
				if (EventHandle.callWordUseEvent(w, player).isCancelled()) return false;
				String[] gr = permission.getPlayerGroups(player);
				for (int i = 0; i < gr.length && w.getType().equals(GroupType.Set); i++) {
					permission.playerRemoveGroup(player, gr[i]);
					WordRank.debug("Removed group '" + gr[i] + "' from player " + player.getName());
				}
				
				permission.playerAddGroup(player, w.getGroup());
				WordRank.debug("Added group '" + w.getGroup() + "' to player " + player.getName());
				
				if (w.getType().equals(GroupType.Set)) player.sendMessage(ChatColor.GOLD + "Your group has been set to " + ChatColor.BOLD + ChatColor.GREEN + w.getGroup() + ". " + ChatColor.RED + "You may have to reconnect for changes to take effect.");
				else if (w.getType().equals(GroupType.Add)) player.sendMessage(ChatColor.GOLD + "You have been added to the group " + ChatColor.BOLD + ChatColor.GREEN + w.getGroup() + ". " + ChatColor.RED + "You may have to reconnect for changes to take effect.");
				else {
					player.sendMessage(ChatColor.RED + "You have redeemed the right word, but the Group Word Type is not 'Set' or 'Add'");
					player.sendMessage(ChatColor.RED + "Please let an administrator know of this problem");
					WordRank.sendErr("GroupType for the word " + word + " is unknown, word will NOT work and has failed to set or add a group to " + player.getName());
					return false;
				}
				if (w.getUses() > 0) w.setUses(w.getUses()-1);
				gm.updateUses(w);
				WordRank.send("Player " + player.getName() + " has used the word " + w.getName());
				if (w.getType().equals(GroupType.Set)) WordRank.send("Player " + player.getName() + "'s group has been set to " + w.getGroup() + " and all other groups have been removed from that player by WordRank");
				if (w.getType().equals(GroupType.Add)) WordRank.send("Player " + player.getName() + " has had the group " + w.getGroup() + " added to his/her list of groups by WordRank");
				return true;
			}
			if (rt.equals(RedeemType.Command)) {
				player.sendMessage(ChatColor.RED + "You are already in the group this word grants");
			}
			return false;
		} else {
			if (rt.equals(RedeemType.Command)) {
				player.sendMessage(ChatColor.RED + "That word doesn't exist");
			}
			return false;
		}
	}
	
	public void help(CommandSender s) {
		if (rt.equals(RedeemType.Chat)) {
			s.sendMessage(ChatColor.DARK_RED + "|-- WordRank Commands You Can Use --|");
			if (s.hasPermission("WordRank.add")) { 
				s.sendMessage(ChatColor.YELLOW + "/wr add [word] [group] (set|add) (# of uses)");
				s.sendMessage(ChatColor.GOLD + "Set(Default): Sets group, removes other groups | Add: adds the group leaving old group(s) | Uses: -1(Default) for unlimited");
			}
			if (s.hasPermission("WordRank.remove")) s.sendMessage(ChatColor.YELLOW + "/wr remove [word]");
			if (s.hasPermission("WordRank.removeall")) s.sendMessage(ChatColor.YELLOW + "/wr removeall");
			if (s.hasPermission("WordRank.info")) s.sendMessage(ChatColor.YELLOW + "/wr info [word]");
			if (s.hasPermission("WordRank.list")) s.sendMessage(ChatColor.YELLOW + "/wr list");
		} else {
			s.sendMessage(ChatColor.DARK_RED + "|-- WordRank Commands You Can Use --|");
			if (s.hasPermission("WordRank.say")) s.sendMessage(ChatColor.YELLOW + "/wr redeem [word]");
			if (s.hasPermission("WordRank.add")) { 
				s.sendMessage(ChatColor.YELLOW + "/wr add [word] [group] (set|add) (# of uses)");
				s.sendMessage(ChatColor.GOLD + "Set(Default): Sets group, removes other groups | Add: adds the group leaving old group(s) | Uses: -1(Default) for unlimited");
			}
			if (s.hasPermission("WordRank.remove")) s.sendMessage(ChatColor.YELLOW + "/wr remove [word]");
			if (s.hasPermission("WordRank.removeall")) s.sendMessage(ChatColor.YELLOW + "/wr removeall");
			if (s.hasPermission("WordRank.info")) s.sendMessage(ChatColor.YELLOW + "/wr info [word]");
			if (s.hasPermission("WordRank.list")) s.sendMessage(ChatColor.YELLOW + "/wr list");
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (args.length == 0) {
			help(sender);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list") && args.length == 1) {
			if (sender.hasPermission("WordRank.list")) {
				if (gm.getWords() == null) {
					sender.sendMessage(ChatColor.RED + "There are no words");
					return true;
				} else {
					sender.sendMessage(ChatColor.AQUA + "List of words: " + ChatColor.GOLD + gm.getWords().toString());
					return true;
				}
			}
		}
		
		else if (args[0].equalsIgnoreCase("add")) {
			if (sender.hasPermission("WordRank.add")) {
				if (!(args.length >= 3 && args.length <= 5)) {
					sender.sendMessage(ChatColor.YELLOW + "/wr add [word] [group] (set|add) (# of uses)");
					sender.sendMessage(ChatColor.GOLD + "Set(Default): Sets group, removes other groups | Add: adds the group leaving old group(s) | Uses: -1(Default) for unlimited");
					return true;
				}
				GroupType gt = GroupType.Set;
				int uses = -1;
				if (args.length >= 4) {
					gt = GroupType.getFrom(args[3]);
				}
				if (gt.equals(GroupType.Unknown)) {
					sender.sendMessage(ChatColor.YELLOW + "/wr add [word] [group] (set|add) (# of uses)");
					sender.sendMessage(ChatColor.GOLD + "Set(Default): Sets group, removes other groups | Add: adds the group leaving old group(s) | Uses: -1(Default) for unlimited");
					return true;
				}
				if (args.length == 5) {
					try {
						uses = Integer.parseInt(args[4]);
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.GOLD + args[4] + ChatColor.RED + " is not a number!");
						return true;
					}
				}
				GroupWord w = new GroupWord(args[1], args[2], gt, uses);
				if (gm.wordExists(w.getName())) {
					sender.sendMessage(ChatColor.RED + "That word already exists");
					return true;
				} else {
					if (!gm.addWordToConfig(w)) {
						sender.sendMessage(ChatColor.RED + "Failed to add the word " + ChatColor.GOLD + w.getName() + ChatColor.RED + " to the config");
						return true;
					} else {
						sender.sendMessage(ChatColor.GREEN + "Success!");
						sender.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.GOLD + w.getName());
						sender.sendMessage(ChatColor.GREEN + "Group: " + ChatColor.GOLD + w.getGroup());
						sender.sendMessage(ChatColor.GREEN + "Type: " + ChatColor.GOLD + w.getType().toString());
						sender.sendMessage(ChatColor.GREEN + "Uses: " + ChatColor.GOLD + w.getUses());
						return true;
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission (WordRank.add)");
				return true;
			}
		}
		
		else if (args[0].equalsIgnoreCase("remove")) {
			if (sender.hasPermission("WordRank.remove")) {
				if (!(args.length == 2)) {
					sender.sendMessage(ChatColor.YELLOW + "/wr remove [word]");
					return true;
				}
				GroupWord w = gm.getWord(args[1]);
				if (w != null) {
					gm.removeWordFromConfig(w.getName());
					sender.sendMessage(ChatColor.GOLD + w.getName() + ChatColor.GREEN + " has been removed.");
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "That word doesn't exist");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission (WordRank.remove)");
				return true;
			}
		}
		
		else if (args[0].equalsIgnoreCase("removeall")) {
			if (sender.hasPermission("WordRank.removeall")) {
				if (args.length == 1) {
					Set<String> list = gm.getWords();
					int i = 0;
					if (list.size() == 0) {
						sender.sendMessage(ChatColor.RED + "There are no words to remove");
						return true;
					}
					for (String f:list) {
						gm.removeWordFromConfig(f);
						i++;
					}
					sender.sendMessage(ChatColor.GOLD + Integer.toString(i) + ChatColor.RED + " word(s) removed");
					return true;
				} else {
					sender.sendMessage(ChatColor.YELLOW + "/wr removeall");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission (WordRank.removeall)");
				return true;
			}
		}
		
		else if (args[0].equalsIgnoreCase("redeem")) {
			if (sender instanceof Player) {
				if (sender.hasPermission("WordRank.say")) {
					if (rt.equals(RedeemType.Command)) {
						if (args.length == 2) {
							Player player = (Player) sender;
							groupWordUse(new GroupWord(args[1], gm.getGroup(args[1]), gm.getType(args[1]), gm.getUses(args[1])), player);
							return true;
						} else {
							sender.sendMessage(ChatColor.YELLOW + "/wr redeem [word]");
							return true;
						}
					} else {
						sender.sendMessage(ChatColor.RED + "Redeem type is set to " + rt.toString() + " and not to Command");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.RED + "You do not have permission (WordRank.say)");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to redeem");
				return true;
			}
		}
		
		else if (args[0].equalsIgnoreCase("info")) {
			if (sender.hasPermission("WordRank.info")) {
				if (args.length == 2) {
					if (gm.wordExists(args[1])) {
						GroupWord w = gm.getWord(args[1]);
						sender.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.GOLD + w.getName());
						sender.sendMessage(ChatColor.GREEN + "Group: " + ChatColor.GOLD + w.getGroup());
						sender.sendMessage(ChatColor.GREEN + "Type: " + ChatColor.GOLD + w.getType().toString());
						sender.sendMessage(ChatColor.GREEN + "Uses: " + ChatColor.GOLD + w.getUses());
						return true;
					} else {
						sender.sendMessage(ChatColor.RED + "That word doesn't exist");
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.YELLOW + "/wr info [word]");
					return true;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You do not have permission (WordRank.info)");
				return true;
			}
		}
		
		help(sender);
		return true;
	}
}