package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.TeleportationType;

public abstract class TeleportCommand extends AbstractCommand {//TODO MAKE THIS TAKE MONEY ONLY WHEN TELEPORT FINISHES CORRECTLY

	public TeleportCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	protected void teleportPlayer(Player player, Location l, TeleportationType type) {
		Teleportation.teleportPlayer(player, l, type);
	}
	
	protected void createRequest(Player player, Player targetPlayer) {
		Teleportation.createRequest(player, targetPlayer);
	}
	
	protected boolean acceptRequest(Player player) {
		return Teleportation.acceptRequest(player);
	}
}
