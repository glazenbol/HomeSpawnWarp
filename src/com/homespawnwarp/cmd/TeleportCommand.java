package com.homespawnwarp.cmd;

import java.util.HashSet;

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
			price[i] = Tools.getConfig().getDouble(
					"prices." + getName() + (i + 1));
			// Warpto is just warpto
		}
	}

	protected double getPrice(Player player) {

		boolean[] isInGroup = new boolean[price.length];
		String perm = "HomeSpawnWarp.prices." + getName();

		if (hasPerm(player, perm + 1, true, false)) {
			isInGroup[0] = true;
		}

		HashSet<Double> prices = new HashSet<Double>();

		for (int i = 1; i < price.length; i++) {
			if (hasPerm(player, perm + (i + 1), false, false)) {
				if (price[i] <= 0) {
					plugin.getLogger()
							.warning(
									HomeSpawnWarp.emblem
											+ " a price is 0, if you want a group free acces you should give it the HomeSpawnWarp.nofee permission");
					return 0; // Price is 0, this shouldn't occur but just in
								// case
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
