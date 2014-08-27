package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class DelHomeCommand extends AbstractCommand {

	public DelHomeCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (args.length == 0) {

			if (ConfigIO.getLocations().contains(
					"homes." + player.getUniqueId() + ".default.x")) {

				ConfigIO.getLocations().set(
						"homes." + player.getUniqueId() + ".default", null);
				MessageSender.message(Message.HOME_DELETED, player);
				return true;
			} else {
				MessageSender.message(Message.WRONG_HOMENAME, player);
			}
		} else {
			if (ConfigIO.getLocations().contains(
					"homes." + player.getUniqueId() + "." + args[0])) {

				ConfigIO.getLocations().set(
						"homes." + player.getUniqueId() + "." + args[0], null);
				MessageSender.message(Message.HOME_DELETED, player);
				return true;
			} else {
				MessageSender.message(Message.WRONG_HOMENAME, player);
			}
		}
		return false;
	}

}
