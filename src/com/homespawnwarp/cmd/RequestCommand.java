package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.EconomyManager;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

public abstract class RequestCommand extends TeleportCommand {

	public abstract void createRequest(Player player, Player targetPlayer,
			double borrowMoney, int warmup);

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
				if (EconomyManager.takeMoney(player, borrowMoney, false)) {
					createRequest(player, targetPlayer, borrowMoney, warmup);
				}

			} else {

				MessageSender.message(Message.PLAYER_NOT_ONLINE, player);
			}
		} else {
			MessageSender.message(Message.TOO_FEW_ARGUMENTS, player);
		}
		return false;
	}

}
