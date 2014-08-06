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

final public class SpawnCommand extends TeleportCommand {

	public SpawnCommand(HomeSpawnWarp plugin, Permission commandPermission,
			boolean isDefaultPermitted) {
		super(plugin, commandPermission, isDefaultPermitted);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (plugin.useGeneralSpawn) {

			// Exact general spawn teleport
			if (Tools.getLocations().contains("spawn")) {
				String[] s = Tools.getLocations()
						.getConfigurationSection("spawn").getKeys(false)
						.toArray(new String[0]);

				if (s.length != 0) {
					Location l = LocationIO.read("spawn." + s[0]);

					if (l != null) {

						teleportPlayer(player, l, TeleportationType.SPAWN, getPrice(player));

					} else {
						player.sendMessage(Tools.getMessage("no-spawn-set"));
					}

				} else {
					player.sendMessage(Tools.getMessage("no-spawn-set"));
				}
			} else {
				player.sendMessage(Tools.getMessage("no-spawn-set"));
			}
		} else {

			// Exact players current world spawn

			Location l = LocationIO
					.read("spawn." + player.getWorld().getName());

			if (l != null) {

				teleportPlayer(player, l,
						TeleportationType.SPAWN, getPrice(player));

			} else {
				player.sendMessage(Tools.getMessage("no-spawn-set"));
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return "spawn";
	}
}
