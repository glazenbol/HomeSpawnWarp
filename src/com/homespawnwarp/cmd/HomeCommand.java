package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.TeleportationType;
import com.homespawnwarp.util.Tools;

final public class HomeCommand extends TeleportCommand {

	public HomeCommand(HomeSpawnWarp plugin, Permission commandPermission,
			boolean isDefaultPermitted, String name) {
		super(plugin, commandPermission, isDefaultPermitted,name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length == 0) {

			Location l = LocationIO.read("homes." + player.getUniqueId()
					+ ".default");

			if (l != null) {

				teleportPlayer(player, l, TeleportationType.HOME, getPrice(player), warmup);
				
			} else {
				player.sendMessage(Tools.getMessage("no-home-set"));
			}

		} else {

			Location l = LocationIO.read("homes." + player.getUniqueId() + "."
					+ args[0]);

			if (l != null) {

				teleportPlayer(player, l, TeleportationType.HOME, getPrice(player), warmup);
			} else {
				player.sendMessage(Tools.getMessage("wrong-homename"));
			}
		}

		return false;
	}
}
