package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.Teleportation;
import com.homespawnwarp.tp.TeleportationType;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class WarpAcceptCommand extends TeleportCommand {

	public WarpAcceptCommand(HomeSpawnWarp plugin,
			Permission commandPermission, String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	boolean doCommand(Player player, CommandSender sender, Command cmd,
			String commandLabel, String[] args) {

		if (Teleportation.teleportRequests.containsKey(player.getUniqueId())
				&& Teleportation.teleportRequests.get(player.getUniqueId())
						.arePlayersOnline()) {

			Player player2 = Teleportation.teleportRequests.get(
					player.getUniqueId()).getSender();

			MessageSender.playerMessage(Message.ACCEPTED_REQUEST_FROM, player,
					player2);
			MessageSender.playerMessage(Message.ACCEPTED_YOUR_REQUEST, player2,
					player);

			teleportPlayer(player2,
					Teleportation.teleportRequests.get(player.getUniqueId())
							.getLocation(), TeleportationType.REQUEST,
					getPrice(player2), warmup);
			Teleportation.removeRequest(player);

			return true;
		} else {
			MessageSender.message(Message.NO_REQUEST, player);
		}
		return false;
	}

	@Override
	public void setupPrices() {
		for (int i = 0; i < price.length; i++) {
			price[i] = ConfigIO.getConfig()
					.getDouble("prices.warpto" + (i + 1));
			// Warpto for warpaccept, cuz warpaccept is doing the money taking
			// TODO
		}
	}

}
