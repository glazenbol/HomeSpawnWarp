package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.Tools;

final public class DelHomeCommand extends AbstractCommand {

	public DelHomeCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (args.length == 0) {
			
			if (Tools.getLocations().contains(
					"homes." + player.getUniqueId() + ".default.x")) {

				Tools.getLocations().set(
						"homes." + player.getUniqueId() + ".default", null);
				player.sendMessage(Tools.getMessage("home-deleted"));
				return true;
			} else {
				player.sendMessage(Tools.getMessage("wrong-homename"));
			}
		} else {
			if (Tools.getLocations().contains(
					"homes." + player.getUniqueId()+ "." + args[0])) {

				Tools.getLocations().set(
						"homes." + player.getUniqueId() + "." + args[0], null);
				player.sendMessage(Tools.getMessage("home-deleted"));
				return true;
			} else {
				player.sendMessage(Tools.getMessage("wrong-homename"));
			}
		}
		return false;
	}

}
