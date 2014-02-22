package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand extends AbstractCommand {

	public DelWarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			if (plugin.getLocations().getString("warps." + args[0]) != null) {
				plugin.getLocations().set("warps." + args[0], null);
				sender.sendMessage(plugin.getMessage(
						"warp-deleted"));
				return true;
			} else {
				sender.sendMessage(plugin.getMessage(
						"wrong-warpname"));
			}

		} else {
			sender.sendMessage(plugin.getMessage(
					"too-few-arguments"));
		}
		return false;
	}
}
