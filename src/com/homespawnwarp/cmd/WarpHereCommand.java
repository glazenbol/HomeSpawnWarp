package com.homespawnwarp.cmd;

import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.Teleportation;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

public class WarpHereCommand extends RequestCommand {

	public WarpHereCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	public void createRequest(Player player, Player targetPlayer,
			double borrowMoney) {
		MessageSender.playerMessage(Message.SENT_WARPTO_REQUEST, player,
				targetPlayer);
		Teleportation.createWarpHereRequest(player, targetPlayer, borrowMoney);
	}
}
