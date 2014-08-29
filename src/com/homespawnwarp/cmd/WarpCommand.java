package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportTicket;
import com.homespawnwarp.util.LocationIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class WarpCommand extends TeleportCommand {

	public WarpCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {

			Location l = LocationIO.read("warps." + args[0]);

			if (l != null) {

				double price;
				if (LocationIO.checkPriced("warps." + args[0])) {
					price = LocationIO.readPrice("warps." + args[0]);
				} else {
					price = getPrice(player);
				}

				teleportPlayer(new TeleportTicket(player, Message.TP_TO_WARP,
						l, true, price, warmup));

			} else {
				MessageSender.message(Message.WRONG_WARPNAME, player);
			}
		} else {
			MessageSender.message(Message.TOO_FEW_ARGUMENTS, player);
		}
		return false;
	}

}
