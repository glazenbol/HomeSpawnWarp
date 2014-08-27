package com.homespawnwarp.tp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.util.message.Message;
import com.homespawnwarp.util.message.MessageSender;

public class WarpHereRequest extends TeleportRequest {

	public WarpHereRequest(Player player, Player targetPlayer, JavaPlugin plugin) {
		super(player, targetPlayer, plugin);

		this.thenLocation = player.getLocation();

	}

	@Override
	public void sendMessage(Player player) {

		MessageSender.message(Message.WARPHERE_REQUEST, player);
	}

}
