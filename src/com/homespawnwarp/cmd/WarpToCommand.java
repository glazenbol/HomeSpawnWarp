package com.homespawnwarp.cmd;

import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.WarpToRequest;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;

final public class WarpToCommand extends RequestCommand {

	public WarpToCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	public void createRequest(Player player, Player targetPlayer,
			double borrowMoney, int warmup) {
		MessageSender.playerMessage(Message.SENT_WARPTO_REQUEST, player, targetPlayer);
		new WarpToRequest(player, targetPlayer, borrowMoney, plugin, warmup);
	}


}
