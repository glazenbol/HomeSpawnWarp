package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.main.HomeSpawnWarp;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.TeleportationType;
import com.homespawnwarp.tool.Tools;

final public class SpawnCommand extends TeleportCommand {

	public SpawnCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (HomeSpawnWarp.useGeneralSpawn) {

			// Exact general spawn teleport
			if (Tools.getLocations().contains("spawn")) {
				String[] s = Tools.getLocations()
						.getConfigurationSection("spawn").getKeys(false)
						.toArray(new String[0]);

				if (s.length != 0) {
					Location l = LocationIO.read("spawn." + s[0]);

					if (l != null) {

						teleportPlayer(player, l, TeleportationType.SPAWN);

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
						TeleportationType.SPAWN);

			} else {
				player.sendMessage(Tools.getMessage("no-spawn-set"));
			}
		}
		return false;
	}
}
