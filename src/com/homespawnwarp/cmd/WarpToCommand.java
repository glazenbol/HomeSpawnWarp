package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.TeleportRequest;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.Tools;

final public class WarpToCommand extends TeleportCommand {

	public WarpToCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			Player targetPlayer = plugin.getServer().getPlayer(args[0]);
			if (targetPlayer != null && targetPlayer.isOnline()) {
				player.sendMessage(Tools.getMessage("sent-request")
						+ ChatColor.AQUA + targetPlayer.getName());
				// createRequest(player, targetPlayer, price);

				TeleportRequest tr = new TeleportRequest(player, targetPlayer);
				Teleportation.teleportRequests.put(targetPlayer.getName(), tr);
				new Thread(tr).start();

			} else {
				player.sendMessage(Tools.getMessage("player-not-online"));// TODO
																			// fix
																			// command
																			// messages
			}
		} else {

			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}
}
