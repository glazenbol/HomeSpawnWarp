package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpListCommand extends AbstractCommand {


	public WarpListCommand(HomeSpawnWarp plugin,
			String commandPermission, boolean isDefaultPermitted,
			boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted,
				isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		

		sender.sendMessage(plugin.getMessage("warps"));
		if (plugin.getLocations().contains("warps")) {
						String[] w = plugin.getLocations().getConfigurationSection("warps").getKeys(false).toArray(new String[0]);
	
			if (w.length != 0) {
	
				StringBuilder sb = new StringBuilder();
	
				for (int i = 0; i < w.length; i++) {
					if (i == w.length - 1) {
						sb.append(ChatColor.DARK_AQUA + w[i] + ChatColor.DARK_GREEN + ".");
					} else {
						sb.append(ChatColor.DARK_AQUA + w[i] + ChatColor.DARK_GREEN + ", ");
					}
				}
	
				sender.sendMessage(sb.toString());
	
			} else {
				sender.sendMessage(plugin.getMessage("none"));
			}
		} else {
			sender.sendMessage(plugin.getMessage("none"));
		}

		return false;
	}
}
