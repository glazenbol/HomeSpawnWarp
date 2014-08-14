package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.Tools;

final public class SetSpawnCommand extends AbstractCommand {

	public SetSpawnCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
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
