package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeListCommand extends AbstractCommand {

	public HomeListCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		player.sendMessage(plugin.getMessage("homes"));

		if (plugin.getLocations().contains("homes." + player.getName())) {

			String[] h = plugin.getLocations()
					.getConfigurationSection("homes." + player.getName())
					.getKeys(false).toArray(new String[0]);

			if (h.length != 0) {

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < h.length; i++) {
					if (i == h.length - 1) {
						sb.append(ChatColor.DARK_AQUA + h[i]
								+ ChatColor.DARK_GREEN + ".");
					} else {
						sb.append(ChatColor.DARK_AQUA + h[i]
								+ ChatColor.DARK_GREEN + ", ");
					}
				}

				player.sendMessage(ChatColor.DARK_GREEN + sb.toString());

			} else {
				player.sendMessage(plugin.getMessages()
						.getString("none"));
			}
		} else {
			player.sendMessage(plugin.getMessage("none"));
		}

		return false;
	}
}
