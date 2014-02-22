package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends AbstractCommand {

	public SetSpawnCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		Location l = player.getLocation();

		String wN = l.getWorld().getName();

		l.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(),
				(int) l.getZ());

		plugin.locationIO.write("spawn." + wN, l);

		player.sendMessage(plugin.getMessage("set-spawn"));
		return true;
	}
}
