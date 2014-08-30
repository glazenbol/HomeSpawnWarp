package com.homespawnwarp.cmd;

import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.WarpHereRequest;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

public class WarpHereCommand extends RequestCommand {

	public WarpHereCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	public boolean createRequest(Player player, Player targetPlayer,
			double borrowMoney, int warmup) {

		if (PermissionAgent.checkPerm(targetPlayer, Permission.NOWARPHERE)
				&& !PermissionAgent.checkPerm(player,
						Permission.OVERRIDE_NOWARPHERE)) {
			MessageSender.playerMessage(Message.DOESNT_ALLOW_WARPHERE, player,
					targetPlayer);
			return false;
		} else {
			MessageSender.playerMessage(Message.SENT_WARPHERE_REQUEST, player,
					targetPlayer);
			new WarpHereRequest(player, targetPlayer, borrowMoney, plugin,
					warmup);
			return true;
		}
	}
}
