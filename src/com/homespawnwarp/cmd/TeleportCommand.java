package com.homespawnwarp.cmd;

import java.util.HashSet;

import org.bukkit.entity.Player;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportTicket;
import com.homespawnwarp.tp.Teleportation;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.perm.Permission;
import com.homespawnwarp.util.perm.PermissionAgent;

public abstract class TeleportCommand extends AbstractCommand {

	public static boolean usingWarmups = false;
	protected double[] price = new double[5];
	protected int warmup;

	public TeleportCommand(HomeSpawnWarp plugin, Permission commandPermission,
			String name) {

		super(plugin, commandPermission, name);

		setupPrices();
		setupWarmup();
	}

	protected void teleportPlayer(TeleportTicket ticket) {
		Teleportation.teleportPlayer(ticket);
	}

	protected void setupPrices() {
		for (int i = 0; i < price.length; i++) {
			price[i] = ConfigIO.getConfig().getDouble("prices." + name + (i + 1));
			// Warpto is just warpto
		}
	}

	private void setupWarmup() {
		warmup = (int) (ConfigIO.getConfig().getDouble("warmups." + name) * 1000);
		if (warmup > 0) {
			TeleportCommand.usingWarmups = true;
		}
	}

	protected double getPrice(Player player) {

		HashSet<Double> prices = new HashSet<Double>();

		if (PermissionAgent.checkCustomPerm(player, Permission.PRICES, false,
				1, name, true)) {
			if (price[0] <= 0) {
				return 0;
			} else {
				prices.add(price[0]);
			}
		}
		for (int i = 1; i < price.length; i++) {
			if (PermissionAgent.checkCustomPerm(player, Permission.PRICES,
					false, i + 1, name, false)) {

				if (price[i] > 0) {
					prices.add(price[i]);
				}
			}
		}

		// Get the lowest price permitted for that user
		double lowestVal = 0;

		for (Double curVal : prices) {
			if (curVal > 0d && (curVal < lowestVal || lowestVal == 0)) {
				lowestVal = curVal;
			}
		}
		return lowestVal;

	}
}
