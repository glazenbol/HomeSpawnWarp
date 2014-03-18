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

				TeleportRequest tr = new TeleportRequest(player, targetPlayer);
				Teleportation.teleportRequests.put(targetPlayer.getName(), tr);
				new Thread(tr).start();

			} else {
				player.sendMessage(Tools.getMessage("player-not-online"));
			}
		} else {// TODO bug around these commands, when cancelled by movement,
				// and performed fast again can say no pending invites while
				// there is an invite, this is caused by another invit who
				// cancels the other one because its cancelled
				//PROBABLY FIXED!!! WARPTO WORKS

			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}

	@Override
	public String getName() {
		return "warpto";
	}
}
