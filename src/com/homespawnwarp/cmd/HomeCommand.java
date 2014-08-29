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

final public class HomeCommand extends TeleportCommand {

	public HomeCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length == 0) {

			Location l = LocationIO.read("homes." + player.getUniqueId()
					+ ".default");

			if (l != null) {

				teleportPlayer(new TeleportTicket(player, Message.TP_TO_HOME,
						l, true, getPrice(player), warmup));

			} else {
				MessageSender.message(Message.NO_HOME_SET, player);
			}

		} else {

			Location l = LocationIO.read("homes." + player.getUniqueId() + "."
					+ args[0]);

			if (l != null) {

				teleportPlayer(new TeleportTicket(player, Message.TP_TO_HOME,
						l, true, getPrice(player), warmup));
			} else {
				MessageSender.message(Message.WRONG_HOMENAME, player);
			}
		}

		return false;
	}
}
