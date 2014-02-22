package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HomeCommand extends AbstractCommand {

	public HomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable,
			ItemStack item, int amount) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable,
				item, amount);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length == 0) {

			Location l = plugin.locationIO.read("homes." + player.getName()
					+ ".default");

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				player.teleport(l);

			} else {
				player.sendMessage(plugin.getMessage(
						"no-home-set"));
			}

		} else {

			Location l = plugin.locationIO.read("homes." + player.getName() + "."
					+ args[0]);

			if (l != null) {
				player.sendMessage(plugin.getMessage(
						"teleport-to-home"));

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				player.teleport(l);
				
			} else {
				player.sendMessage(plugin.getMessage(
						"wrong-homename"));
			}
		}

		return false;
	}
}
