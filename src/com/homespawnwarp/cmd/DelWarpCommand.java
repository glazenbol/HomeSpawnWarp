package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class DelWarpCommand extends AbstractCommand implements
		ConsoleSendable {

	public DelWarpCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			if (ConfigIO.getLocations().getString("warps." + args[0]) != null) {
				ConfigIO.getLocations().set("warps." + args[0], null);
				MessageSender.message(Message.WARP_DELETED, player);
				return true;
			} else {
				MessageSender.message(Message.WRONG_WARPNAME, player);
			}

		} else {
			MessageSender.message(Message.TOO_FEW_ARGUMENTS, player);
		}
		return false;
	}
}
