package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.main.HomeSpawnWarp;
import com.homespawnwarp.tool.Tools;

final public class DelHomeCommand extends HomeSpawnWarpCommand {

	public DelHomeCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (args.length == 0) {
			if (Tools.getLocations().contains(
					"homes." + player.getName() + ".default.x")) {

				Tools.getLocations().set(
						"homes." + player.getName() + ".default", null);
				player.sendMessage(Tools.getMessage("home-deleted"));
				return true;
			} else {
				player.sendMessage(Tools.getMessage("wrong-homename"));
			}
		} else {
			if (Tools.getLocations().contains(
					"homes." + player.getName() + "." + args[0])) {

				Tools.getLocations().set(
						"homes." + player.getName() + "." + args[0], null);
				player.sendMessage(Tools.getMessage("home-deleted"));
				return true;
			} else {
				player.sendMessage(Tools.getMessage("wrong-homename"));
			}
		}
		return false;
		// Je suis cool
		
	}
}
