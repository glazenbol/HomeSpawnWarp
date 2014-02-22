package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand extends AbstractCommand {

	public DelHomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (args.length == 0) {
			if (plugin.getLocations().contains("homes." + player.getName()
					+ ".default")) {

				plugin.getLocations().set("homes." + player.getName() + ".default",
						null);
				player.sendMessage(plugin.getMessage(
						"home-deleted"));
				return true;
			} else {
				player.sendMessage(plugin.getMessage(
						"wrong-homename"));
			}
		} else {
			if (plugin.getLocations()
					.contains("homes." + player.getName()+ "." + args[0])) {

				plugin.getLocations().set("homes." + player.getName() + "." + args[0],
						null);
				player.sendMessage(plugin.getMessage(
						"home-deleted"));
				return true;
			} else {
				player.sendMessage(plugin.getMessage(
						"wrong-homename"));
			}
		}
		return false;
	}
}
