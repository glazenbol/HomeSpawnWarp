package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportationType;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class SpawnCommand extends TeleportCommand {

	public SpawnCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (plugin.useGeneralSpawn) {

			// Exact general spawn teleport
			if (ConfigIO.getLocations().contains("spawn")) {
				String[] s = ConfigIO.getLocations()
						.getConfigurationSection("spawn").getKeys(false)
						.toArray(new String[0]);

				if (s.length != 0) {
					Location l = LocationIO.read("spawn." + s[0]);

					if (l != null) {

						teleportPlayer(player, l, TeleportationType.SPAWN,
								getPrice(player), warmup);

					} else {
						MessageSender.message(Message.NO_SPAWN_SET, player);
					}

				} else {
					MessageSender.message(Message.NO_SPAWN_SET, player);
				}
			} else {
				MessageSender.message(Message.NO_SPAWN_SET, player);
			}
		} else {

			// Exact players current world spawn

			Location l = LocationIO
					.read("spawn." + player.getWorld().getName());

			if (l != null) {

				teleportPlayer(player, l, TeleportationType.SPAWN,
						getPrice(player), warmup);

			} else {
				MessageSender.message(Message.NO_SPAWN_SET, player);
			}
		}
		return false;
	}
}
