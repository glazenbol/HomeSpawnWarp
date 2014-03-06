package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.TeleportationType;

public abstract class TeleportCommand extends AbstractCommand {

	protected double price1, price2, price3;

	public TeleportCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	protected void teleportPlayer(Player player, Location l,
			TeleportationType type, double price) {
		Teleportation.teleportPlayer(player, l, type, price);
	}

	public void setPrices(double price1, double price2, double price3) {
		this.price1 = price1;
		this.price2 = price2;
		this.price3 = price3;
	}

	protected double getPrice(Player player) {
		return 0;
		//hasPerm(sender, permission, true, false)// TODO
	}
}
