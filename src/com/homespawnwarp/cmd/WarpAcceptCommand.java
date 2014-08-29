package com.homespawnwarp.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportRequest;
import com.homespawnwarp.tp.TeleportTicket;
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

		if (TeleportRequest.requests.containsKey(player.getUniqueId())
				&& TeleportRequest.get(player)
						.arePlayersOnline()) {
			
			
			TeleportRequest req = TeleportRequest.get(player);

			Player sender1 = req.getSender();

			MessageSender.playerMessage(Message.ACCEPTED_REQUEST_FROM, player,
					sender1);
			MessageSender.playerMessage(Message.ACCEPTED_YOUR_REQUEST, sender1,
					player);

			teleportPlayer(new TeleportTicket(sender1, Message.TP_TO_REQUEST,req
					.getLocation(),true,getPrice(player),req.getWarmup()));
			TeleportRequest.removeRequest(player);

			return true;
		} else {
			MessageSender.message(Message.NO_REQUEST, player);
		}
		return false;
	}

}
