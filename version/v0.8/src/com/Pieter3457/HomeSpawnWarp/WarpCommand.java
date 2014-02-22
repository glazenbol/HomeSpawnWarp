package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WarpCommand extends AbstractCommand {

	public WarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable,
			ItemStack item, int amount) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable,
				item, amount);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {

			Location l = plugin.locationIO.read("warps." + args[0]);

			if (l != null) {

				player.sendMessage(plugin.getMessage(
						"teleport-to-warp"));

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				player.teleport(l);

			} else {
				player.sendMessage(plugin.getMessage(
						"wrong-warpname"));
			}
		} else {
			player.sendMessage(plugin.getMessage(
					"too-few-arguments"));
		}
		return false;
	}
}
