package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.TeleportationType;
import com.homespawnwarp.tool.Tools;

final public class WarpCommand extends TeleportCommand {

	public WarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {

			Location l = LocationIO.read("warps." + args[0]);

			if (l != null) {

				teleportPlayer(player, l, TeleportationType.WARP);

			} else {
				player.sendMessage(Tools.getMessage("wrong-warpname"));
			}
		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}
}
