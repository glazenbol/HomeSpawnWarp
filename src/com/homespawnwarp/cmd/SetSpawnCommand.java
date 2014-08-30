package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

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

		if (plugin.useGeneralSpawn) {
			ConfigIO.getConfig().set("spawn", null);
		}
		
		LocationIO.write("spawn." + wN, l);

		MessageSender.message(Message.SET_SPAWN, player);
		return true;
	}

}
