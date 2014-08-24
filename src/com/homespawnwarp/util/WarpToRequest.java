package com.homespawnwarp.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpToRequest extends TeleportRequest {

	public WarpToRequest(Player player, Player targetPlayer, JavaPlugin plugin) {
		super(player, targetPlayer, plugin);

		this.thenLocation = player.getLocation();
	}

	@Override
	public void sendMessage(Player player) {

		player.sendMessage(Tools.getMessage("warpto-request"));
	}
}
