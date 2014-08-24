package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.MoneyMachine;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.Tools;

public abstract class RequestCommand extends TeleportCommand {

	public abstract void createRequest(Player player, Player targetPlayer, double borrowMoney);
	
	public RequestCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (args.length != 0) {

			double borrowMoney = getPrice(player);

			@SuppressWarnings("deprecation")
			Player targetPlayer = plugin.getServer().getPlayer(args[0]);

			if (targetPlayer != null && targetPlayer.isOnline()) {
				if (MoneyMachine.takeMoney(player, borrowMoney, false)) {
					createRequest(player, targetPlayer, borrowMoney);
				}

			} else {
				player.sendMessage(Tools.getMessage("player-not-online"));
			}
		} else {
			player.sendMessage(Tools.getMessage("too-few-arguments"));
		}
		return false;
	}

}
