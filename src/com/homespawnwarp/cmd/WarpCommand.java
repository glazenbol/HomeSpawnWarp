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

final public class WarpCommand extends TeleportCommand {

	public WarpCommand(HomeSpawnWarp plugin, Permission commandPermission, String name) {
		super(plugin, commandPermission,  name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {

			Location l = LocationIO.read("warps." + args[0]);

			if (l != null) {

				double price;
				if (LocationIO.checkPriced("warps." + args[0])) {
					price = LocationIO.readPrice("warps."+ args[0]);
				} else {
					price = getPrice(player);
				}

				teleportPlayer(player, l, TeleportationType.WARP, price, warmup);

			} else {
				player.sendMessage(Tools.getMessage("wrong-warpname"));
			}
		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}

}
