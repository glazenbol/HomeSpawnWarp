package com.homespawnwarp.cmd;

import java.util.HashSet;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.homespawnwarp.plugin.HomeSpawnWarp;
import com.homespawnwarp.util.Permission;
import com.homespawnwarp.util.PermissionAgent;
import com.homespawnwarp.util.Teleportation;
import com.homespawnwarp.util.TeleportationType;
import com.homespawnwarp.util.Tools;

public abstract class TeleportCommand extends AbstractCommand {

	protected double[] price = new double[5];

	public TeleportCommand(HomeSpawnWarp plugin, Permission commandPermission,
			boolean isDefaultPermitted) {

		super(plugin, commandPermission, isDefaultPermitted);

		if (this instanceof MoneyCommand) {
			setupPrices();
		}
	}

	protected void teleportPlayer(Player player, Location l,
			TeleportationType type, double price) {
		Teleportation.teleportPlayer(player, l, type, price);
	}

	public void setupPrices() {
		for (int i = 0; i < price.length; i++) {
			price[i] = Tools.getConfig().getDouble(
					"prices." + getName() + (i + 1));
			// Warpto is just warpto
		}
	}

	protected double getPrice(Player player) {

		boolean[] isInGroup = new boolean[price.length];

		if (PermissionAgent.checkPerm(player, Permission.PRICES, true, false,
				1, getName())) {
			if (price[0] <= 0) {
				return 0;
			}
			isInGroup[0] = true;
		}

		HashSet<Double> prices = new HashSet<Double>();

		for (int i = 1; i < price.length; i++) {
			if (PermissionAgent.checkPerm(player, Permission.PRICES, false,
					false, i + 1, getName())) {
				if (price[i] <= 0) {
					return 0;
				}
				prices.add(price[i]);
			}
		}

		// Get the lowest price permitted for that user
		double lowestVal = 0;

		for (Double curVal : prices) {
			if (curVal < lowestVal) {
				lowestVal = curVal;
			}
		}
		return lowestVal;

	}
}
