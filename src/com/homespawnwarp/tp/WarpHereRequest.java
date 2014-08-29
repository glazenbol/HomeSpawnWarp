package com.homespawnwarp.tp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;

public class WarpHereRequest extends TeleportRequest {

	public WarpHereRequest(Player player, Player targetPlayer,
			double borrowedMoney, JavaPlugin plugin, int warmup) {
		super(player, targetPlayer, borrowedMoney, plugin, warmup);

		this.thenLocation = player.getLocation();

	}

	@Override
	public void sendMessage(Player player, Player targetPlayer) {

		MessageSender.playerMessage(Message.WARPHERE_REQUEST, player,
				targetPlayer);
	}

}
