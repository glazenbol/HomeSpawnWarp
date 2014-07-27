package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.TeleportRequest;
import com.homespawnwarp.util.Teleportation;
import com.homespawnwarp.util.Tools;

final public class WarpToCommand extends TeleportCommand {


	public WarpToCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted) {
		super(plugin, commandPermission, isDefaultPermitted);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {
			
			@SuppressWarnings("deprecation")
			Player targetPlayer = plugin.getServer().getPlayer(args[0]);
			
			if (targetPlayer != null && targetPlayer.isOnline()) {
				player.sendMessage(Tools.getMessage("sent-request")
						+ ChatColor.AQUA + targetPlayer.getName());

				TeleportRequest tr = new TeleportRequest(player, targetPlayer);
				Teleportation.teleportRequests.put(targetPlayer.getUniqueId(), tr);
				new Thread(tr).start();

			} else {
				player.sendMessage(Tools.getMessage("player-not-online"));
			}
		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}

	@Override
	public String getName() {
		return "warpto";
	}
}
