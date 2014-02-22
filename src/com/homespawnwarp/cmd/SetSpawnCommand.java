package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.main.HomeSpawnWarp;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.Tools;

final public class SetSpawnCommand extends HomeSpawnWarpCommand {

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

		LocationIO.write("spawn." + wN, l);

		player.sendMessage(Tools.getMessage("set-spawn"));
		return true;
	}
}
