package com.homespawnwarp.cmd;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.TeleportationType;
import com.homespawnwarp.tool.Tools;

public abstract class TeleportCommand extends AbstractCommand {

	protected double[] price = new double[5];

	public TeleportCommand(HomeSpawnWarp plugin, String commandPermission,
			boolean isDefaultPermitted, boolean isConsoleSendable) {
		super(plugin, commandPermission, isDefaultPermitted, isConsoleSendable);
	}

	protected void teleportPlayer(Player player, Location l,
			TeleportationType type, double price) {
		Teleportation.teleportPlayer(player, l, type, price);
	}

	public void setupPrices() {
		for (int i = 0; i < price.length; i++) {
			price[i] = Tools.getConfig().getDouble("prices." + getName() + (i+1));
			//Warpto is just warpto
		}
	}

	protected double getPrice(Player player) {

		boolean[] isInGroup = new boolean[price.length];

		if (hasPerm(player, "HomeSpawnWarp.prices." + getName() + 1, true, false)) {
			isInGroup[0] = true;
		}
		
		for (int i = 1; i < homelimit.length; i++) {
			if (hasPerm(player, "HomeSpawnWarp.home.GROUP" + (i + 1), false, false)) {
				isInGroup[i] = true;
			}
		}
	}
}
