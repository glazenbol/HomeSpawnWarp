package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.Tools;

final public class SetWarpCommand extends AbstractCommand {

	public SetWarpCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			if (!containsIllegalChars(args[0], sender)) {
				Location l = player.getLocation();

				LocationIO.write("warps." + args[0], l);

				player.sendMessage(Tools.getMessage("set-warp"));
			}
		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
			return false;
		}
		return true;
	}

	@Override
	public String getName() {
		return "setwarp";
	}
}
