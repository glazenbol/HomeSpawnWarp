package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand extends AbstractCommand {

	public SetWarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			Location l = player.getLocation();

			plugin.locationIO.write("warps." + args[0], l);

			player.sendMessage(plugin.getMessages()
					.getString("set-warp"));

			return true;
		} else {
			player.sendMessage(plugin.getMessage(
					"too-few-arguments"));
		}
		return false;
	}
}
