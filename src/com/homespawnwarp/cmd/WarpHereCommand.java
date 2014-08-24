package com.homespawnwarp.cmd;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.Teleportation;
import com.homespawnwarp.util.Tools;

public class WarpHereCommand extends RequestCommand {

	public WarpHereCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {
		super(plugin, commandPermission, name);
	}

	@Override
	public void createRequest(Player player, Player targetPlayer,
			double borrowMoney) {
		player.sendMessage(Tools.getMessage("sent-warphere-request")
				+ ChatColor.AQUA + targetPlayer.getName());
		Teleportation.createWarpHereRequest(player, targetPlayer, borrowMoney);
	}
}
