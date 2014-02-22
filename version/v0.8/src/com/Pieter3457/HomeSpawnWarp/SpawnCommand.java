package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnCommand extends AbstractCommand {

	public SpawnCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable,
			ItemStack item, int amount) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable,
				item, amount);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		player.sendMessage(plugin.getMessage(
				"teleport-to-spawn"));

		if (plugin.useGeneralSpawn) {

			// Exact general spawn teleport
			if (plugin.getLocations().contains("spawn")) {
				String[] s = plugin.getLocations().getConfigurationSection("spawn")
						.getKeys(false).toArray(new String[0]);

				if (s.length != 0) {
					Location l = plugin.locationIO.read("spawn." + s[0]);

					if (l != null) {

						if (!l.getChunk().isLoaded()) {
							l.getChunk().load();
						}
						player.teleport(l);

					} else {
						player.sendMessage(plugin.getMessage(
								"no-spawn-set"));
					}

				} else {
					player.sendMessage(plugin.getMessage(
							"no-spawn-set"));
				}
			} else {
				player.sendMessage(plugin.getMessage(
						"no-spawn-set"));
			}
		} else {

			// Exact players current world spawn

			Location l = plugin.locationIO.read("spawn."
					+ player.getWorld().getName());

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				player.teleport(l);

			} else {
				player.sendMessage(plugin.getMessage(
						"no-spawn-set"));
			}
		}
		return false;
	}
}
