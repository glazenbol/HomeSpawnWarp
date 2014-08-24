package com.homespawnwarp.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpHereRequest extends TeleportRequest {

	public WarpHereRequest(Player player, Player targetPlayer, JavaPlugin plugin) {
		super(player, targetPlayer, plugin);

		this.thenLocation = player.getLocation();

	}

	@Override
	public void sendMessage(Player player) {

		player.sendMessage(Tools.getMessage("warphere-request"));
	}

}
