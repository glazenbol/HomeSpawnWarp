package com.homespawnwarp.cmd;

import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.WarpToRequest;
import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

final public class WarpToCommand extends RequestCommand {

	public WarpToCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	public boolean createRequest(Player player, Player targetPlayer,
			double borrowMoney, int warmup) {

		if (PermissionAgent.checkPerm(targetPlayer, Permission.NOWARPTO)
				&& !PermissionAgent.checkPerm(player,
						Permission.OVERRIDE_NOWARPTO)) {

			MessageSender.playerMessage(Message.DOESNT_ALLOW_WARPTO, player,
					targetPlayer);
			return false;
		} else {
			
			MessageSender.playerMessage(Message.SENT_WARPTO_REQUEST, player,
					targetPlayer);
			new WarpToRequest(player, targetPlayer, borrowMoney, plugin, warmup);
			return true;
		}
	}

}
