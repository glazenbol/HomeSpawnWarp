package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.TeleportationType;
import com.homespawnwarp.tool.Tools;

final public class WarpAcceptCommand extends TeleportCommand {

	public WarpAcceptCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (Teleportation.teleportRequests.containsKey(player.getName())
				&& Teleportation.teleportRequests.get(player.getName())
						.arePlayersOnline()) {

			Player player2 = Teleportation.teleportRequests.get(
					player.getName()).getSender();

			player.sendMessage(Tools.getMessage("you-accepted-request-of") + ChatColor.AQUA + player.getName());
			player2.sendMessage(ChatColor.AQUA + player.getName()
					+ Tools.getMessage("accepted-your-request"));

			teleportPlayer(player2, Teleportation.teleportRequests.get(player.getName()).getLocation(),
					TeleportationType.REQUEST, HomeSpawnWarp.warpToCommand.getPrice(player2));
			Teleportation.removeRequest(player);

			return true;
		} else {
			player.sendMessage(Tools.getMessage("no-request"));
		}
		return false;
	}


	@Override
	public String getName() {
		return "warpaccept";
	}
}
