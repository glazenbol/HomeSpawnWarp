package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Tools;

final public class DelWarpCommand extends AbstractCommand implements ConsoleSendable{

	public DelWarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted) {
		super(plugin, commandPermission, isDefaultPermitted);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			if (Tools.getLocations().getString("warps." + args[0]) != null) {
				Tools.getLocations().set("warps." + args[0], null);
				sender.sendMessage(Tools.getMessage(
						"warp-deleted"));
				return true;
			} else {
				sender.sendMessage(Tools.getMessage(
						"wrong-warpname"));
			}

		} else {
			sender.sendMessage(Tools.getMessage(
					"too-few-arguments"));
		}
		return false;
	}

	@Override
	public String getName() {
		return "delwarp";
	}
}
